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
import page.GroupListPage;
import page.LoginPage;
import page.MainPage;
import page.UserListPage;

public class NewGroupTests extends AbstractTest {
	LoginPage loginPage;
	MainPage mainPage;
	AdminNewGroupPage groupPage;
	UserListPage userPage;
	AdminNewUserPage newUserPage;
	GroupListPage groupListPage;

	@BeforeClass
	public void preconitions() throws IOException {
		loginPage = new LoginPage(driver);
		mainPage = new MainPage(driver);
		groupPage = new AdminNewGroupPage(driver);
		userPage = new UserListPage(driver);
		newUserPage = new AdminNewUserPage(driver);
		groupListPage = new GroupListPage(driver);
	}

	@Parameters({ "groupName" })
	@AfterClass
	public void deleteGroupCreatedForTest(String p1) {
		groupPage.deleteGroup(p1);
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

	@Parameters({ "adminUserName", "groupName", "groupDescription" })
	@Test(groups = { "createGroup" }, priority = 1, enabled = true)
	public void createGroupTest(String p1, String p2, String p3) {
		groupPage.createGroup(p1, p2, p3);
		driver.navigate().refresh();
		mainPage.clickAdminButton();
		mainPage.clickAdminGroupsButton();
		mainPage.waitForAdminButton();
		assertTrue(driver.findElement(By.xpath("//td[contains(text(), '" + p2 + "')]")).isDisplayed(),
				"Created group name isn't displayed or group wasn't created");
	}

	@Test(groups = { "createGroup" }, priority = 2, enabled = true)
	public void groupWithoutNameTest() {
		mainPage.clickAdminButton();
		mainPage.clickAdminNewGroupButton();
		groupPage.clickCreateButton();
		assertTrue(driver.findElement(By.xpath("//div[contains(text(), 'New Group')]")).isDisplayed(),
				"Group should not be created without a name");
		groupPage.clickCancelButton();
	}

	@Parameters({ "adminUserName", "groupName" })
	@Test(groups = { "createGroup" }, priority = 3, enabled = true)
	public void userInGroupTest(String p1, String p2) {
		mainPage.clickAdminButton();
		mainPage.clickAdminUsersButton();
		mainPage.waitForAdminButton();
		userPage.clickEditSpecificUserButton(p1);
		newUserPage.waitForCancelButton();
		assertTrue(driver
				.findElement(
						By.xpath("//td[contains(text(), '" + p2 + "')]/..//td[2]//button[contains(text(),'Remove')]"))
				.isDisplayed(), "User was not added to the group correctly");
	}

	@Test(groups = { "createGroup" }, priority = 1, enabled = false)
	public void editGroupTest() {
		groupPage.clickCancelButton();
		mainPage.waitForAdminButton();
		mainPage.clickAdminButton();
		mainPage.clickAdminGroupsButton();
		groupListPage.clickEditSpecificGroupButton("");
	}
}
