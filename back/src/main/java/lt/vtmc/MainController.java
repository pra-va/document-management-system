package lt.vtmc;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Main controller class.
 * 
 * @author pra-va
 *
 */
@RestController
public class MainController {

	@GetMapping("/api/user")
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	public String helloUser() {
		return "Hello, User!";
	}

	@GetMapping("/api/admin")
	@PreAuthorize("hasRole('ADMIN')")
	public String helloAdmin() {
		return "Hello, Admin!";
	}

}
