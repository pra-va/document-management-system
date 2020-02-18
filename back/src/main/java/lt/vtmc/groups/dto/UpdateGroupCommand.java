package lt.vtmc.groups.dto;

public class UpdateGroupCommand {
	private String newName;
	private String[] userList;
	private String description;
	private String[] docTypesToCreate;
	private String[] docTypesToApprove;

	public UpdateGroupCommand(String newName, String[] userList, String descirption, String[] docTypesToCreate,
			String[] docTypesToApprove) {
		super();
		this.newName = newName;
		this.userList = userList;
		this.description = descirption;
		this.docTypesToCreate = docTypesToCreate;
		this.docTypesToApprove = docTypesToApprove;
	}

	public String[] getUserList() {
		return userList;
	}

	public void setUserList(String[] userList) {
		this.userList = userList;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String[] getDocTypesToCreate() {
		return docTypesToCreate;
	}

	public void setDocTypesToCreate(String[] docTypesToCreate) {
		this.docTypesToCreate = docTypesToCreate;
	}

	public String[] getDocTypesToApprove() {
		return docTypesToApprove;
	}

	public void setDocTypesToApprove(String[] docTypesToApprove) {
		this.docTypesToApprove = docTypesToApprove;
	}

	public String getNewName() {
		return newName;
	}

	public void setNewName(String newName) {
		this.newName = newName;
	}

}
