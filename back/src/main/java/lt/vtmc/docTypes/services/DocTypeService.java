package lt.vtmc.docTypes.services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import lt.vtmc.docTypes.dao.DocTypeRepository;
import lt.vtmc.docTypes.dto.DocTypeDetailsDTO;
import lt.vtmc.docTypes.model.DocType;
import lt.vtmc.documents.model.Document;
import lt.vtmc.groups.dao.GroupRepository;
import lt.vtmc.groups.model.Group;
import lt.vtmc.groups.service.GroupService;
import lt.vtmc.paging.PagingData;
import lt.vtmc.paging.PagingResponse;

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
		if (groupListApprove.length == 0) {
			newDoc.setDocumentList(new ArrayList<Document>());
		} else {
			for (int i = 0; i < groupListApprove.length; i++) {
				Group groupToAdd = groupRepository.findGroupByName(groupListApprove[i]);
				List<DocType> tmp = groupToAdd.getDocTypesToApprove();
				tmp.add(newDoc);
				groupToAdd.setDocTypesToApprove(tmp);
			}
		}
		return newDoc;
	}

	@Transactional
	public DocType addDocTypeToGroupsCreate(String[] groupListCreate, DocType newDoc) {
		if (groupListCreate.length == 0) {
			newDoc.setDocumentList(new ArrayList<Document>());
		} else {
			for (int i = 0; i < groupListCreate.length; i++) {
				Group groupToAdd = groupRepository.findGroupByName(groupListCreate[i]);
				List<DocType> tmp = groupToAdd.getDocTypesToCreate();
				tmp.add(newDoc);
				groupToAdd.setDocTypesToCreate(tmp);
			}
		}
		return newDoc;
	}

//	public List<DocTypeDetailsDTO> getAllDocTypes() {
//		List<DocType> tmpList = docTypeRepo.findAll();
//		List<DocTypeDetailsDTO> list = new ArrayList<DocTypeDetailsDTO>();
//		for (int i = 0; i < tmpList.size(); i++) {
//			list.add(new DocTypeDetailsDTO(tmpList.get(i)));
//		}
//		return list;
//	}

	public Map<String, Object> retrieveAllDocTypes(PagingData pagingData) {
		Pageable firstPageable = pagingData.getPageable();
		Page<DocType> doctypelist = docTypeRepo.findLike(pagingData.getSearchValueString(), firstPageable);
		Map<String, Object> responseMap = new HashMap<String, Object>();
		responseMap.put("pagingData",
				new PagingResponse(doctypelist.getNumber(), doctypelist.getTotalElements(), doctypelist.getSize()));
		responseMap.put("documentList", doctypelist.getContent().stream().map(doctype -> new DocTypeDetailsDTO(doctype))
				.collect(Collectors.toList()));
		return responseMap;
	}

	@Transactional
	public void deleteDocType(DocType dType) {
		for (int i = 0; i < dType.getGroupsApproving().size(); i++) {
			Group tmp = dType.getGroupsApproving().get(i);
			List<DocType> tmpList = tmp.getDocTypesToApprove();
			tmpList.remove(dType);
		}
		for (int i = 0; i < dType.getGroupsCreating().size(); i++) {
			Group tmp = dType.getGroupsCreating().get(i);
			List<DocType> tmpList = tmp.getDocTypesToCreate();
			tmpList.remove(dType);
			groupRepository.save(tmp);
		}
		docTypeRepo.delete(dType);
	}

	public String[] findGroupsSigningDocType(String name) {
		List<Group> tmpList = docTypeRepo.findDocTypeByName(name).getGroupsApproving();
		String[] groups = new String[tmpList.size()];
		for (int i = 0; i < tmpList.size(); i++) {
			groups[i] = tmpList.get(i).getName();
		}
		return groups;
	}

	public String[] findGroupsCreatingDocType(String name) {
		List<Group> tmpList = docTypeRepo.findDocTypeByName(name).getGroupsCreating();
		String[] groups = new String[tmpList.size()];
		for (int i = 0; i < tmpList.size(); i++) {
			groups[i] = tmpList.get(i).getName();
		}
		return groups;
	}

	@Transactional
	public void updateDocTypeDetails(String newName, String name, String[] groupsApproving, String[] groupsCreating) {
		DocType updateDocType = docTypeRepo.findDocTypeByName(name);

		List<Group> approving = createGroupListFromGroupNames(groupsApproving);
		List<Group> creating = createGroupListFromGroupNames(groupsCreating);

		updateDocType.setName(newName);
		updateDocType.setGroupsApproving(approving);
		updateDocType.setGroupsCreating(creating);

		updateDocType = docTypeRepo.save(updateDocType);

		System.out.println("\n\n\n\n\n\n\n\n\n***********************");
		System.out.println(updateDocType.toString());
		System.out.println(Arrays.toString(groupsApproving));
		System.out.println(creating.toString());
		System.out.println(approving.toString());
		System.out.println(updateDocType.toString());
		System.out.println("***********************\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
	}

	public List<Group> createGroupListFromGroupNames(String[] groupNames) {
		List<Group> groups = new ArrayList<>();
		for (int i = 0; i < groupNames.length; i++) {
			String element = groupNames[i];
			groups.add(groupRepository.findGroupByName(element));
		}
		return groups;
	}

	public void docTypeCreate(String groupName, String docTypeName, boolean add, List<Group> creatingGroups) {
		Group group = groupRepository.findGroupByName(groupName);
		DocType docType = docTypeRepo.findDocTypeByName(docTypeName);

		List<DocType> docTypesCreate = group.getDocTypesToCreate();

		if (add) {
			docTypesCreate.add(docTypeRepo.findDocTypeByName(docTypeName));
		} else {
			for (int i = 0; i < docTypesCreate.size(); i++) {
				if (docTypesCreate.get(i).getName().equals(docTypeName)) {
					docTypesCreate.remove(i);
				}
			}
		}
		group.setDocTypesToCreate(docTypesCreate);
		docType.setGroupsCreating(creatingGroups);

		groupRepository.save(group);
		docTypeRepo.save(docType);

	}

}
