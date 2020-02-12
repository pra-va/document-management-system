package lt.vtmc.documents.services;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lt.vtmc.documents.dao.DocTypeRepository;
import lt.vtmc.documents.dto.DocTypeDetailsDTO;
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
	public DocType findDocTypeByName(String name) {
		return docTypeRepo.findDocTypeByName(name);
	}

	@Transactional
	public void createDocType(String name, String[] creating, String[] approving) { // String documentType,
		DocType newDocType = new DocType();
		newDocType.setName(name);
//		newDocType.setDocumentType(documentType);
//		newDocType.setGroupsApproving(new ArrayList<Group>());
//		newDocType.setGroupsCreating(new ArrayList<Group>());
		addDocTypeToGroupsCreate(creating, newDocType);
		addDocTypeToGroupsApprove(approving, newDocType);
		docTypeRepo.save(newDocType);
	}

	@Transactional
	public DocType addDocTypeToGroupsApprove(String[] groupListApprove, DocType newDoc) {
		for (int i = 0; i < groupListApprove.length; i++) {
			Group groupToAdd = groupRepository.findGroupByName(groupListApprove[i]);
			List<DocType> tmp = groupToAdd.getDocTypesToApprove();
			tmp.add(newDoc);
			groupToAdd.setDocTypesToApprove(tmp);
		}
		return newDoc;
	}

	@Transactional
	public DocType addDocTypeToGroupsCreate(String[] groupListCreate, DocType newDoc) {
		for (int i = 0; i < groupListCreate.length; i++) {
			Group groupToAdd = groupRepository.findGroupByName(groupListCreate[i]);
			List<DocType> tmp = groupToAdd.getDocTypesToCreate();
			tmp.add(newDoc);
			groupToAdd.setDocTypesToCreate(tmp);
		}
		return newDoc;
	}

	public List<DocTypeDetailsDTO> getAllDocTypes() {
		List<DocType> tmpList = docTypeRepo.findAll();
		List<DocTypeDetailsDTO> list = new ArrayList<DocTypeDetailsDTO>();
		for (int i = 0; i < tmpList.size(); i++) {
			list.add(new DocTypeDetailsDTO(tmpList.get(i)));
		}
		return list;
	}

	@Transactional
	public void deleteDocType(String name) {
		docTypeRepo.delete(docTypeRepo.findDocTypeByName(name));
	}

	public String[] findGroupsSigningDocType(String name) {
		List<Group>tmpList = docTypeRepo.findDocTypeByName(name).getGroupsApproving();
		String[]groups = new String[tmpList.size()];
		for (int i = 0; i < tmpList.size(); i++) {
			groups[i] = tmpList.get(i).getName();
		}
		return groups;
	}
	
	public String[] findGroupsCreatingDocType(String name) {
		List<Group>tmpList = docTypeRepo.findDocTypeByName(name).getGroupsCreating();
		String[]groups = new String[tmpList.size()];
		for (int i = 0; i < tmpList.size(); i++) {
			groups[i] = tmpList.get(i).getName();
		}
		return groups;
	}

}
