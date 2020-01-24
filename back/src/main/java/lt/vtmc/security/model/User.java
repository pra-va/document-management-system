package lt.vtmc.security.model;

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
	private String password;

	@Id
	@Size(min = 4)
	private String username;

	private String role;

	/**
	 * Constructor.
	 * 
	 * @param password
	 * @param username
	 * @param role
	 */
	public User(String password, String username, String role) {
		this.password = password;
		this.username = username;
		this.role = role;
	}

	/**
	 * Empty constructor.
	 */
	public User() {
	}

	/**
	 * 
	 * @return
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
	 * @return
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
	 * @return
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

}
