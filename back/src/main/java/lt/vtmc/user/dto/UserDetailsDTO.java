package lt.vtmc.user.dto;

import lt.vtmc.user.model.User;

/**
 * This is DTO for user details. This method is used to retrieve user
 * information.
 * 
 * @author pra-va
 *
 */
public class UserDetailsDTO {

	private String username;

	private String name;

	private String surname;

	private String role;

	private String[] groupList;

	public UserDetailsDTO(User user) {
		super();
		this.username = user.getUsername();
		this.name = user.getName();
		this.surname = user.getSurname();
		this.role = user.getRole();
		this.groupList = filterUsersGroups(user);
	}

	private String[] filterUsersGroups(User user) {
		String[] groupNames = new String[user.getGroupList().size()];
		for (int i = 0; i < user.getGroupList().size(); i++) {
			groupNames[i] = user.getGroupList().get(i).getName();
		}
		return groupNames;
	}

	public String[] getGroupList() {
		return groupList;
	}

	public void setGroupList(String[] groupList) {
		this.groupList = groupList;
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
