package lt.vtmc;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import lt.vtmc.security.service.UserService;

@SpringBootTest
class SpringSecurityApplicationTests {

	@Autowired
	private UserService userService;

	@Test
	void contextLoads() {
	}

	@Test
	void createTestUsers() {
		userService.createUser("user", "user", "USER");
		userService.createUser("admin", "admin", "ADMIN");
	}

}
