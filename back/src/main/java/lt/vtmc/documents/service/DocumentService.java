package lt.vtmc.documents.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lt.vtmc.docTypes.dao.DocTypeRepository;
import lt.vtmc.docTypes.model.DocType;
import lt.vtmc.documents.dao.DocumentRepository;
import lt.vtmc.documents.model.Document;
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
	/**
	 * 
	 * This method finds a document from group repository by name.
	 * 
	 * @return Document
	 */
	public Document findDocumentByName(String name) {
		return docRepo.findDocumentByName(name);
	}
	
	/**
	 * Method to create new document.
	 * 
	 * @return Document
	 */
	@Transactional
	public Document createDocument(String name, String authorUsername, String description, String dType, String currentTime) {
		Document newDocument = new Document(name, userRepo.findUserByUsername(authorUsername), dTypeRepo.findDocTypeByName(dType), description, currentTime);
		docRepo.save(newDocument);
		return newDocument;
	}

	public String[] findAll() {
		List<Document> tmpList = docRepo.findAll();
		String[] documentList = new String[tmpList.size()];
		for (int i = 0; i < tmpList.size(); i++) {
			documentList[i] = tmpList.get(i).getName();
		}
		return documentList;
	}
}
