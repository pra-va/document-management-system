package lt.vtmc.documents.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lt.vtmc.documents.dao.DocTypeRepository;
import lt.vtmc.documents.model.DocType;

/**
 * DocType service for creating and managing Document types.
 * 
 * @author VStoncius
 *
 */

@Service
public class DocTypeService {

	@Autowired
	DocTypeRepository docTypeRepo;
	
	/**
	 * 
	 * This method finds groups from group repository.
	 */
	public DocType findGroupByName(String name) {
		return docTypeRepo.findDocTypeByName(name);
	}
	

}
