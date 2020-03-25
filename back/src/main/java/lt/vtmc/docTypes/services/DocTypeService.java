package lt.vtmc.docTypes.services;

import java.util.ArrayList;
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
import lt.vtmc.documents.service.DocumentService;
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

	@Autowired
	private DocumentService docService;

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

	public Map<String, Object> retrieveAllDocTypesNoGroups(PagingData pagingData) {
		Pageable firstPageable = pagingData.getPageable();
		Page<String> doctypelist = docTypeRepo.findLikeDocTypeNames(pagingData.getSearchValueString(), firstPageable);
		Map<String, Object> responseMap = new HashMap<String, Object>();
		Map<String, List<String>> approving = new HashMap<>();
		Map<String, List<String>> creating = new HashMap<>();
		for (int i = 0; i < doctypelist.getContent().size(); i++) {
			String element = doctypelist.getContent().get(i);
			approving.put(element, docTypeRepo.findGroupsApprovingByDocTypeName(element));
			creating.put(element, docTypeRepo.findGroupsCreatingByDocTypeName(element));
		}

		responseMap.put("pagingData",
				new PagingResponse(doctypelist.getNumber(), doctypelist.getTotalElements(), doctypelist.getSize()));
		responseMap.put("documentList", doctypelist);
		responseMap.put("creating", creating);
		responseMap.put("approving", approving);
		return responseMap;
	}

	public List<String> getGroupsApproving(String docTypeName) {
		return docTypeRepo.findGroupsApprovingByDocTypeName(docTypeName);
	}

	public List<String> getGroupsCreating(String docTypeName) {
		return docTypeRepo.findGroupsCreatingByDocTypeName(docTypeName);
	}

	@Transactional
	public void deleteDocType(DocType dType) {
		List<Document> tmpListDocuments = dType.getDocumentList();
		for (Document document : tmpListDocuments) {
			document.setdType(null);
			docService.deleteDocument(document);
		}
		for (int i = 0; i < dType.getGroupsApproving().size(); i++) {
			Group tmp = dType.getGroupsApproving().get(i);
			List<DocType> tmpList = tmp.getDocTypesToApprove();
			tmpList.remove(dType);
		}
		for (int i = 0; i < dType.getGroupsCreating().size(); i++) {
			Group tmp = dType.getGroupsCreating().get(i);
			List<DocType> tmpList = tmp.getDocTypesToCreate();
			tmpList.remove(dType);
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
		DocType docTypeToUpdate = docTypeRepo.findDocTypeByName(name);
		docTypeToUpdate.setName(newName);

		List<Group> newApproveList = new ArrayList<Group>();
		for (String group : groupsApproving) {
			newApproveList.add(groupRepository.findGroupByName(group));
		}

		List<Group> tmpApproveList = docTypeToUpdate.getGroupsApproving();
		for (Group group : tmpApproveList) {
			{
				List<DocType> tmpList = group.getDocTypesToApprove();
				tmpList.remove(docTypeToUpdate);
				group.setDocTypesToApprove(tmpList);
			}
		}

		for (Group group : newApproveList) {
			List<DocType> tmpList = group.getDocTypesToApprove();
			tmpList.add(docTypeToUpdate);
			group.setDocTypesToApprove(tmpList);
		}
		docTypeToUpdate.setGroupsApproving(newApproveList);

		List<Group> newCreateList = new ArrayList<Group>();
		for (String group : groupsCreating) {
			newCreateList.add(groupRepository.findGroupByName(group));
		}

		List<Group> tmpCreateList = docTypeToUpdate.getGroupsCreating();
		for (Group group : tmpCreateList) {
			{
				List<DocType> tmpList = group.getDocTypesToCreate();
				tmpList.remove(docTypeToUpdate);
				group.setDocTypesToCreate(tmpList);
			}
		}

		for (Group group : newCreateList) {
			List<DocType> tmpList = group.getDocTypesToCreate();
			tmpList.add(docTypeToUpdate);
			group.setDocTypesToCreate(tmpList);
		}
		docTypeToUpdate.setGroupsCreating(newCreateList);
		docTypeRepo.save(docTypeToUpdate);
	}

}
