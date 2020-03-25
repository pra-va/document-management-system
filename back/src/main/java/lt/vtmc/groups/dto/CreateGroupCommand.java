package lt.vtmc.groups.dto;

public class CreateGroupCommand {

	private String[] userList;

	private String description;

	private String groupName;

	private String[] docTypesToCreate;

	private String[] docTypesToSign;

	public CreateGroupCommand(String[] userList, String description, String groupName, String[] docTypesToSign,
			String[] docTypesToCreate) { //
		super();
		this.groupName = groupName;
		this.description = description;
		this.userList = userList;
		this.docTypesToCreate = docTypesToCreate;
		this.docTypesToSign = docTypesToSign;
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

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public String[] getDocTypesToCreate() {
		return docTypesToCreate;
	}

	public void setDocTypesToCreate(String[] docTypesToCreate) {
		this.docTypesToCreate = docTypesToCreate;
	}

	public String[] getDocTypesToSign() {
		return docTypesToSign;
	}

	public void setDocTypesToSign(String[] docTypesToSign) {
		this.docTypesToSign = docTypesToSign;
	}

}
