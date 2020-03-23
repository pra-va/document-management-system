package test;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import org.openqa.selenium.By;
import org.testng.annotations.AfterGroups;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeGroups;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import page.EditGroupPage;
import page.GroupListPage;
import page.LoginPage;
import page.MainPage;

public class EditGroupTest extends AbstractTest {
	LoginPage loginPage;
	MainPage mainPage;
	GroupListPage groupListPage;
	EditGroupPage editGroupPage;

	@BeforeClass
	public void preconitions() {
		loginPage = new LoginPage(driver);
		mainPage = new MainPage(driver);
		groupListPage = new GroupListPage(driver);
		editGroupPage = new EditGroupPage(driver);
	}

	@Parameters({ "adminUserName", "adminPasswrod" })
	@BeforeGroups("editGroup")
	public void login(String p1, String p2) {
		loginPage.sendKeysUserName(p1);
		loginPage.sendKeysPassword(p2);
		loginPage.clickButtonLogin();
	}

	@AfterGroups("editGroup")
	public void logout() {
		mainPage.waitForLogoutButton();
		mainPage.clickLogoutButton();
	}

	/*-
	 * Preconditions: 
	 *   - admin is logged in the system;
	 *   - at least one group is created for testing purpose.
	 * Test steps:
	 * 1. Open admin groups page.
	 * 2. Enter name of the group you are searching for.
	 * 3. Click edit that group and change its name.
	 * 4. Click update button.
	 * 5. Change the group name back
	 * Expected results:
	 *   - Groups name is changed correctly.
	 */
	@Parameters({ "groupName", "newGroupName" })
	@Test(groups = { "editGroup" }, priority = 0, enabled = true)
	public void editGroupName(String p1, String p2) {
		mainPage.clickAdminButton();
		mainPage.clickAdminGroupsButton();
		groupListPage.sendKeysSearchForGroup(p1);
		groupListPage.clickEditSpecificGroupButton(p1);
		editGroupPage.clearGroupNameField();
		editGroupPage.sendKeysGroupName(p2);
		editGroupPage.clickUpdateButton();
		groupListPage.clearSearchField();
		groupListPage.sendKeysSearchForGroup(p2);
		assertTrue(driver.findElement(By.xpath("//td[contains(text(), '" + p2 + "')]")).isDisplayed(),
				"Group name wasn't changed correctly");
		groupListPage.clickEditSpecificGroupButton(p2);
		editGroupPage.clearGroupNameField();
		editGroupPage.sendKeysGroupName(p1);
		editGroupPage.clickUpdateButton();
		groupListPage.clearSearchField();
	}

	/*-
	 * Preconditions: 
	 *   - admin is logged in the system;
	 *   - at least one group is created for testing purpose.
	 * Test steps:
	 * 1. Open admin groups page.
	 * 2. Enter name of the group you are searching for.
	 * 3. Click edit that group and remove a user.
	 * 4. Click update button.
	 * 5. Click edit group again and add the user back.
	 * Expected results:
	 *   - User is removed and added to the group correctly.
	 */
	@Parameters({ "groupName", "adminUserName" })
	@Test(groups = { "editGroup" }, priority = 1, enabled = true)
	public void updateGroupUsersTest(String p1, String p2) {
		mainPage.clickAdminButton();
		mainPage.clickAdminGroupsButton();
		groupListPage.sendKeysSearchForGroup(p1);
		groupListPage.clickEditSpecificGroupButton(p1);
		editGroupPage.sendKeysSearchGroupsUsers(p2);
		editGroupPage.clickSpecificUserCheckBox(p2);
		editGroupPage.clickUpdateButton();
		driver.navigate().refresh();
		groupListPage.sendKeysSearchForGroup(p1);
		groupListPage.clickEditSpecificGroupButton(p1);
		editGroupPage.sendKeysSearchGroupsUsers(p2);
		assertFalse(driver
				.findElement(
						By.xpath("//div[@id='newUserGroups']//td[contains(text(), '" + p2 + "')]/..//td[1]//input"))
				.isSelected(), "User should have been removed from the group");
		editGroupPage.clickSpecificUserCheckBox(p2);
		editGroupPage.clickUpdateButton();
		driver.navigate().refresh();
		groupListPage.sendKeysSearchForGroup(p1);
		groupListPage.clickEditSpecificGroupButton(p1);
		editGroupPage.sendKeysSearchGroupsUsers(p2);
		assertTrue(driver
				.findElement(
						By.xpath("//div[@id='newUserGroups']//td[contains(text(), '" + p2 + "')]/..//td[1]//input"))
				.isSelected(), "User should have been added to the group");
		editGroupPage.clickCancelButton();
	}

	/*-
	 * Preconditions: 
	 *   - admin is logged in the system;
	 *   - at least one group is created for testing purpose.
	 * Test steps:
	 * 1. Open admin groups page.
	 * 2. Enter name of the group you are searching for.
	 * 3. Click edit that group and remove creat and sign rights on specific document type .
	 * 4. Click update button.
	 * 5. Click edit group again and add create and sign rights back.
	 * Expected results:
	 *   - Create and sign rights are removed and added to the group correctly.
	 */
	@Parameters({ "groupName", "docTypeName" })
	@Test(groups = { "editGroup" }, priority = 2, enabled = true)
	public void updateGroupCreatSignRightsTest(String p1, String p2) {
		mainPage.clickAdminButton();
		mainPage.clickAdminGroupsButton();
		groupListPage.clearSearchField();
		groupListPage.sendKeysSearchForGroup(p1);
		groupListPage.clickEditSpecificGroupButton(p1);
		editGroupPage.sendKeysSearchDocTypes(p2);
		editGroupPage.clickSpecificDocTypeCreateCheckBox(p2);
		editGroupPage.clickSpecificDocTypeSignCheckBox(p2);
		editGroupPage.clickUpdateButton();
		driver.navigate().refresh();
		groupListPage.sendKeysSearchForGroup(p1);
		groupListPage.clickEditSpecificGroupButton(p1);
		editGroupPage.sendKeysSearchDocTypes(p2);
		assertFalse(driver
				.findElement(
						By.xpath("//div[@id='newUserGroups']//td[contains(text(), '" + p2 + "')]/..//td[2]//input"))
				.isSelected(), "User should have been removed from the group");
		assertFalse(driver
				.findElement(
						By.xpath("//div[@id='newUserGroups']//td[contains(text(), '" + p2 + "')]/..//td[3]//input"))
				.isSelected(), "User should have been removed from the group");
		editGroupPage.clickSpecificDocTypeCreateCheckBox(p2);
		editGroupPage.clickSpecificDocTypeSignCheckBox(p2);
		editGroupPage.clickUpdateButton();
		driver.navigate().refresh();
		groupListPage.sendKeysSearchForGroup(p1);
		groupListPage.clickEditSpecificGroupButton(p1);
		editGroupPage.sendKeysSearchDocTypes(p2);
		assertTrue(driver
				.findElement(
						By.xpath("//div[@id='newUserGroups']//td[contains(text(), '" + p2 + "')]/..//td[2]//input"))
				.isSelected(), "User should have been removed from the group");
		assertTrue(driver
				.findElement(
						By.xpath("//div[@id='newUserGroups']//td[contains(text(), '" + p2 + "')]/..//td[3]//input"))
				.isSelected(), "User should have been removed from the group");
		editGroupPage.clickCancelButton();
	}
}
