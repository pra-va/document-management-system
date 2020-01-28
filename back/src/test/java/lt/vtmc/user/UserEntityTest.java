package lt.vtmc.user;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import lt.vtmc.user.model.User;

@RunWith(SpringJUnit4ClassRunner.class)
public class UserEntityTest {

	@Test
	public void testUserEntity() throws Exception {

		User user = new User("testUsername", "testName", "testSurname", "testing123", "USER");
		assertNotNull(user);
		assertEquals(user.getUsername(), "testUsername");
		assertEquals(user.getName(), "testName");
		assertEquals(user.getSurname(), "testSurname");
		assertEquals(user.getPassword(), "testing123");
		assertEquals(user.getRole(), "USER");
	}
}
