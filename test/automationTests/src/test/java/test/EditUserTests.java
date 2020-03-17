package test;

import static org.testng.Assert.assertTrue;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeGroups;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.mashape.unirest.http.exceptions.UnirestException;
import com.thoughtworks.xstream.XStream;

import page.AdminNewGroupPage;
import page.AdminNewUserPage;
import page.EditUserPage;
import page.LoginPage;
import page.MainPage;
import page.ProfilePage;
import page.UserListPage;

public class EditUserTests extends AbstractTest {

	LoginPage loginPage;
	MainPage mainPage;
	UserListPage userListPage;
	EditUserPage editUserPage;
	AdminNewUserPage adminNewUserPage;
	ProfilePage profilePage;


	@BeforeClass
	public void preconditions() throws IOException {
		loginPage = new LoginPage(driver);
		mainPage = new MainPage(driver);
		userListPage = new UserListPage(driver);
		editUserPage = new EditUserPage(driver);
		adminNewUserPage = new AdminNewUserPage(driver);
		profilePage = new ProfilePage(driver);
	}

	@Parameters({ "adminUserName", "adminPassword" })
	@BeforeGroups("editUser")
	public void login(String adminUserName, String adminPassword) {
		loginPage.sendKeysUserName(adminUserName);
		loginPage.sendKeysPassword(adminPassword);
		loginPage.clickButtonLogin();
	}

	// TODO EDIT!!!
	// before test create user and two groups
	//delete user and groups after test

	/*-
	 * Test edits user properties, checks if all properties are saved correctly in user list, 
	 * "Edit user" page and "Profile" page, checks logins to the system with new credentials. 
	 * 1. Login to the system as an admin. 
	 * 2. Click "Admin" menu, "Users" option. 
	 * 3. Search for specific user and click "Edit / view" page.
	 * 4. Fill fields in Edit user form: "First Name", "Last Name", check box "Update password",  fill field "Password", click "Yes" on "Admin" selection, search for a group name 
	 * in section "Add user to groups", click button "Add", search for a group name in section "User's groups" click button "Remove", click button "Submit". 
	 * 5. Click "Admin" menu, "Users" option. 
	 * 6. Search for edited Username. 
	 * 7. Check if new properties ("First Name", "Last Name", "Username", "Role") on a list are displayed correctly. 
	 * 8. Click "Edit / View" button. 
	 * 9. Check if all properties ("First Name", "Last Name", "Username", "Role", groups) are displayed correctly. 
	 * 10. Click button "Cancel".
	 * 11. Click button "Logout". 
	 * 12. Login to the system using new user's username and password, click button "Login".	 
	 * 13. Check if all new user data on Profile Page is displayed correctly;
	 */
	@Parameters({ "newAdminFirstName", "newAdminLastName", "newAdminUserName", "newAdminPassword", "newAdminRole",
			"groupOne" })
	@Test(groups = { "editUser" }, priority = 1, enabled = true)
	public void editUserTest(String newAdminFirstName, String newAdminLastName, String newAdminUserName,
			String newAdminPassword, String newAdminRole, String groupOne) {

		mainPage.clickAdminButton();
		mainPage.clickAdminNewUserButton();
		adminNewUserPage.sendKeysFirstName(newAdminFirstName);
		adminNewUserPage.sendKeysLastName(newAdminLastName);
		adminNewUserPage.sendKeysUserName(newAdminUserName);
		adminNewUserPage.sendKeysPassword(newAdminPassword);
		adminNewUserPage.clickAdminRadio();
		adminNewUserPage.sendKeysSearchGroup(groupOne);
		adminNewUserPage.clickAddRemoveSpecificGroupButton(groupOne);
		adminNewUserPage.clickCreateButton();
		mainPage.clickAdminButton();
		mainPage.clickAdminUsersButton();
		userListPage.sendKeysSearchForUser(newAdminUserName);
		userListPage.clickViewEditSpecificUserButton(newAdminUserName);
		editUserPage.sendKeysUpdateFirstName("newFirstName");
		editUserPage.sendKeysUpdateLastName("newLastName");
		editUserPage.checkUpdatePassword();
		editUserPage.sendKeysUpdatePassword("aaaaaaaaaaa");
		editUserPage.clickUserRadio();
		//search for group
		editUserPage.clickAddRemoveSpecificGroupButton(groupOne);
		// editUserPage.clickAddRemoveSpecificGroupButton("someGroup");
		editUserPage.clickUpdateButton();
		mainPage.clickAdminUsersButton();
		userListPage.sendKeysSearchForUser(newAdminUserName);
		assertTrue(userListPage.getFirstNameFromUserListByUsername(newAdminUserName).equals("newFirstName"),
				"User's First Name isn't displayed correctly in user list");
		assertTrue(userListPage.getLastNameFromUserListByUsername(newAdminUserName).equals("newLastName"),
				"User's Last Name isn't displayed correctly in user list");
		assertTrue(userListPage.getRoleFromUserListByUsername(newAdminUserName).equals("ADMIN"),
				"User's role isn't displayed correctly in user list");
		userListPage.clickViewEditSpecificUserButton(newAdminUserName);
		editUserPage.waitForEditUserPage();
		assertTrue(editUserPage.getFirstName().equals("newFirstName"),
				"User's First Name isn't displayed correctly in Edit user page");
		assertTrue(editUserPage.getLastName().equals("newLastName"),
				"User's Last Name isn't displayed correctly in Edit user page");
		assertTrue(editUserPage.isRadioButtonUserSelected(), "User's role isn't displayed correctly in Edit user page");
		editUserPage.sendKeysSearchGroups(groupOne);
		assertTrue(driver.findElement(By.xpath("//td[contains(text(), '" + groupOne + "')]")).isDisplayed(),
				"User was not added to the group correctly");		
		editUserPage.clickCancelButton();
		mainPage.clickLogoutButton();
		loginPage.sendKeysUserName(newAdminUserName);
		loginPage.sendKeysPassword("aaaaaaaaaaa");
		loginPage.clickButtonLogin();
		mainPage.clickProfileButton();
		profilePage.waitForHeaderUsernameVisibility();
//		assertTrue(profilePage.getTextUsername().equals(newUserUserName),
//				"User's Username isn't displayed correctly in profile page");
		assertTrue(profilePage.getTextFirstName().equals("newFirstName"),
				"User's First Name isn't displayed correctly in profile page");
		assertTrue(profilePage.getTextLastName().equals("newLastName"),
				"User's Last Name isn't displayed correctly in profile page");
		assertTrue(profilePage.getTextRole().contentEquals("ADMIN"),
				"User's Role isn't displayed correctly in profile page");
		assertTrue(profilePage.getTextUserGroups().equals(groupOne),
				"User's group isn't shown correctly in profile page");
		profilePage.clickButtonClose();
		mainPage.clickLogoutButton();

	}
}