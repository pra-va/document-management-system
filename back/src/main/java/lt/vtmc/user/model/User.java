package lt.vtmc.user.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import lt.vtmc.documents.model.Document;
import lt.vtmc.groups.model.Group;

/**
 * User entity for database.
 * 
 * @author pra-va
 *
 */
@Entity
@Table(name = "Users")
public class User {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Size(min = 4)
	@Column(name = "username")
	private String username;

	@NotEmpty
	private String password;

	@NotEmpty(message = "Name field may not be empty")
	private String name;

	@NotEmpty(message = "Surname field may not be empty")
	private String surname;

	@NotEmpty(message = "Role field may not be empty")
	private String role;

	@ManyToMany(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	@JoinTable(name = "USERS_TO_GROUPS", joinColumns = @JoinColumn(name = "group_id"), inverseJoinColumns = @JoinColumn(name = "user_id"))
	private List<Group> groupList;

	@OneToMany(fetch = FetchType.LAZY)
	private List<Document> createdDocuments = new ArrayList<Document>();

	@OneToMany(fetch = FetchType.LAZY)
	private List<Document> processedDocuments = new ArrayList<Document>();

	/**
	 * Constructor.
	 * 
	 * @param passwordGroups
	 * @param username
	 * @param role
	 */
	public User(String username, String name, String surname, String password, String role) {
		this.username = username;
		this.name = name;
		this.surname = surname;
		this.password = password;
		this.role = role;
	}

	/**
	 * Empty constructor.
	 */
	public User() {
	}

	public List<Document> getCreatedDocuments() {
		return createdDocuments;
	}

	public void setCreatedDocuments(List<Document> createdDocuments) {
		this.createdDocuments = createdDocuments;
	}

	public List<Document> getProcessedDocuments() {
		return processedDocuments;
	}

	public void setProcessedDocuments(List<Document> processedDocuments) {
		this.processedDocuments = processedDocuments;
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

	public void/**
				 * 
				 * @param surname
				 */
			setSurname(String surname) {
		this.surname = surname;
	}

	/**
	 * Set
	 * 
	 * @return role
	 */
	public String getRole() {
		return role;
	}

	/**
	 * Groups
	 * 
	 * @param role
	 */
	public void setRole(String role) {
		this.role = role;
	}

	/**
	 * 
	 * @return
	 */
	public List<Group> getGroupList() {
		return groupList;
	}

	/**
	 * 
	 * @param groupList
	 */
	public void setGroupList(List<Group> groupList) {
		this.groupList = groupList;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((groupList == null) ? 0 : groupList.hashCode());
		result = prime * result + id;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((password == null) ? 0 : password.hashCode());
		result = prime * result + ((role == null) ? 0 : role.hashCode());
		result = prime * result + ((surname == null) ? 0 : surname.hashCode());
		result = prime * result + ((username == null) ? 0 : username.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		if (groupList == null) {
			if (other.groupList != null)
				return false;
		} else if (!groupList.equals(other.groupList))
			return false;
		if (id != other.id)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (password == null) {
			if (other.password != null)
				return false;
		} else if (!password.equals(other.password))
			return false;
		if (role == null) {
			if (other.role != null)
				return false;
		} else if (!role.equals(other.role))
			return false;
		if (surname == null) {
			if (other.surname != null)
				return false;
		} else if (!surname.equals(other.surname))
			return false;
		if (username == null) {
			if (other.username != null)
				return false;
		} else if (!username.equals(other.username))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "User [password=" + password + ", id=" + id + ", username=" + username + ", name=" + name + ", surname="
				+ surname + ", role=" + role + ", groupList=" + groupList + "]";
	}

	/**
	 * 
	 * @return
	 */

}
