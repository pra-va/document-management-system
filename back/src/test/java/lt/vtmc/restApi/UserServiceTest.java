package lt.vtmc.restApi;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.mock.mockito.MockBean;

import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import lt.vtmc.restApi.dao.UserRepository;
import lt.vtmc.restApi.model.User;
import lt.vtmc.restApi.service.UserService;

@RunWith(SpringJUnit4ClassRunner.class)
public class UserServiceTest {
	
	@MockBean
	private User user;
	
	@MockBean
	private UserRepository userRepository;
	
	@Test
	public void testCreateUser() throws Exception {
		UserService userService = new UserService();
		
		assertNotNull(userService.createUser("testUser", "testing123"));
		assertEquals(userService.createUser("testUser", "testing123").getUsername(), "testUser");
		assertEquals(userService.createUser("testUser", "testing123").getRole(), "USER");
	}
	
	@Test
	public void testCreateAdmin() throws Exception {
		UserService userService = new UserService();
		
		assertNotNull(userService.createSystemAdministrator("testAdmin", "testing123"));
		assertEquals(userService.createSystemAdministrator("testAdmin", "testing123").getUsername(), "testAdmin");
		assertEquals(userService.createSystemAdministrator("testAdmin", "testing123").getRole(), "ADMIN");
	}
}
