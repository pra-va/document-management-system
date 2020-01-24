package lt.vtmc.security.dto;

/**
 * Create user command used by controller
 * {@link lt.vtmc.security.controller.SecurityController#createAdmin(CreateUserCommand)}
 * and
 * {@link lt.vtmc.security.controller.SecurityController#crerateUser(CreateUserCommand)}
 * 
 * @author pra-va
 *
 */
public class CreateUserCommand {

	private String username;
	private String password;

	/**
	 * Constructor method for CreateUserCommand
	 * 
	 * @param username
	 * @param password
	 */
	public CreateUserCommand(String username, String password) {
		this.username = username;
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

}
