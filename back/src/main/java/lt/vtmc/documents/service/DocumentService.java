package lt.vtmc.documents.service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
import lt.vtmc.paging.PagingData;
import lt.vtmc.paging.PagingResponse;
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

	public Map<String, Object> retrieveAllDocuments(PagingData pagingData) {
		Pageable firstPageable = pagingData.getPageable();
		Page<Document> documentlist = docRepo.findLike(pagingData.getSearchValueString(), firstPageable);
		Map<String, Object> responseMap = new HashMap<String, Object>();
		responseMap.put("pagingData",
				new PagingResponse(documentlist.getNumber(), documentlist.getTotalElements(), documentlist.getSize()));
		responseMap.put("documentList", documentlist.getContent().stream().map(user -> new DocumentDetailsDTO(user))
				.collect(Collectors.toList()));
		return responseMap;
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
		List<File4DB> tmpList = document.getFileList();
		if (tmpList != null) {
			for (File4DB file4db : tmpList) {
				fileService.deleteFile(file4db);
			}
		}

		User author = document.getAuthor();
		User handler = document.getHandler();
		DocType docType = document.getdType();

		if (author != null) {
			List<Document> tmpListAuth = author.getCreatedDocuments();
			tmpListAuth.remove(document);
			author.setCreatedDocuments(tmpListAuth);
			document.setAuthor(null);
		}

		if (handler != null) {
			List<Document> tmpListHand = handler.getProcessedDocuments();
			tmpListHand.remove(document);
			handler.setProcessedDocuments(tmpListHand);
			document.setHandler(null);
		}
		if (docType != null) {
			List<Document> tmpListDocType = docType.getDocumentList();
			tmpListDocType.remove(document);
			docType.setDocumentList(tmpListDocType);
			document.setdType(null);
		}
		docRepo.delete(document);
	}

	@Transactional
	public void setStatusPateiktas(String UID) {
		Document tmp = findDocumentByUID(UID);
		tmp.setDateSubmit(Instant.now().toString());
		tmp.setStatus(Status.SUBMITTED);
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
		tmp.setStatus(Status.DECLINED);
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

	public Map<String, Object> returnAllDocumentsByUsername(String username, PagingData pagingData) {
		Pageable pageable = pagingData.getPageable();
		Page<Document> documents = userRepo.docsByUsername(username, pagingData.getSearchValueString(), pageable);
		Map<String, Object> responseMap = new HashMap<>();
		responseMap.put("pagingData",
				new PagingResponse(documents.getNumber(), documents.getTotalElements(), documents.getSize()));
		responseMap.put("documents",
				documents.getContent().stream().map(doc -> new DocumentDetailsDTO(doc)).collect(Collectors.toList()));
		return responseMap;
	}

	public List<Document> findAllDocumentsByUsername(String username) {
		User tmpUser = userRepo.findUserByUsername(username);
		return tmpUser.getCreatedDocuments();
	}

	public Map<String, Object> findAllDocumentsToSignByUsername(String username, PagingData pagingData) {
		Pageable pageable = pagingData.getPageable();
		Page<Document> documents = userRepo.docsToSignByUsername(username, pagingData.getSearchValueString(), pageable);
		Map<String, Object> responseMap = new HashMap<>();
		responseMap.put("pagingData",
				new PagingResponse(documents.getNumber(), documents.getTotalElements(), documents.getSize()));
		responseMap.put("documents",
				documents.getContent().stream().map(doc -> new DocumentDetailsDTO(doc)).collect(Collectors.toList()));
		return responseMap;
	}

	@Transactional
	public void updateDocument(String docUID, String newName, String newDescription, String newDocType,
			String[] filesToRemove) {
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

	public Map<String, Object> returnSubmitted(String username, PagingData pagingData) {
		Pageable pageable = pagingData.getPageable();
		Page<Document> documents = userRepo.docsByUsernameAndStatus(username, pagingData.getSearchValueString(),
				Status.SUBMITTED, pageable);
		Map<String, Object> responseMap = new HashMap<>();
		responseMap.put("pagingData",
				new PagingResponse(documents.getNumber(), documents.getTotalElements(), documents.getSize()));
		responseMap.put("documents",
				documents.getContent().stream().map(doc -> new DocumentDetailsDTO(doc)).collect(Collectors.toList()));
		return responseMap;
	}

	public Map<String, Object> returnAccepted(String username, PagingData pagingData) {
		Pageable pageable = pagingData.getPageable();
		Page<Document> documents = userRepo.docsByUsernameAndStatus(username, pagingData.getSearchValueString(),
				Status.ACCEPTED, pageable);
		Map<String, Object> responseMap = new HashMap<>();
		responseMap.put("pagingData",
				new PagingResponse(documents.getNumber(), documents.getTotalElements(), documents.getSize()));
		responseMap.put("documents",
				documents.getContent().stream().map(doc -> new DocumentDetailsDTO(doc)).collect(Collectors.toList()));
		return responseMap;
	}

	public Map<String, Object> returnRejected(String username, PagingData pagingData) {
		Pageable pageable = pagingData.getPageable();
		Page<Document> documents = userRepo.docsByUsernameAndStatus(username, pagingData.getSearchValueString(),
				Status.DECLINED, pageable);
		Map<String, Object> responseMap = new HashMap<>();
		responseMap.put("pagingData",
				new PagingResponse(documents.getNumber(), documents.getTotalElements(), documents.getSize()));
		responseMap.put("documents",
				documents.getContent().stream().map(doc -> new DocumentDetailsDTO(doc)).collect(Collectors.toList()));
		return responseMap;
	}

	public Map<String, Object> returnCreated(String username, PagingData pagingData) {
		Pageable pageable = pagingData.getPageable();
		Page<Document> documents = userRepo.docsByUsernameAndStatus(username, pagingData.getSearchValueString(),
				Status.CREATED, pageable);
		Map<String, Object> responseMap = new HashMap<>();
		responseMap.put("pagingData",
				new PagingResponse(documents.getNumber(), documents.getTotalElements(), documents.getSize()));
		responseMap.put("documents",
				documents.getContent().stream().map(doc -> new DocumentDetailsDTO(doc)).collect(Collectors.toList()));
		return responseMap;
	}

	@Transactional
	public boolean deleteDocumentRequestedByUser(String uid, String username) {
		boolean doesUserHaveDoc = docRepo.doesUserHaveDoc(uid, username);

		if (doesUserHaveDoc) {
			deleteDocument(docRepo.findDocumentByUID(uid));
			return true;
		} else {
			return false;
		}
	}
}
