package lt.vtmc;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import lt.vtmc.groups.service.GroupService;
import lt.vtmc.user.service.UserService;

@SpringBootTest
class SpringSecurityApplicationTests {

	@Autowired
	private UserService userService;

	@Autowired
	private GroupService groupService;

	@Test
	void contextLoads() {
	}

	/**
	 * !!! Important !!! These methods are for testing purposes only. DELETE THEM in
	 * real life implementation. Creates user, groups and system administrator for
	 * testing purpose.
	 */
	@Test
	public void createFakeUserAndSystemAdministrator() {
		if (userService.findUserByUsername("admin") == null) {
			userService.createSystemAdministrator("admin", "testName", "testSurname", "adminadmin");
			userService.createUser("user", "testName", "testSurname", "useruser");
		}
	}

	@Test
	public void createFakeGroups() {
		if (groupService.findGroupByName("dummy1") == null) {
			groupService.createGroup("dummy1", "testDescription");
			groupService.createGroup("dummy2", "testDescription");
			groupService.createGroup("dummy3", "testDescription");
			groupService.createGroup("dummy4", "testDescription");
			groupService.createGroup("dummy5", "testDescription");
			groupService.createGroup("dummy6", "testDescription");
			groupService.createGroup("dummy11", "testDescription");
			groupService.createGroup("dummy21", "testDescription");
			groupService.createGroup("dummy31", "testDescription");
			groupService.createGroup("dummy41", "testDescription");
			groupService.createGroup("dummy51", "testDescription");
			groupService.createGroup("dummy61", "testDescription");
		}
	}
}
