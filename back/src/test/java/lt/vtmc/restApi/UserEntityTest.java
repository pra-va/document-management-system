package lt.vtmc.restApi;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import lt.vtmc.restApi.model.User;

@RunWith(SpringJUnit4ClassRunner.class)
public class UserEntityTest {
	
	@Test
	public void testUserEntity() throws Exception {
		
		User user = new User("testUser", "testing123", "USER");
		assertNotNull(user);
		assertEquals(user.getUsername(), "testUser");
		assertEquals(user.getPassword(), "testing123");
		assertEquals(user.getRole(), "USER");
		assertEquals(user.toString(), "User [username=" + "testUser" + "password=" + "testing123" + ",role=" + "USER" + "]");
	}
}
