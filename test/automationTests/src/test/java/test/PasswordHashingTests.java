package test;

import static org.testng.Assert.assertFalse;

import java.io.IOException;

import org.testng.annotations.AfterClass;
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

	@Parameters({ "adminUserName", "adminPassword", "userUserName", })
	@AfterClass
	public void deleteEntities(String adminUserName, String adminPassword, String userUserName) throws IOException {
		sessionID = GetSessionId.login("admin", "adminadmin");
		API.deleteUser(userUserName, sessionID);
	}

	/**-	 
	 * Preconditions: new user is created.<br>
	 * 
	 * Test steps:<br>
	 * 1. Get API call response of user password by username.<br>
	 * Expected results: response is not equal to password.<br>
	 */

	@Parameters({ "userUserName", "userPassword" })
	@Test(groups = { "passwordHashingTests" }, priority = 1, enabled = true)
	public void passwordHashingTest(String userUserName, String userPassword) throws IOException {

		String hashedPassword = API.getPasswordFromDatabase(userUserName, sessionID);
		assertFalse(hashedPassword.equals(userPassword), "User password isn't hashed");
	}

}
