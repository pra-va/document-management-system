package lt.vtmc.groups.dto;

public class CreateGroupCommand {

	private String[] userList;
	
	private String description;
	
	private String groupName;
	/**
	 * Constructor method for CreateGroupCommand
	 * 
	 * @param userList
	 * @param description
	 */
	public CreateGroupCommand(String[] userList, String groupName, String description) {
		super();
		this.groupName = groupName;
		this.description = description;
		this.userList = userList;
	}
	/**
	 * 
	 * @param name
	 */
	public String[] getUserList() {
		return userList;
	}
	/**
	 * 
	 * @return name
	 */
	public void setUserList(String[] userList) {
		this.userList = userList;
	}
	/**
	 * 
	 * @param description
	 */
	public String getDescription() {
		return description;
	}
	/**
	 * 
	 * @return description
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	public String getGroupName() {
		return groupName;
	}
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	
	
}
