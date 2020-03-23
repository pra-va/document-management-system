package test;

import static org.testng.Assert.assertTrue;

import java.io.IOException;

import org.openqa.selenium.By;
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
import utilities.API;

public class EditUserTests extends AbstractTest {

	LoginPage loginPage;
	MainPage mainPage;
	UserListPage userListPage;
	EditUserPage editUserPage;
	AdminNewUserPage adminNewUserPage;
	ProfilePage profilePage;

	@Parameters({ "groupNameOne", "groupNameTwo", "newAdminFirstName", "newAdminLastName", "newAdminPassword",
			"newAdminUserName" })
	@BeforeClass
	public void preconditions(String groupNameOne, String groupNameTwo, String newAdminFirstName,
			String newAdminLastName, String newAdminPassword, String newAdminUserName) throws IOException {

		loginPage = new LoginPage(driver);
		mainPage = new MainPage(driver);
		userListPage = new UserListPage(driver);
		editUserPage = new EditUserPage(driver);
		adminNewUserPage = new AdminNewUserPage(driver);
		profilePage = new ProfilePage(driver);
		//API.createGroup("Group One description", "[\"\"]", "[\"\"]", groupNameOne, "[\"\"]");
		//API.createGroup("Group Two description", "[\"\"]", "[\"\"]", groupNameTwo, "[\"\"]");
		//API.createAdmin("[\"" + groupNameOne + "\"]", newAdminFirstName, newAdminLastName, newAdminPassword,
				//newAdminUserName);

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
	// delete user and groups after test

	/*-
	 * Test edits user properties, checks if all properties are saved correctly in user list, 
	 * "Edit user" page and "Profile" page, checks logins to the system with new credentials. 
	 * 
	 * Preconditions: 
	 * - one User with Admin role and Two groups are created.
	 * 
	 * Test steps:
	 * 1. Login to the system as an admin. 
	 * 2. Click "Admin" menu, "Users" option. 
	 * 3. Search for specific user and click "Edit / view" button.
	 * 4. Fill fields in Edit user form: "First Name", "Last Name", check box "Update password",  
	 * fill field "Password", click "Yes" on "Admin" selection, search for a first group name, 
	 * click on Group name in section "Add user to groups", clear search filed, search for second group, click on group name, 
	 * click button "Submit". 
	 * 5. Click "Admin" menu, "Users" option. 
	 * 6. Search for Username. 
	 * 7. Check if new properties ("First Name", "Last Name", "Role") on a list are displayed correctly. 
	 * 8. Click "Edit / View" button. 
	 * 9. Check if all properties ("First Name", "Last Name", "Role", groups) are displayed correctly. 
	 * 10. Click button "Cancel".
	 * 11. Click button "Logout". 
	 * 12. Login to the system using new user's username and password, click button "Login".	 
	 * 13. Check if all new user data on Profile Page is displayed correctly.
	 */
	@Parameters({ "newAdminUserName", "newAdminPassword", "newAdminRole", "groupNameOne", "groupNameTwo" })
	@Test(groups = { "editUser" }, priority = 1, enabled = true)
	public void editUserTest(String newAdminUserName, String newAdminPassword, String newAdminRole, String groupNameOne,
			String groupNameTwo) throws InterruptedException {

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
		editUserPage.sendKeysUpdatePassword("aaaaaaaaaaa");
		editUserPage.clickUserRadio();
		editUserPage.sendKeysSearchGroups(groupNameOne);
		Thread.sleep(2000);
		editUserPage.waitForGroupNameVisibility(groupNameOne);
		Thread.sleep(2000);
		editUserPage.clickAddRemoveSpecificGroupButton(groupNameOne);
		Thread.sleep(2000);
		//editUserPage.waitForGroupNameSelection(groupNameOne);	
		Thread.sleep(2000);
		editUserPage.clearSearchGroupsField();
		Thread.sleep(2000);
		editUserPage.sendKeysSearchGroups(groupNameTwo);
		Thread.sleep(2000);
		editUserPage.waitForGroupNameVisibility(groupNameTwo);
		Thread.sleep(2000);
		editUserPage.clickAddRemoveSpecificGroupButton(groupNameTwo);
		Thread.sleep(2000);
		//editUserPage.waitForGroupNameSelection(groupNameTwo);
		Thread.sleep(4000);
		editUserPage.clickUpdateButton();
		//mainPage.clickAdminUsersButton();
		//userListPage.sendKeysSearchForUser(newAdminUserName);
		assertTrue(userListPage.getFirstNameFromUserListByUsername(newAdminUserName).equals("newFirstName"),
				"User's First Name isn't displayed correctly in user list");
		assertTrue(userListPage.getLastNameFromUserListByUsername(newAdminUserName).equals("newLastName"),
				"User's Last Name isn't displayed correctly in user list");
		Thread.sleep(4000);
		assertTrue(userListPage.getRoleFromUserListByUsername(newAdminUserName).equals("USER"),
				"User's role isn't displayed correctly in user list");
		userListPage.clickViewEditSpecificUserButton(newAdminUserName);
		editUserPage.waitForEditUserPage();
		assertTrue(editUserPage.getFirstName().equals("newFirstName"),
				"User's First Name isn't displayed correctly in Edit user page");
		assertTrue(editUserPage.getLastName().equals("newLastName"),
				"User's Last Name isn't displayed correctly in Edit user page");
		assertTrue(editUserPage.isRadioButtonUserSelected(), "User's role isn't displayed correctly in Edit user page");

//		driver.findElement(By.xpath("//td[contains(text(), 'Name')]")).click();
//		editUserPage.sendKeysSearchGroups(groupNameTwo);
//		assertTrue(driver.findElement(By.xpath("//td[contains(text(), '" + groupNameTwo + "')]")).isSelected(),
//				"User was not added to the group correctly");
		editUserPage.clickCancelButton();
		Thread.sleep(2000);
		mainPage.clickLogoutButton();
		loginPage.sendKeysUserName(newAdminUserName);
		loginPage.sendKeysPassword("aaaaaaaaaaa");
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
		mainPage.clickLogoutButton();

	}
}