package lt.vtmc.user.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

/**
 * User entity for database.
 * 
 * @author pra-va
 *
 */
@Entity
@Table(name = "User")
public class User {

	@NotEmpty
	// @Size(min = 8)
	private String password;

	@Id
	@Size(min = 4)
	private String username;

	@NotEmpty(message = "Name field may not be empty")
	private String name;

	@NotEmpty(message = "Surname field may not be empty")
	private String surname;

	@NotEmpty(message = "Rple field may not be empty")
	private String role;

	/**
	 * Constructor.
	 * 
	 * @param password
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
	 * 
	 * @return role
	 */
	public String getRole() {
		return role;
	}

	/**
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
	@Override
	public String toString() {
		return "User [username=" + username + "password=" + password + ",role=" + role + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((password == null) ? 0 : password.hashCode());
		result = prime * result + ((role == null) ? 0 : role.hashCode());
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
		if (username == null) {
			if (other.username != null)
				return false;
		} else if (!username.equals(other.username))
			return false;
		return true;
	}

}
