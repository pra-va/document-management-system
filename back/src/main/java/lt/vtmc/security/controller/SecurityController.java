package lt.vtmc.security.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import lt.vtmc.security.dto.CreateUserCommand;
import lt.vtmc.security.service.UserService;

/**
 * Security controller for system users.
 * 
 * @author pra-va
 *
 */
@RestController
public class SecurityController {
	@Autowired
	NamedParameterJdbcTemplate jdbcTemplate;

	@Autowired
	private UserService userService;

	/**
	 * This method will return logged in users username.
	 * 
	 * @url /api/loggedAdmin
	 * @method GET
	 * @return username or "not logged".
	 */
	@RequestMapping(path = "/api/loggedAdmin", method = RequestMethod.GET)
	public String getLoggedInUsername() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (!(authentication instanceof AnonymousAuthenticationToken)) {
			String currentUsername = authentication.getName();
			return currentUsername;
		}
		return "Not logged in.";
	}

	/**
	 * Creates user with ADMIN role. Only system administrator should be able to
	 * access this method.
	 * 
	 * @url /api/createadmin
	 * @method POST
	 * @param user details
	 */
	@RequestMapping(path = "/api/createadmin", method = RequestMethod.POST)
	public void createAdmin(@RequestBody CreateUserCommand command) {
		userService.createSystemAdministrator(command.getUsername(), command.getPassword());
	}

	/**
	 * Creates user with ADMIN role. Only system administrator should be able to
	 * access this method.
	 * 
	 * @url /api/createadmin
	 * @method POST
	 * @param user details
	 */
	@RequestMapping(path = "/api/createuser", method = RequestMethod.POST)
	public void createUser(@RequestBody CreateUserCommand command) {
		userService.createUser(command.getUsername(), command.getPassword());
	}

}
