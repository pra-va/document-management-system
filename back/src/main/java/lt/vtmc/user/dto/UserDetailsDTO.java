package lt.vtmc.user.dto;

import lt.vtmc.user.model.User;

public class UserDetailsDTO {

	private String username;

//	private String password;

	private String name;

	private String surname;

	private String role;

	private String[] gruopList;

//	@JsonIgnore
//	private List<Group> groupList;

	/**
	 * Constructor method for GroupDetails
	 * 
	 * @param username
	 * @param password
	 * @param name
	 * @param surname
	 * @param role
	 * @param groupList
	 */

	public UserDetailsDTO(User user) {
		super();
		this.username = user.getUsername();
//		this.password = user.getPassword();
		this.name = user.getName();
		this.surname = user.getSurname();
		this.role = user.getRole();
		this.gruopList = filterUsersGroups(user);
//		this.groupList = user.getGroupList();
	}

	private String[] filterUsersGroups(User user) {
		String[] groupNames = new String[user.getGroupList().size()];
		for (int i = 0; i < user.getGroupList().size(); i++) {
			groupNames[i] = user.getGroupList().get(i).getName();
		}
		return groupNames;
	}

	public String[] getGruopList() {
		return gruopList;
	}

	public void setGruopList(String[] gruopList) {
		this.gruopList = gruopList;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

//	public String getPassword() {
//		return password;
//	}
//
//	public void setPassword(String password) {
//		this.password = password;
//	}

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

//	public List<Group> getGroupListArray() {
//		return groupList;
//	}

//	public String[] getGroupList() {
//		String[] groupNames = new String[groupList.size()];
//		for (int i = 0; i < groupList.size(); i++) {
//			groupNames[i] = groupList.get(i).getName();
//		}
//		return groupNames;
//	}

//	public void setGroupList(List<Group> groupList) {
//		this.groupList = groupList;
//	}

}
