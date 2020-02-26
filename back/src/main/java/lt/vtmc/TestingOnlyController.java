package lt.vtmc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import lt.vtmc.user.dao.UserRepository;

@RestController
public class TestingOnlyController {

	@Autowired
	private UserRepository userRepo;
	
	@GetMapping(path = "/api/testingonly/returnpass/{username}")
	public String returnHashedPassword(@PathVariable ("username") String username) {
		return userRepo.findUserByUsername(username).getPassword();
	}
}
