package lt.vtmc.groups.dto;


import java.util.List;

import lt.vtmc.documents.model.DocType;
import lt.vtmc.groups.model.Group;
import lt.vtmc.user.model.User;

public class GroupDetails {
	
	private String name;
	
	private String description;
	
	private List<User> userList;
	
	private List<DocType> docTypesToCreate;
	
	private List<DocType> docTypesToApprove;
	/**
	 * Constructor method for GroupDetails
	 * 
	 * @param name
	 * @param description
	 * @param userList
	 * @param docTypesToCreate
	 * @param docTypesToApprove
	 */

	public GroupDetails(Group group) {
		super();
		this.name = group.getName();
		this.description = group.getDescription();
		this.userList = group.getUserList();
		this.docTypesToCreate = group.getDocTypesToCreate();
		this.docTypesToApprove = group.getDocTypesToApprove();
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public List<User> getUserList() {
		return userList;
	}
	public String[] getUserListNames() {
		String[]userListNames = new String[0];
		for (int i = 0; i < userList.size(); i++) {
			userListNames[i] = userList.get(i).getName();
		}
		return userListNames;
	}
	public void setUserList(List<User> userList) {
		this.userList = userList;
	}
	public List<DocType> getDocTypesToCreate() {
		return docTypesToCreate;
	}
	public String[] getDocTypesToCreateNames() {
		String[]docTypesNames = new String[0];
		for (int i = 0; i < docTypesToCreate.size(); i++) {
			docTypesNames[i] = docTypesToCreate.get(i).getDocumentType();
		}
		return docTypesNames;
	}
	public void setDocTypesToCreate(List<DocType> docTypesToCreate) {
		this.docTypesToCreate = docTypesToCreate;
	}
	public List<DocType> getDocTypesToApprove() {
		return docTypesToApprove;
	}
	public String[] getDocTypesToApproveNames() {
		String[]docTypesNames = new String[0];
		for (int i = 0; i < docTypesToApprove.size(); i++) {
			docTypesNames[i] = docTypesToApprove.get(i).getDocumentType();
		}
		return docTypesNames;
	}
	public void setDocTypesToApprove(List<DocType> docTypesToApprove) {
		this.docTypesToApprove = docTypesToApprove;
	}
	
	
}
