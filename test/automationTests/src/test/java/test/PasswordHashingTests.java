package test;

import static org.testng.Assert.assertFalse;

import java.io.IOException;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import utilities.API;
import utilities.GetSessionId;

public class PasswordHashingTests extends AbstractTest {

	String sessionID;

	@Parameters({ "userFirstName", "userLastName", "userPassword", "userUserName" })
	@BeforeClass
	public void preconditions(String userFirstName, String userLastName, String userPassword, String userUserName)
			throws IOException {
		sessionID = GetSessionId.login("admin", "adminadmin");
		API.createUser("[]", userFirstName, userLastName, userPassword, userUserName, sessionID);
	}

	/*-
	 * Test checks if:
	 *   - new User Password is hashed.
	 * Preconditions:  
	 *   - new user is created.
	 *   
	 * Test steps:
	 * 1. Get API call response of user password by username.
	 * 2. Check if response is not equal to password.
	 */

	@Parameters({ "userUserName", "userPassword" })
	@Test(groups = { "passwordHashingTests" }, priority = 1, enabled = true)
	public void passwordHashingTest(String userUserName, String userPassword) throws IOException {
			
		String hashedPassword = API.getPasswordFromDatabase(userUserName, sessionID);	
		assertFalse(hashedPassword.equals(userPassword), "User password isn't hashed");
	}

	/*-
	 * Test checks if:
	 *   - User Password is hashed after edition.
	 * Preconditions: 
	 *   - admin is logged in the system (API URL's are locked for other users);
	 *   - user was created and his/her password was edited.
	 * Test steps:
	 * 1. Edit user password.
	 * 2. Get HTTP response of user password by username.
	 * 3. Check if HTTP response is not equal to password.
	 */
	@Test(groups = { "passwordHashingTests" }, priority = 1, enabled = false)
	public void editedPasswordHashingTest() throws IOException {

//		String hashedPassword = Unirest.get("http://akademijait.vtmc.lt:8180/dvs/api/testingonly/returnpass/{username}")
//				.routeParam("username", adminUserName).asString().getBody();
//
//		assertFalse(hashedPassword.equals(adminPassword), "Edited password isn't hashed");

		// String inputJson = {

		// }

	}

}
