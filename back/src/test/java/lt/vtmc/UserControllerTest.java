package lt.vtmc;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import lt.vtmc.restApi.controller.UserController;
import lt.vtmc.restApi.model.User;
import lt.vtmc.restApi.service.UserService;
import lt.vtmc.security.SecurityEntryPoint;

@RunWith(SpringJUnit4ClassRunner.class)
@WebMvcTest(value = UserController.class)
public class UserControllerTest {

	@Autowired private MockMvc mockMvc;
	
	@MockBean
	private SecurityEntryPoint securityEntryPoint;
	
	@MockBean
	private UserService userService;
	
	@MockBean
	private User user;
	
@Test
public void testCreateUser() throws Exception {
	User mockUser = new User("testUser", "testing123", "USER");

	// userService.createUser to respond back with mockUser
	Mockito.when(
			userService.createUser(Mockito.anyString(), Mockito.anyString())
			)
			.thenReturn(mockUser);
	
	String exampleCreateUserCommand = "{\"username\":\"testUser\",\"password\":\"testing123\"}";
	
	// Send CreateUserCommand as body to /api/createuser
	RequestBuilder requestBuilder = MockMvcRequestBuilders
			.post("/api/createuser")
			.accept(MediaType.APPLICATION_JSON)
			.content(exampleCreateUserCommand)
			.contentType(MediaType.APPLICATION_JSON);

	MvcResult result = mockMvc.perform(requestBuilder).andReturn();

	MockHttpServletResponse response = result.getResponse();

	assertEquals(HttpStatus.CREATED.value(), response.getStatus());
}

@Test
public void testCreateAdminUser() throws Exception {
	User mockUser = new User("testAdminUser", "testing123", "ADMIN");

	// userService.createUser to respond back with mockUser
	Mockito.when(
			userService.createUser(Mockito.anyString(), Mockito.anyString())
			)
			.thenReturn(mockUser);
	
	String exampleCreateUserCommand = "{\"username\":\"testAdmin\",\"password\":\"testing123\"}";
	
	// Send CreateUserCommand as body to /api/createadmin
	RequestBuilder requestBuilder = MockMvcRequestBuilders
			.post("/api/createadmin")
			.accept(MediaType.APPLICATION_JSON)
			.content(exampleCreateUserCommand)
			.contentType(MediaType.APPLICATION_JSON);

	MvcResult result = mockMvc.perform(requestBuilder).andReturn();

	MockHttpServletResponse response = result.getResponse();

	assertEquals(HttpStatus.CREATED.value(), response.getStatus());
}
}
