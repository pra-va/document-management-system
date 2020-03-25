package test;

import static org.testng.Assert.assertTrue;

import java.io.IOException;

import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterGroups;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeGroups;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.mashape.unirest.http.exceptions.UnirestException;

import page.AdminNewUserPage;
import page.EditUserPage;
import page.LoginPage;
import page.MainPage;
import page.ProfilePage;
import page.UserListPage;
import utilities.API;
import utilities.GetSessionId;

public class EditUserTests extends AbstractTest {

	LoginPage loginPage;
	MainPage mainPage;
	UserListPage userListPage;
	EditUserPage editUserPage;
	AdminNewUserPage adminNewUserPage;
	ProfilePage profilePage;
	String sessionID;

	@Parameters({ "adminUserName", "adminPassword", "groupNameOne", "groupNameTwo", "newAdminFirstName", "newAdminLastName", "newAdminPassword",
			"newAdminUserName" })
	@BeforeClass
	public void preconditions(String adminUserName, String adminPassword, String groupNameOne, String groupNameTwo, String newAdminFirstName,
			String newAdminLastName, String newAdminPassword, String newAdminUserName) throws IOException {

		loginPage = new LoginPage(driver);
		mainPage = new MainPage(driver);
		userListPage = new UserListPage(driver);
		editUserPage = new EditUserPage(driver);
		adminNewUserPage = new AdminNewUserPage(driver);
		profilePage = new ProfilePage(driver);
		sessionID = GetSessionId.login(adminUserName, adminPassword);
		API.createGroup("Group One description", "[]", "[]", groupNameOne, "[]", sessionID);
		API.createGroup("Group Two description", "[]", "[]", groupNameTwo, "[]", sessionID);
		API.createAdmin("[\"" + groupNameOne + "\"]", newAdminFirstName, newAdminLastName, newAdminPassword,
				newAdminUserName, sessionID);
	}

	@Parameters({ "adminUserName", "adminPassword" })
	@BeforeGroups("editUser")
	public void login(String adminUserName, String adminPassword) {
		loginPage.sendKeysUserName(adminUserName);
		loginPage.sendKeysPassword(adminPassword);
		loginPage.clickButtonLogin();
	}

	@AfterGroups("editUser")
	public void logout() throws UnirestException {
		mainPage.waitForLogoutButton();
		mainPage.clickLogoutButton();
	}

	@Parameters({"adminUserName", "adminPassword", "groupNameOne", "groupNameTwo", "newAdminUserName" })
	@AfterClass
	public void deleteEntities(String adminUserName, String adminPassword ,String groupNameOne, String groupNameTwo, String newAdminUserName)
			throws IOException {
		sessionID = GetSessionId.login(adminUserName, adminPassword);
		API.deleteUser(newAdminUserName, sessionID);
		API.deleteGroup(groupNameOne, sessionID);
		API.deleteGroup(groupNameTwo, sessionID);
	}

	/**-	 
	 * Preconditions: <br>
	 * - one User  and two groups are created.<br>
	 * - admin is logged in the system.<br>
	 * 
	 * Test steps:<br>
	 * 1. Login to the system as an admin. <br>
	 * 2. Click "Admin" menu, "Users" option. <br>
	 * 3. Search for specific user and click "Edit / view" button.<br>
	 * 4. Fill fields in Edit user form: "First Name", "Last Name", check box "Update password", <br> 
	 *    fill field "Password", click "Yes" on "Admin" selection, search for a first group name, <br>
	 *    click on Group name in section "Add user to groups", clear search filed, search for second group, click on group name, <br>
	 *    click button "Update". <br>
	 * 5. Click "Admin" menu, "Users" option.<br> 
	 * 6. Search for Username. 	 <br>
	 *    Expected conditions: "First Name", "Last Name", "Role" on a list are displayed correctly. <br>	  
	 * 7. Click "Edit / View" button. <br>
	 * 8. Check if all properties ("First Name", "Last Name", "Role", groups) on edit page are displayed correctly. <br>
	 * 9. Click button "Cancel".<br>
	 * 10. Click button "Logout". <br>
	 * 11. Login to the system using new user's username and password, click button "Login".<br>
	 *     Expected results: all new user data on Profile Page is displayed correctly.<br>
	 */
	@Parameters({ "newAdminUserName", "newAdminPassword", "newAdminRole", "groupNameOne", "groupNameTwo", "updatedPassword" })
	@Test(groups = { "editUser" }, priority = 1, enabled = true)
	public void editUserTest(String newAdminUserName, String newAdminPassword, String newAdminRole, String groupNameOne,
			String groupNameTwo, String updatedPassword) throws InterruptedException {
		
		mainPage.clickAdminButton();
		mainPage.clickAdminUsersButton();
		userListPage.sendKeysSearchForUser(newAdminUserName);
		userListPage.clickViewEditSpecificUserButton(newAdminUserName);
		editUserPage.waitForEditUserPage();
		editUserPage.clearFirstNameFiel();
		editUserPage.sendKeysUpdateFirstName("newFirstName");
		editUserPage.clearLastNameFiel();
		editUserPage.sendKeysUpdateLastName("newLastName");
		editUserPage.checkUpdatePassword();
		editUserPage.sendKeysUpdatePassword(updatedPassword);
		editUserPage.clickUserRadio();
		editUserPage.sendKeysSearchGroups(groupNameOne);		
		editUserPage.waitForGroupNameVisibility(groupNameOne);		
		editUserPage.clickAddRemoveSpecificGroupButton(groupNameOne);			
		editUserPage.clearSearchGroupsField();		
		editUserPage.sendKeysSearchGroups(groupNameTwo);		
		editUserPage.waitForGroupNameVisibility(groupNameTwo);		
		editUserPage.clickAddRemoveSpecificGroupButton(groupNameTwo);			
		editUserPage.clickUpdateButton();
		mainPage.waitForLogoutButton();
		mainPage.clickAdminButton();
		mainPage.clickAdminUsersButton();
		userListPage.clearSearchUserFiels();
		userListPage.sendKeysSearchForUser(newAdminUserName);		
		assertTrue(userListPage.getFirstNameFromUserListByUsername(newAdminUserName).equals("newFirstName"),
				"User's First Name isn't displayed correctly in user list");
		assertTrue(userListPage.getLastNameFromUserListByUsername(newAdminUserName).equals("newLastName"),
				"User's Last Name isn't displayed correctly in user list");		
		assertTrue(userListPage.getRoleFromUserListByUsername(newAdminUserName).equals("USER"),
				"User's role isn't displayed correctly in user list");
		userListPage.clickViewEditSpecificUserButton(newAdminUserName);
		editUserPage.waitForEditUserPage();
		assertTrue(editUserPage.getFirstName().equals("newFirstName"),
				"User's First Name isn't displayed correctly in Edit user page");
		assertTrue(editUserPage.getLastName().equals("newLastName"),
				"User's Last Name isn't displayed correctly in Edit user page");
		assertTrue(editUserPage.isRadioButtonUserSelected(), "User's role isn't displayed correctly in Edit user page");
		editUserPage.waitForSearchFieldToBeAttached();
		editUserPage.sendKeysSearchGroups(groupNameTwo);		
		assertTrue(editUserPage.isUserAddedToGroup(groupNameTwo),
				"User was not added to the group correctly");		
		editUserPage.clickCancelButton();
		mainPage.waitForLogoutButton();
		mainPage.clickLogoutButton();
		loginPage.sendKeysUserName(newAdminUserName);
		loginPage.sendKeysPassword(updatedPassword);
		loginPage.clickButtonLogin();
		mainPage.clickProfileButton();
		profilePage.waitForHeaderUsernameVisibility();	
		assertTrue(profilePage.getTextFirstName().equals("newFirstName"),
				"User's First Name isn't displayed correctly in profile page");
		assertTrue(profilePage.getTextLastName().equals("newLastName"),
				"User's Last Name isn't displayed correctly in profile page");
		assertTrue(profilePage.getTextRole().contentEquals("USER"),
				"User's Role isn't displayed correctly in profile page");
		assertTrue(profilePage.getTextUserGroups().equals(groupNameTwo),
				"User's group isn't shown correctly in profile page");
		profilePage.clickButtonClose();		
	}
}