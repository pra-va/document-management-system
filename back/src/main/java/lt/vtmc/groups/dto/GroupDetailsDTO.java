package lt.vtmc.groups.dto;


import java.util.Arrays;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lt.vtmc.documents.model.DocType;
import lt.vtmc.groups.model.Group;
import lt.vtmc.user.model.User;

public class GroupDetailsDTO {
	
	private String name;
	
	private String description;
	
	@JsonIgnore
	private List<User> userList;
	
	@JsonIgnore
	private List<DocType> docTypesToCreate;
	
	@JsonIgnore
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

	public GroupDetailsDTO(Group group) {
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
		String[]userListNames = new String[userList.size()];
		for (int i = 0; i < userList.size(); i++) {
			userListNames[i] = userList.get(i).getUsername();
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
		String[]docTypesNames = new String[userList.size()];
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
		String[]docTypesNames = new String[userList.size()];
		for (int i = 0; i < docTypesToApprove.size(); i++) {
			docTypesNames[i] = docTypesToApprove.get(i).getDocumentType();
		}
		return docTypesNames;
	}
	public void setDocTypesToApprove(List<DocType> docTypesToApprove) {
		this.docTypesToApprove = docTypesToApprove;
	}
	@Override
	public String toString() {
		return "[name: " + name + ", description: " + getDescription() + ", users: " + Arrays.toString(getUserListNames())
				+ ", Can create: " + Arrays.toString(getDocTypesToCreateNames()) + ", Can approve: " + Arrays.toString(getDocTypesToApproveNames()) + "]";
	}
	
	
}
