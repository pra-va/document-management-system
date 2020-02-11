package lt.vtmc.documents.services;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lt.vtmc.documents.dao.DocTypeRepository;
import lt.vtmc.documents.model.DocType;
import lt.vtmc.groups.dao.GroupRepository;
import lt.vtmc.groups.model.Group;
import lt.vtmc.groups.service.GroupService;

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
	
	@Autowired
	GroupService groupService;
	
	@Autowired
	private GroupRepository groupRepository;
	/**
	 * 
	 * This method finds groups from group repository.
	 */
	public DocType findGroupByName(String name) {
		return docTypeRepo.findDocTypeByName(name);
	}

	@Transactional
	public void createDocType(String name, String[] creating, String[] approving) { // String documentType, 
		DocType newDocType = new DocType();
		newDocType.setName(name);
//		newDocType.setDocumentType(documentType);
		newDocType.setGroupsApproving(new ArrayList<Group>());
		newDocType.setGroupsCreating(new ArrayList<Group>());
		docTypeRepo.save(newDocType);
		addDocTypeToGroupsByName(creating, approving, name);
		
	}

	@Transactional
	public void addDocTypeToGroupsByName(String[] groupListCreate, String[] groupListApprove, String name) {
		DocType docTypeToAdd = docTypeRepo.findDocTypeByName(name);
		for (int i = 0; i < groupListApprove.length; i++) {
			Group groupToAddTo = groupRepository.findGroupByName(groupListApprove[i]);
			List<DocType> tmpDocList = groupToAddTo.getDocTypesToApprove();
			List<Group> tmpGroupList = docTypeToAdd.getGroupsApproving();
			if (tmpDocList.contains(docTypeToAdd) == false && tmpGroupList.contains(groupToAddTo) == false) {
				tmpGroupList.add(groupToAddTo);
				docTypeToAdd.setGroupsApproving(tmpGroupList);
				tmpDocList.add(docTypeToAdd);
				groupToAddTo.setDocTypesToApprove(tmpDocList);
			}
		}
		for (int i = 0; i < groupListCreate.length; i++) {
			Group groupToAddTo = groupRepository.findGroupByName(groupListCreate[i]);
			List<DocType> tmpDocList = groupToAddTo.getDocTypesToApprove();
			List<Group> tmpGroupList = docTypeToAdd.getGroupsApproving();
			if (tmpDocList.contains(docTypeToAdd) == false && tmpGroupList.contains(groupToAddTo) == false) {
				tmpGroupList.add(groupToAddTo);
				docTypeToAdd.setGroupsApproving(tmpGroupList);
				tmpDocList.add(docTypeToAdd);
				groupToAddTo.setDocTypesToApprove(tmpDocList);
			}
		}
	}
	
	public List<DocType> getAllDocTypes() {
		return docTypeRepo.findAll();
	}

	public void deleteDocType(String name) {
		DocType docTypeToDelete = docTypeRepo.findDocTypeByName(name);
			docTypeRepo.delete(docTypeToDelete);
	}
	

}
