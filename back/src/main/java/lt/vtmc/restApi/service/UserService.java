package lt.vtmc.restApi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lt.vtmc.restApi.dao.UserRepository;
import lt.vtmc.restApi.model.User;

/**
 * User service class to create and manipulate user instaces.
 * 
 * @author pra-va
 *
 */
@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;

	/**
	 * 
	 * This method finds users from user repository.
	 */
	public User findUserByUsername(String username) {
		return userRepository.findUserByUsername(username);
	}

	/**
	 * Method to create users.
	 * 
	 * @param username
	 * @param password
	 * @param role
	 * @return User
	 */
	@Transactional
	public User createUser(String username, String password) {
		User newUser = new User();
		newUser.setUsername(username);

		PasswordEncoder encoder = new BCryptPasswordEncoder();

		newUser.setPassword(encoder.encode(password));

		newUser.setRole("USER");
		return newUser;

	}

	/**
	 * Method to create system administrators.
	 * 
	 * @param username
	 * @param password
	 * @return User
	 */
	@Transactional
	public User createSystemAdministrator(String username, String password) {
		User newUser = new User();
		newUser.setUsername(username);

		PasswordEncoder encoder = new BCryptPasswordEncoder();

		newUser.setPassword(encoder.encode(password));

		newUser.setRole("ADMIN");
		return newUser;
	}

}
