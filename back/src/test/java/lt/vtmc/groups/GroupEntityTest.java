package lt.vtmc.groups;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import lt.vtmc.groups.model.Group;

@RunWith(SpringJUnit4ClassRunner.class)
public class GroupEntityTest {

	@Test
	public void testUserEntity() throws Exception {
		Group group = new Group("testName", "testDescription");
		assertNotNull(group);
		assertEquals(group.getName(), "testName");
		assertEquals(group.getDescription(), "testDescription");
	}
}
