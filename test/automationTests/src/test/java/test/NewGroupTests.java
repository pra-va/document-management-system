package test;

import static org.testng.Assert.assertTrue;

import java.io.IOException;
import java.util.List;

import org.openqa.selenium.By;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeGroups;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import page.AdminNewGroupPage;
import page.AdminNewUserPage;
import page.GroupListPage;
import page.LoginPage;
import page.MainPage;
import page.UserListPage;
import utilities.TestData;

public class NewGroupTests extends AbstractTest {
	LoginPage loginPage;
	MainPage mainPage;
	AdminNewGroupPage groupPage;
	UserListPage userPage;
	AdminNewUserPage newUserPage;
	GroupListPage groupListPage;
	List<String> description;

	@BeforeClass
	public void preconitions() throws IOException {
		loginPage = new LoginPage(driver);
		mainPage = new MainPage(driver);
		groupPage = new AdminNewGroupPage(driver);
		userPage = new UserListPage(driver);
		newUserPage = new AdminNewUserPage(driver);
		groupListPage = new GroupListPage(driver);
		description = TestData.getTestData("src/test/resources/groupDescription.txt");
	}

	@BeforeGroups("createGroup")
	public void login() {
		loginPage.sendKeysUserName(admin.getUserName());
		loginPage.sendKeysPassword(admin.getPassWord());
		loginPage.clickButtonLogin();
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

	@Test(groups = { "createGroup" }, priority = 1, enabled = true)
	public void createGroupTest() {
		groupPage.sendKeysGroupName("Junior developer");
		groupPage.sendKeysGroupDescription(description.get(0));
		groupPage.clickAddSpecificUserButton("admin");
		groupPage.clickAddSpecificDocTypeButton("test");
		groupPage.clickCreateDocRigthsCheckBox("test");
		groupPage.clickSignDocRigthsCheckBox("test");
		// groupPage.clickCreateButton();
		groupPage.clickCancelButton();
		mainPage.waitForAdminButton();
		mainPage.clickAdminButton();
		mainPage.clickAdminGroupsButton();
		mainPage.waitForAdminButton();
		assertTrue(driver.findElement(By.xpath("//td[contains(text(), 'Junior developer')]")).isDisplayed(),
				"Created group name isn't displayed or group wasn't created");
	}

	@Test(groups = { "createGroup" }, priority = 0, enabled = true)
	public void groupWithoutNameTest() {
		groupPage.clickCreateButton();
		assertTrue(driver.findElement(By.xpath("//div[contains(text(), 'New Group')]")).isDisplayed(),
				"Group should not be created without a name");
		groupPage.clickCancelButton();
	}

	@Test(groups = { "createGroup" }, priority = 2, enabled = true)
	public void userInGroupTest() throws InterruptedException {
		groupPage.clickCancelButton();
		mainPage.waitForAdminButton();
		mainPage.clickAdminButton();
		mainPage.clickAdminUsersButton();
		mainPage.waitForAdminButton();
		userPage.clickEditSpecificUserButton("admin");
		newUserPage.waitForcancelButton();
		assertTrue(driver
				.findElement(By.xpath("//div[@id='newUserAddedGroups']//td[contains(text(), 'Junior developer')]"))
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
		groupListPage.clickEditSpecificGroupButton("juniors");
	}
}
