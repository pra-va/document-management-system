package lt.vtmc.groups;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import lt.vtmc.groups.dao.GroupRepository;
import lt.vtmc.groups.model.Group;
import lt.vtmc.groups.service.GroupService;
import lt.vtmc.user.dao.UserRepository;
import lt.vtmc.user.model.User;
import lt.vtmc.user.service.UserService;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class GroupServiceTest {

	@MockBean
	private Group group;

	@MockBean
	private GroupRepository groupRepository;

	@Autowired
	private GroupService groupService;
	
	@Test
	public void testCreateGroup() throws Exception {
		assertNotNull(groupService.createGroup("testName", "testing123"));
		assertEquals(groupService.createGroup("testName", "testing123").getName(),
				"testName");
		assertEquals(groupService.createGroup("testName", "testing123").getDescription(),
				"testing123");
		groupRepository.deleteById("testName");
	}
	
	
}
