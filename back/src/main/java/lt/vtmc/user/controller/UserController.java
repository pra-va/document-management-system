package lt.vtmc.user.controller;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import lt.vtmc.documents.service.DocumentService;
import lt.vtmc.groups.service.GroupService;
import lt.vtmc.paging.PagingData;
import lt.vtmc.user.dto.CreateUserCommand;
import lt.vtmc.user.dto.UpdateUserCommand;
import lt.vtmc.user.dto.UserDetailsDTO;
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

	private static final Logger LOG = LoggerFactory.getLogger(UserController.class);

	@Autowired
	private UserService userService;

	@Autowired
	private GroupService groupService;

	@Autowired
	private DocumentService docService;

	/**
	 * Creates user with ADMIN role. Only system administrator should be able to
	 * access this method.
	 * 
	 * @url /api/createadmin
	 * @method POST }
	 * @param user details
	 */
	@Secured({ "ROLE_ADMIN" })
	@RequestMapping(path = "/api/createadmin", method = RequestMethod.POST)
	public ResponseEntity<String> createAdmin(@RequestBody CreateUserCommand command) {
		try {
			if (userService.findUserByUsername(command.getUsername()) == null & command.getPassword().length() > 7
					& command.getPassword().length() < 21) {
				userService.createSystemAdministrator(command.getUsername(), command.getName(), command.getSurname(),
						command.getPassword());
				groupService.addUserToGroupByUsername(command.getGroupList(), command.getUsername());

				LOG.info("# LOG # Initiated by [{}]: User [{}] with Admin role was created with group(s): [{}]#",
						SecurityContextHolder.getContext().getAuthentication().getName(), command.getUsername(),
						command.getGroupList());

				return new ResponseEntity<String>("Saved succesfully", HttpStatus.CREATED);
			}
		} catch (Exception e) {
			e.getStackTrace();
		}

		LOG.info("# LOG # Initiated by [{}]: User [{}] with Admin role was NOT created #",
				SecurityContextHolder.getContext().getAuthentication().getName(), command.getUsername());
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
	@Secured({ "ROLE_ADMIN" })
	@RequestMapping(path = "/api/createuser", method = RequestMethod.POST)
	public ResponseEntity<String> createUser(@RequestBody CreateUserCommand command) {

		try {
			if (userService.findUserByUsername(command.getUsername()) == null & command.getPassword().length() > 7
					& command.getPassword().length() < 21) {
				User tmpUser = userService.createUser(command.getUsername(), command.getName(), command.getSurname(),
						command.getPassword());
				groupService.addUserToGroupByUsername(command.getGroupList(), tmpUser.getUsername());

				LOG.info("# LOG # Initiated by [{}]: User [{}] was created with group(s): [{}]#",
						SecurityContextHolder.getContext().getAuthentication().getName(), command.getUsername(),
						command.getGroupList());
				return new ResponseEntity<String>("Saved succesfully", HttpStatus.CREATED);
			}
		} catch (Exception e) {
			e.getStackTrace();
		}

		LOG.info("# LOG # Initiated by [{}]: User [{}] was NOT created #",
				SecurityContextHolder.getContext().getAuthentication().getName(), command.getUsername());
		return new ResponseEntity<String>("Failed to create user", HttpStatus.CONFLICT);
	}

	@Secured({ "ROLE_ADMIN" })
	@RequestMapping(path = "/api/users", method = RequestMethod.POST)
	public Map<String, Object> listAllUsers(@RequestBody PagingData pagingData) {

		LOG.info("# LOG # Initiated by [{}]: requested list of all groups #",
				SecurityContextHolder.getContext().getAuthentication().getName());

		return userService.retrieveAllUsers(pagingData);
	}

	/**
	 * Finds and returns a user using username.
	 * 
	 * @url /api/user/{username}
	 * @method GET
	 */
	@Secured({ "ROLE_ADMIN", "ROLE_USER" })
	@GetMapping(path = "/api/user/{username}")
	public UserDetailsDTO findUserByUsername(@PathVariable("username") String username) {

		LOG.info("# LOG # Initiated by [{}]: searching for user [{}] #",
				SecurityContextHolder.getContext().getAuthentication().getName(), username);

		return new UserDetailsDTO(userService.findUserByUsername(username));
	}

	/**
	 * Deletes user from database
	 * 
	 * @url /api/delete/{username}
	 * @method DELETE
	 */
	@Secured({ "ROLE_ADMIN" })
	@DeleteMapping("/api/delete/{username}")
	public ResponseEntity<String> deleteUserByUsername(@PathVariable("username") String username) {
		User tmpUser = userService.findUserByUsername(username);
		if (tmpUser != null) {

			LOG.info("# LOG # Initiated by [{}]: User [{}] was deleted #",
					SecurityContextHolder.getContext().getAuthentication().getName(), username);

			userService.deleteUser(tmpUser);
			return new ResponseEntity<String>("Deleted succesfully", HttpStatus.OK);
		}

		LOG.info("# LOG # Initiated by [{}]: User [{}] was NOT deleted - [{}] was NOT found #",
				SecurityContextHolder.getContext().getAuthentication().getName(), username, username);

		return new ResponseEntity<String>("No user found", HttpStatus.NOT_FOUND);
	}

	/**
	 * Updates user information in the database
	 * 
	 * @url /api/user/update/{username}
	 * @method POST
	 */
	@Secured({ "ROLE_ADMIN" })
	@PostMapping(path = "/api/user/update/{username}")
	public ResponseEntity<String> updateUserByUsername(@PathVariable("username") String username,
			@RequestBody UpdateUserCommand command) {
		try {
			if (userService.findUserByUsername(username) != null) {
				userService.updateUserDetails(username, command.getName(), command.getSurname(), command.getPassword(),
						command.getRole());
				groupService.compareGroups(command.getGroupList(), username);

				LOG.info("# LOG # Initiated by [{}]: User [{}] was updated #",
						SecurityContextHolder.getContext().getAuthentication().getName(), username);

				return new ResponseEntity<String>("Updated succesfully", HttpStatus.ACCEPTED);
			}
		} catch (Exception e) {
			e.getStackTrace();
		}
		LOG.info("# LOG # Initiated by [{}]: User [{}] was NOT updated - [{}] was NOT found #",
				SecurityContextHolder.getContext().getAuthentication().getName(), username, username);

		return new ResponseEntity<String>("No user found", HttpStatus.NOT_FOUND);
	}

	@Secured({ "ROLE_ADMIN" })
	@GetMapping(path = "/api/{username}/exists")
	public boolean checkIfUserExists(@PathVariable("username") String username) throws Exception {
		if (userService.findUserByUsername(username) != null) {
			return true;
		} else {
			return false;
		}
	}

	@Secured({ "ROLE_USER", "ROLE_ADMIN" })
	@PostMapping(path = "/api/{username}/dtypescreate")
	public Map<String, Object> getUserDocTypesCreate(@PathVariable("username") String username,
			@RequestBody PagingData pagingData) {
		return userService.getUserDocTypesToCreate(username, pagingData);
	}

	@Secured({ "ROLE_USER", "ROLE_ADMIN" })
	@PostMapping(path = "/api/{username}/doctobesigned")
	public Map<String, Object> getDocumentsToBeSigned(@PathVariable("username") String username,
			@RequestBody PagingData pagingData) {
		return docService.findAllDocumentsToSignByUsername(username, pagingData);
	}

	@Secured({ "ROLE_USER", "ROLE_ADMIN" })
	@PostMapping(path = "/api/{username}/alldocuments")
	public Map<String, Object> getAllDocumentsByUsername(@PathVariable("username") String username,
			@RequestBody PagingData pagingData) {
		return docService.returnAllDocumentsByUsername(username, pagingData);
	}

	/**
	 * This method will create initial administrator. It only can be executed
	 * successfully if there is no user with administrator role in database.
	 * 
	 * @param command
	 * @return
	 */
	@PostMapping("/api/user/first/create")
	public ResponseEntity<String> createInitialAdmin(@RequestBody CreateUserCommand command) {
		boolean adminShouldBeCreated = userService.shouldCreateFirstUser();
		if (adminShouldBeCreated) {
			userService.createSystemAdministrator(command.getUsername(), command.getName(), command.getSurname(),
					command.getPassword());
			return new ResponseEntity<String>("Created succesfully.", HttpStatus.CREATED);
		} else {
			return new ResponseEntity<String>("Initial Admin is already created.", HttpStatus.METHOD_NOT_ALLOWED);
		}
	}

	/**
	 * Returns info if initial user needs to be created.
	 * 
	 * @return
	 */
	@GetMapping("/api/user/first")
	public ResponseEntity<String> shouldInitAdminBeCreated() {
		boolean dbContainsAdmin = userService.shouldCreateFirstUser();
		if (dbContainsAdmin) {
			return new ResponseEntity<String>("Initial user needs to be created.", HttpStatus.OK);
		} else {
			return new ResponseEntity<String>("Initial Admin is already created.", HttpStatus.IM_USED);
		}
	}
}
