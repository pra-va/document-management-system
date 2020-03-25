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
	 * Returns document type by document name.
	 * 
	 * @param name of document type
	 * @return document type
	 */
	public DocType findDocTypeByName(String name) {
		return docTypeRepo.findDocTypeByName(name);
	}

	/**
	 * This method will create document type.
	 * 
	 * @param name      of document type
	 * @param creating  groups of document type
	 * @param approving groups of document type
	 */
	@Transactional
	public void createDocType(String name, String[] creating, String[] approving) {
		DocType newDocType = new DocType();
		newDocType.setName(name);
		addDocTypeToGroupsCreate(creating, newDocType);
		addDocTypeToGroupsApprove(approving, newDocType);
		docTypeRepo.save(newDocType);
	}

	/**
	 * This method will add groups that are able to approve documents by document
	 * type name.
	 * 
	 * @param groupListApprove list of groups that can approve documents belonging
	 *                         to this document type
	 * @param newDoc           new, updated document type
	 * @return updated document type
	 */
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

	/**
	 * This method will add groups that are able to create documents by document
	 * type name.
	 * 
	 * @param groupListCreate list of groups that can create documents belonging to
	 *                        this document type
	 * @param newDoc          new, updated document type
	 * @return updated document type
	 */
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

	/**
	 * Will return all document types
	 * 
	 * @param pagingData to set number of items, order and search phrase
	 * @return map of paging data nad all document types
	 */
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

	/**
	 * Returns all document types without groups.
	 * 
	 * @param pagingData to set number of items, order, and search phrase
	 * @return all document types without groups list
	 */
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

	/**
	 * Returns groups that are approving document type provided by its name.
	 * 
	 * @param docTypeName name
	 * @return list of document type names
	 */
	public List<String> getGroupsApproving(String docTypeName) {
		return docTypeRepo.findGroupsApprovingByDocTypeName(docTypeName);
	}

	/**
	 * Returns groups that are creating document type provided by its name.
	 * 
	 * @param docTypeName name
	 * @return list of document type names
	 */
	public List<String> getGroupsCreating(String docTypeName) {
		return docTypeRepo.findGroupsCreatingByDocTypeName(docTypeName);
	}

	/**
	 * This method will remove document type.
	 * 
	 * @param dType to remove
	 */
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

	/**
	 * Finds a list of group names that are able to sing document type by name
	 * provided in parameters.
	 * 
	 * @param name of document type
	 * @return list of groups that can sign document types
	 */
	public String[] findGroupsSigningDocType(String name) {
		List<Group> tmpList = docTypeRepo.findDocTypeByName(name).getGroupsApproving();
		String[] groups = new String[tmpList.size()];
		for (int i = 0; i < tmpList.size(); i++) {
			groups[i] = tmpList.get(i).getName();
		}
		return groups;
	}

	/**
	 * Finds a list of group names that are able to create document type by name
	 * provided in parameters.
	 * 
	 * @param name of document type
	 * @return list of groups that can create document types
	 */
	public String[] findGroupsCreatingDocType(String name) {
		List<Group> tmpList = docTypeRepo.findDocTypeByName(name).getGroupsCreating();
		String[] groups = new String[tmpList.size()];
		for (int i = 0; i < tmpList.size(); i++) {
			groups[i] = tmpList.get(i).getName();
		}
		return groups;
	}

	/**
	 * Updates document type details.
	 * 
	 * @param newName         of document type
	 * @param name            of document type that user wants to update
	 * @param groupsApproving set new groups approving
	 * @param groupsCreating  set new groups creating
	 */
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
