package lt.vtmc.user.dto;

/**
 * Create user command used by controller
 * 
 * lt.vtmc.user.controller.UserController#createAdmin(CreateUserCommand) and
 * lt.vtmc.user.controller.UserController#crerateUser(CreateUserCommand)
 * 
 * @author pra-va
 *
 */
public class CreateUserCommand {

	private String username;
	private String name;
	private String surname;
	private String password;
	private String[] groupList;

	public CreateUserCommand(String username, String name, String surname, String password, String[] groupList) {
		this.username = username;
		this.name = name;
		this.surname = surname;
		this.password = password;
		this.groupList = groupList;

	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String[] getGroupList() {
		return groupList;
	}

	public void setGroupList(String[] groupList) {
		this.groupList = groupList;
	}

}
