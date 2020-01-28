package lt.vtmc;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import lt.vtmc.user.service.UserService;

@SpringBootTest
class SpringSecurityApplicationTests {

	@Autowired
	private UserService userService;

	@Test
	void contextLoads() {
	}

	/**
	 * !!! Important !!! This method is for testing purpose only. DELETE IT in real
	 * life implementation. Creates user and system administrator for testing
	 * purpose.
	 */
	@Test
	public void createFakeUserAndSystemAdministrator() {
		userService.createSystemAdministrator("admin", "testName", "testSurname", "ADMIN");
		userService.createUser("user", "testName", "testSurname", "USER");
	}

}
