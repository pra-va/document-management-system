package lt.vtmc.groups;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import lt.vtmc.groups.controller.GroupController;
import lt.vtmc.groups.model.Group;
import lt.vtmc.groups.service.GroupService;
import lt.vtmc.security.SecurityEntryPoint;
import lt.vtmc.user.service.UserService;

@RunWith(SpringJUnit4ClassRunner.class)
@WebMvcTest(value = GroupController.class)
public class GroupControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private SecurityEntryPoint securityEntryPoint;

	@MockBean
	private GroupService groupService;
	
	@MockBean
	private Group group;
	
	@MockBean
	private UserService userService;

	@Test
	public void testCreateGroup() throws Exception {
		Group mockGroup = new Group("testName", "testDescription");

		// groupService.createUser to respond back with mockGroup
		Mockito.when(groupService.createGroup(Mockito.anyString(), Mockito.anyString())).thenReturn(mockGroup);

		String exampleCreateGroupCommand = "{\"name\":\"testGroupName\",\"description\":\"testDescription\"}";

		// Send CreateGroupCommand as body to /api/creategroup
		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/creategroup")
				.accept(MediaType.APPLICATION_JSON).content(exampleCreateGroupCommand)
				.contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		MockHttpServletResponse response = result.getResponse();

		assertEquals(HttpStatus.CREATED.value(), response.getStatus());
	}
}
