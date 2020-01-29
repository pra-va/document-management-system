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

	@GetMapping("/")
	public String helloAll() {
		return "Hello, everybody!";
	}
}
