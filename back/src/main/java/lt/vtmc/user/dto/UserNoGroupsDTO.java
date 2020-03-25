package lt.vtmc.user.dto;

/**
 * This data transfer object should be used when there is need to transfer user
 * derails without the groups that this user belongs to.
 * 
 * @author pra-va
 *
 */
public class UserNoGroupsDTO {

	private String username;

	private String name;

	private String surname;

	private String role;

	public UserNoGroupsDTO(String username, String name, String surname, String role) {
		this.username = username;
		this.name = name;
		this.surname = surname;
		this.role = role;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
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

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}
}
