package lt.vtmc.restApi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import lt.vtmc.restApi.dto.CreateUserCommand;
import lt.vtmc.restApi.service.UserService;

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

	/**
	 * Creates user with ADMIN role. Only system administrator should be able to
	 * access this method.
	 * 
	 * @url /api/createadmin
	 * @method POST
	 * @param user details
	 */
	@RequestMapping(path = "/api/createadmin", method = RequestMethod.POST)
	public ResponseEntity<String> createAdmin(@RequestBody CreateUserCommand command) {
		if (userService.findUserByUsername(command.getUsername()) == null) {
			userService.createSystemAdministrator(command.getUsername(), command.getPassword());
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
		if (userService.findUserByUsername(command.getUsername()) == null) { // creates a new user entity ONLY if there
																				// are no user in the database with the
																				// same username
			userService.createUser(command.getUsername(), command.getPassword());
			return new ResponseEntity<String>("Saved succesfully", HttpStatus.CREATED);
		} else
			return new ResponseEntity<String>("Failed to create user", HttpStatus.CONFLICT);
	}
}
