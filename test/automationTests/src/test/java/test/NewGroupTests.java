package test;

import static org.testng.Assert.assertTrue;

import java.io.IOException;
import org.openqa.selenium.By;
import org.testng.annotations.AfterGroups;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeGroups;
import org.testng.annotations.BeforeMethod;
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

	@BeforeMethod
	public void navigateToNewGroupPage() {
		mainPage.waitForAdminButton();
		mainPage.clickAdminButton();
		mainPage.clickAdminNewGroupButton();
	}

	@AfterMethod
	public void afterTest() {
		mainPage.navigateToMainPage();
	}

	@Parameters({ "adminUserName", "groupName", "groupDescription", "docTypeName" })
	@Test(groups = { "createGroup" }, priority = 0, enabled = true)
	public void createGroupTest(String p1, String p2, String p3, String p4) {
		groupPage.sendKeysGroupName(p2);
		groupPage.sendKeysGroupDescription(p3);
		groupPage.clickAddSpecificUserButton(p1);
		groupPage.clickAddSpecificDocTypeButton(p4);
		groupPage.clickCreateDocRigthsCheckBox(p4);
		groupPage.clickSignDocRigthsCheckBox(p4);
		// groupPage.clickCreateButton();
		groupPage.clickCancelButton();
		mainPage.waitForAdminButton();
		mainPage.clickAdminButton();
		mainPage.clickAdminGroupsButton();
		mainPage.waitForAdminButton();
		assertTrue(driver.findElement(By.xpath("//td[contains(text(), '" + p2 + "')]")).isDisplayed(),
				"Created group name isn't displayed or group wasn't created");
	}

	@Test(groups = { "createGroup" }, priority = 1, enabled = true)
	public void groupWithoutNameTest() {
		groupPage.clickCreateButton();
		assertTrue(driver.findElement(By.xpath("//div[contains(text(), 'New Group')]")).isDisplayed(),
				"Group should not be created without a name");
		groupPage.clickCancelButton();
	}

	@Parameters({ "adminUserName", "groupName" })
	@Test(groups = { "createGroup" }, priority = 2, enabled = true)
	public void userInGroupTest(String p1, String p2) {
		groupPage.clickCancelButton();
		mainPage.waitForAdminButton();
		mainPage.clickAdminButton();
		mainPage.clickAdminUsersButton();
		mainPage.waitForAdminButton();
		userPage.clickEditSpecificUserButton(p1);
		newUserPage.waitForCancelButton();
		assertTrue(driver.findElement(By.xpath("//div[@id='newUserAddedGroups']//td[contains(text(), '" + p2 + "')]"))
				.isDisplayed(), "User was not added to the group correctly");
		groupPage.clickCancelButton();
	}

	@Test(groups = { "createGroup" }, priority = 1, enabled = false)
	public void docTypeTest() {

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
