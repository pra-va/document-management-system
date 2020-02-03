package lt.vtmc.user.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import lt.vtmc.groups.service.GroupService;
import lt.vtmc.user.dto.CreateUserCommand;
import lt.vtmc.user.model.User;
import lt.vtmc.user.service.UserService;

/**
 * Controller for managing system users.
 * 
 *
 *
 */
@RestController
public class UserController {

	@Autowired
	private UserService userService;

	@Autowired
	private GroupService groupService;

	/**
	 * Creates user with ADMIN role. Only system administrator should be able to
	 * access this method.
	 * 
	 * @url /api/createadmin
	 * @method POST }
	 * @param user details
	 */
	@RequestMapping(path = "/api/createadmin", method = RequestMethod.POST)
	public ResponseEntity<String> createAdmin(@RequestBody CreateUserCommand command) {
		if (userService.findUserByUsername(command.getUsername()) == null) {
			userService.createSystemAdministrator(command.getUsername(), command.getName(), command.getSurname(),
					command.getPassword());
			if (command.getGroupList().length != 0) {
				groupService.addUserToGroup(command.getGroupList(), command.getUsername());
			}
			return new ResponseEntity<String>("Saved succesfully", HttpStatus.CREATED);
		} else
			return new ResponseEntity<String>("Failed to create user", HttpStatus.CONFLICT);
	}

	/**
	 * Creates user with USER role. Only system administrator should be able to
	 * access this method.
	 * 
	 * @url /api/createuser
	 * @method POST
	 * @param user details
	 */
	@RequestMapping(path = "/api/createuser", method = RequestMethod.POST)
	public ResponseEntity<String> createUser(@RequestBody CreateUserCommand command) {
		if (userService.findUserByUsername(command.getUsername()) == null) {
			userService.createUser(command.getUsername(), command.getName(), command.getSurname(),
					command.getPassword());
			if (command.getGroupList().length != 0) {
				groupService.addUserToGroup(command.getGroupList(), command.getUsername());
			}
			return new ResponseEntity<String>("Saved succesfully", HttpStatus.CREATED);
		} else
			return new ResponseEntity<String>("Failed to create user", HttpStatus.CONFLICT);
	}

	/**
	 * Finds and returns all users registered in the database.
	 * 
	 * @url /api/users
	 * @method GET
	 */
	@GetMapping(path = "/api/users")
	public List<User> listAllUsers() {
		return userService.retrieveAllUsers();
	}

	/**
	 * Finds and returns a user using username.
	 * 
	 * @url /api/user/{username}
	 * @method GET
	 */
	@GetMapping(path = "/api/user/{username}")
	public User findUserByUsername(@PathVariable("username") String username) {
		return userService.findUserByUsername(username);
	}

	/**
	 * Deletes user from database
	 * 
	 * @url /api/delete/{username}
	 * @method DELETE
	 */
	@DeleteMapping("/api/delete/{username}")
	public ResponseEntity<String> deleteUserByUsername(@RequestBody String username) {
		User tmpUser = userService.findUserByUsername(username);
		if (tmpUser != null) {
			userService.deleteUser(tmpUser);
			return new ResponseEntity<String>("Deleted succesfully", HttpStatus.OK);
		}
		return new ResponseEntity<String>("No user found", HttpStatus.NOT_FOUND);
	}

	/**
	 * Updates user information in the database database
	 * 
	 * @url /api/user/{username}
	 * @method POST
	 */
	@PostMapping(path = "/api/user/update/{username}")
	public ResponseEntity<String> updateUserByUsername(@RequestBody CreateUserCommand command, String username) {
		if (userService.findUserByUsername(command.getUsername()) != null) {
			userService.updateUserDetails(command.getUsername(), command.getName(), command.getSurname(),
					command.getPassword());
			return new ResponseEntity<String>("Updated succesfully", HttpStatus.ACCEPTED);
		}
		return new ResponseEntity<String>("No user found", HttpStatus.NOT_FOUND);
	}

	@GetMapping(path = "/api/{username}/exists")
	public boolean checkIfUserExists(@PathVariable("username") String username) {
		if (findUserByUsername(username) != null) {
			return true;
		}
		return false;
	}
}
