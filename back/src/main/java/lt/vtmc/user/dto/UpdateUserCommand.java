package lt.vtmc.user.dto;

/**
 * Create user command used by controller
 * {@link lt.vtmc.user.controller.UserController#createAdmin(CreateUserCommand2)}
 * and
 * {@link lt.vtmc.user.controller.UserController#crerateUser(CreateUserCommand2)}
 * 
 * @author pra-va
 *
 */
public class UpdateUserCommand {

	private String name;
	private String surname;
	private String password;
	private String[] groupList;

	/**
	 * Constructor method for CreateUserCommand
	 * 
	 * @param username
	 * @param password
	 */
	public UpdateUserCommand(String name, String surname, String password, String[] groupList) {
		this.name = name;
		this.surname = surname;
		this.password = password;
		this.groupList = groupList;

	}
	/**
	 * 
	 * @return password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * 
	 * @param password
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * 
	 * @return name
	 */
	public String getName() {
		return name;
	}
	/**
	 * 
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * 
	 * @return surname
	 */
	public String getSurname() {
		return surname;
	}
	/**
	 * 
	 * @param surname
	 */
	public void setSurname(String surname) {
		this.surname = surname;
	}
	/**
	 * 
	 * @return groupList
	 */
	public String[] getGroupList() {
		return groupList;
	}
	/**
	 * 
	 * @param groupList
	 */
	public void setGroupList(String[] groupList) {
		this.groupList = groupList;
	}
	
	
}
