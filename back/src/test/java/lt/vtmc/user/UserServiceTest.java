package lt.vtmc.user;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import lt.vtmc.user.dao.UserRepository;
import lt.vtmc.user.model.User;
import lt.vtmc.user.service.UserService;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class UserServiceTest {

	@MockBean
	private User user;

	@MockBean
	private UserRepository userRepository;

	@Autowired
	private UserService userService;

	@Test
	public void testCreateUser() throws Exception {
		assertNotNull(userService.createUser("testUser", "testing123"));
		assertEquals(userService.createUser("testUser", "testing123").getUsername(), "testUser");
		assertEquals(userService.createUser("testUser", "testing123").getRole(), "USER");

		userRepository.deleteById("testUser");
	}

	@Test
	public void testCreateAdmin() throws Exception {
		assertNotNull(userService.createSystemAdministrator("testAdmin", "testing123"));
		assertEquals(userService.createSystemAdministrator("testAdmin", "testing123").getUsername(), "testAdmin");
		assertEquals(userService.createSystemAdministrator("testAdmin", "testing123").getRole(), "ADMIN");

		userRepository.deleteById("testAdmin");
	}

}
