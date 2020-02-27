package lt.vtmc.documents.service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lt.vtmc.docTypes.dao.DocTypeRepository;
import lt.vtmc.docTypes.model.DocType;
import lt.vtmc.documents.Status;
import lt.vtmc.documents.dao.DocumentRepository;
import lt.vtmc.documents.dto.DocumentDetailsDTO;
import lt.vtmc.documents.model.Document;
import lt.vtmc.files.model.File4DB;
import lt.vtmc.files.service.FileService;
import lt.vtmc.groups.model.Group;
import lt.vtmc.user.dao.UserRepository;
import lt.vtmc.user.model.User;

/**
 * Document service for creating and managing documents.
 * 
 * @author VStoncius
 *
 */
@Service
public class DocumentService {

	@Autowired
	private DocumentRepository docRepo;

	@Autowired
	private UserRepository userRepo;

	@Autowired
	private DocTypeRepository dTypeRepo;

	@Autowired
	private FileService fileService;
	/**
	 * 
	 * This method finds a document from group repository by name.
	 * 
	 * @return Document
	 */
	public Document findDocumentByUID(String UID) {
		return docRepo.findDocumentByUID(UID);
	}

	/**
	 * Method to create new document.
	 * 
	 * @return Document
	 */
	@Transactional
	public Document createDocument(String name, String authorUsername, String description, String dType,
			String currentTime) {
		Document newDocument = new Document(currentTime, userRepo.findUserByUsername(authorUsername),
				dTypeRepo.findDocTypeByName(dType), name, description, String.valueOf(System.currentTimeMillis()));
		List<Document> tmpList = userRepo.findUserByUsername(authorUsername).getCreatedDocuments();
		tmpList.add(newDocument);
		List<Document> tmpListDocuments = dTypeRepo.findDocTypeByName(dType).getDocumentList();
		tmpListDocuments.add(newDocument);
		newDocument.setFileList(new ArrayList<File4DB>());
		return docRepo.save(newDocument);
	}

	public List<DocumentDetailsDTO> findAll() {
		List<Document> tmpList = docRepo.findAll();
		List<DocumentDetailsDTO> list = new ArrayList<DocumentDetailsDTO>();
		for (int i = 0; i < tmpList.size(); i++) {
			list.add(new DocumentDetailsDTO(tmpList.get(i)));
		}
		return list;
	}
	
	@Transactional
	public void deleteDocument(Document document) {
		document.setFileList(null);
		document.setHandler(null);
		document.setAuthor(null);
		document.setdType(null);
		docRepo.delete(document);
	}

	@Transactional
	public void setStatusPateiktas(String UID) {
		Document tmp = findDocumentByUID(UID);
		tmp.setDateSubmit(Instant.now().toString());
		tmp.setStatus(Status.SUBMITED);
		docRepo.save(tmp);
	}

	@Transactional
	public void setStatusPriimtas(String UID, String username) {
		Document tmp = findDocumentByUID(UID);
		tmp.setDateProcessed(Instant.now().toString());
		User tmpUser = userRepo.findUserByUsername(username);
		tmp.setHandler(tmpUser);
		List<Document> tmpList = tmpUser.getProcessedDocuments();
		System.out.println(tmpList.toString());
		tmpList.add(tmp);
		tmpUser.setProcessedDocuments(tmpList);
		tmp.setStatus(Status.ACCEPTED);
		userRepo.save(tmpUser);
		docRepo.save(tmp);
	}

	@Transactional
	public void setStatusAtmestas(String UID, String username, String reasonToReject) {
		Document tmp = findDocumentByUID(UID);
		tmp.setDateProcessed(Instant.now().toString());
		tmp.setReasonToReject(reasonToReject);
		User tmpUser = userRepo.findUserByUsername(username);
		tmp.setHandler(tmpUser);
		List<Document> tmpList = tmpUser.getProcessedDocuments();
		tmpList.add(tmp);
		tmpUser.setProcessedDocuments(tmpList);
		tmp.setStatus(Status.REJECTED);
		userRepo.save(tmpUser);
		docRepo.save(tmp);
	}

	public String generateUID(String time) {
		StringBuilder UID = new StringBuilder();
		for (int i = 0; i < time.length(); i++) {
			if (Character.isDigit(time.charAt(i)) == true) {
				UID.append(time.charAt(i));
			}
		}
		return UID.toString();
	}

	public List<DocumentDetailsDTO> returnAllDocumentsByUsername(String username) {
		List<Document> tmpList = findAllDocumentsByUsername(username);
		List<DocumentDetailsDTO> listToReturn = new ArrayList<DocumentDetailsDTO>();
		for (Document document : tmpList) {
			listToReturn.add(new DocumentDetailsDTO(document));
		}
		return listToReturn;
	}

	public List<Document> findAllDocumentsByUsername(String username) {
		User tmpUser = userRepo.findUserByUsername(username);
		return tmpUser.getCreatedDocuments();
	}

	public List<DocumentDetailsDTO> findAllDocumentsToSignByUsername(String username) {
		User tmpUser = userRepo.findUserByUsername(username);
		List<Document> tmpList = findAllDocumentsByUsername(username);
		List<DocType> docTypeListToApprove = new ArrayList<DocType>();
		List<Group> tmpGroups = tmpUser.getGroupList();
		for (Group group : tmpGroups) {
			docTypeListToApprove.addAll(group.getDocTypesToApprove());
		}
		List<DocumentDetailsDTO> listToReturn = new ArrayList<DocumentDetailsDTO>();
		for (Document doc : tmpList) {
			if (docTypeListToApprove.contains(doc.getdType()) == true & doc.getStatus() == Status.SUBMITED) {
				listToReturn.add(new DocumentDetailsDTO(doc));
			}
		}
		return listToReturn;
	}

	@Transactional
	public void updateDocument(String docUID, String newName, String newDescription, String newDocType, String[] filesToRemove) {
		Document documentToUpdate = findDocumentByUID(docUID);
		documentToUpdate.setName(newName);
		documentToUpdate.setDescription(newDescription);
		List<Document> listToRemoveFrom = documentToUpdate.getdType().getDocumentList();
		listToRemoveFrom.remove(documentToUpdate);
		List<Document> listToAddTo = dTypeRepo.findDocTypeByName(newDocType).getDocumentList();
		listToAddTo.add(documentToUpdate);
		documentToUpdate.setdType(dTypeRepo.findDocTypeByName(newDocType));
		
		for (String file : filesToRemove) {
			fileService.deleteFileByUID(file);
		}
		
		docRepo.save(documentToUpdate);
	}
}
