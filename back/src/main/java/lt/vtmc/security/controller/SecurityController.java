package lt.vtmc.security.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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

	/**
	 * This method will return logged in users username.
	 * 
	 * @url /api/loggedin
	 * @method GET
	 * @return username or "not logged".
	 */
	@RequestMapping(path = "/api/loggedin", method = RequestMethod.GET)
	public String getLoggedInUsername() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (!(authentication instanceof AnonymousAuthenticationToken)) {
			String currentUsername = authentication.getName();
			return currentUsername;
		}
		return "Not logged in.";
	}
}
