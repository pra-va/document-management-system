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

	/**
	 * Constructor method for CreateUserCommand
	 * 
	 * @param username
	 * @param password
	 */
	public CreateUserCommand(String username, String name, String surname, String password) {
		this.username = username;
		this.name = name;
		this.surname = surname;
		this.password = password;

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
	 * @param name
	 */
	public String getName() {
		return name;
	}
	/**
	 * 
	 * @return name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * 
	 * @param surname
	 */
	public String getSurname() {
		return surname;
	}
	/**
	 * 
	 * @return surname
	 */
	public void setSurname(String surname) {
		this.surname = surname;
	}
	
	
}
