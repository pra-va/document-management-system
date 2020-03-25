package lt.vtmc.user.dto;

/**
 * Create user command used by controller
 * 
 * lt.vtmc.user.controller.UserController#createAdmin(CreateUserCommand2) and
 * lt.vtmc.user.controller.UserController#crerateUser(CreateUserCommand2)
 * 
 * @author pra-va
 *
 */
public class UpdateUserCommand {

	private String name;
	private String surname;
	private String password;
	private String[] groupList;
	private String role; // TODO should be ENUM

	public UpdateUserCommand(String name, String surname, String password, String[] groupList, String role) {
		this.name = name;
		this.surname = surname;
		this.password = password;
		this.groupList = groupList;
		this.role = role;
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

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

}
