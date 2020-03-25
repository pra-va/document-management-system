package test;

import static org.testng.Assert.assertTrue;

import java.io.IOException;
import org.openqa.selenium.By;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterGroups;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeGroups;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import page.AdminNewGroupPage;
import page.AdminNewUserPage;
import page.EditUserPage;
import page.GroupListPage;
import page.LoginPage;
import page.MainPage;
import page.UserListPage;
import utilities.API;
import utilities.GetSessionId;

public class NewGroupTests extends AbstractTest {
	LoginPage loginPage;
	MainPage mainPage;
	AdminNewGroupPage groupPage;
	UserListPage userPage;
	AdminNewUserPage newUserPage;
	GroupListPage groupListPage;
	EditUserPage editUserPage;
	String sessionID;

	@BeforeClass
	public void preconitions() throws IOException {
		loginPage = new LoginPage(driver);
		mainPage = new MainPage(driver);
		groupPage = new AdminNewGroupPage(driver);
		userPage = new UserListPage(driver);
		newUserPage = new AdminNewUserPage(driver);
		groupListPage = new GroupListPage(driver);
		editUserPage = new EditUserPage(driver);
	}

	@Parameters({ "groupName" })
	@AfterClass
	public void deleteGroupCreatedForTest(String groupName) throws IOException {
		sessionID = GetSessionId.login("admin", "adminadmin");
		API.deleteGroup(groupName, sessionID);
	}

	@Parameters({ "adminUserName", "adminPasswrod" })
	@BeforeGroups("createGroup")
	public void login(String p1, String p2) {
		loginPage.sendKeysUserName(p1);
		loginPage.sendKeysPassword(p2);
		loginPage.clickButtonLogin();
	}

	@AfterGroups("createGroup")
	public void logout() {
		mainPage.waitForLogoutButton();
		mainPage.clickLogoutButton();
	}

	@AfterMethod
	public void afterTest() {
		mainPage.navigateToMainPage();
	}

	/**-
	 * Preconditions:<br> 
	 *   - admin is logged in the system;<br>
	 * Test steps:<br>
	 * 1. Open new group page.<br>
	 * 2. Enter groups name and description.<br>
	 * 3. Add user "adminas" to this group.<br>
	 * 4. Click create button.<br>
	 * Expected results:<br>
	 *   - New group is created successfully.<br>
	 */
	@Parameters({ "adminUserName", "groupName", "groupDescription" })
	@Test(groups = { "createGroup" }, priority = 1, enabled = true)
	public void createGroupTest(String p1, String p2, String p3) {
		groupPage.createGroup(p1, p2, p3);
		driver.navigate().refresh();
		mainPage.clickAdminButton();
		mainPage.clickAdminGroupsButton();
		mainPage.waitForAdminButton();
		groupListPage.sendKeysSearchForGroup(p2);
		assertTrue(driver.findElement(By.xpath("//td[contains(text(), '" + p2 + "')]")).isDisplayed(),
				"Created group name isn't displayed or group wasn't created");
	}

	/**-
	 * Preconditions: <br>
	 *   - admin is logged in the system;<br>
	 * Test steps:<br>
	 * 1. Open new group page.<br>
	 * 2. Leave groups name field empty.<br>
	 * 3. Click create button.<br>
	 * Expected results:<br>
	 *   - New group should not be created without a name.<br>
	 */
	@Test(groups = { "createGroup" }, priority = 0, enabled = true)
	public void groupWithoutNameTest() {
		mainPage.clickAdminButton();
		mainPage.clickAdminNewGroupButton();
		groupPage.clickCreateButton();
		assertTrue(driver.findElement(By.xpath("//div[contains(text(), 'New Group')]")).isDisplayed(),
				"Group should not be created without a name");
		groupPage.clickCancelButton();
	}

	/**-
	 * Preconditions: <br>
	 *   - admin is logged in the system;<br>
	 * Test steps:<br>
	 * 1. Open admin users page.<br>
	 * 2. Enter "adminas" name in the search field.<br>
	 * 3. Click edit/view.<br>
	 * Expected results:<br>
	 *   - User should be assigned to the proper group.<br>
	 */
	@Parameters({ "adminUserName", "groupName" })
	@Test(groups = { "createGroup" }, priority = 2, enabled = true)
	public void userInGroupTest(String p1, String p2) {
		mainPage.clickAdminButton();
		mainPage.clickAdminUsersButton();
		mainPage.waitForAdminButton();
		userPage.sendKeysSearchForUser(p1);
		userPage.clickEditSpecificUserButton(p1);
		editUserPage.waitForEditUserPage();
		//editUserPage.sendKeysSearchGroups2(p2);
		editUserPage.sendKeysSearchGroups(p2);
		newUserPage.waitForCancelButton();
		assertTrue(driver.findElement(By.xpath("//td[contains(text(), '" + p2 + "')]/..//td[1]//input")).isSelected(),
				"User was not added to the group correctly");
	}
}
