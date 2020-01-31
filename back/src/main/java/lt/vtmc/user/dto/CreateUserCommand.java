package lt.vtmc.user.dto;

/**
 * Create user command used by controller
 * {@link lt.vtmc.user.controller.UserController#createAdmin(CreateUserCommand)}
 * and
 * {@link lt.vtmc.user.controller.UserController#crerateUser(CreateUserCommand)}
 * 
 * @author pra-va
 *
 */
public class CreateUserCommand {

	private String username;
	private String name;
	private String surname;
	private String password;
	private String[] names;

	/**
	 * Constructor method for CreateUserCommand
	 * 
	 * @param username
	 * @param password
	 */
	public CreateUserCommand(String username, String name, String surname, String password, String[] names) {
		this.username = username;
		this.name = name;
		this.surname = surname;
		this.password = password;
		this.names = names;

	}

	/**
	 * 
	 * @return username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * 
	 * @param username
	 */
	public void setUsername(String username) {
		this.username = username;
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
		return names;
	}
	/**
	 * 
	 * @param groupList
	 */
	public void setGroupList(String[] names) {
		this.names = names;
	}
	
	
}
