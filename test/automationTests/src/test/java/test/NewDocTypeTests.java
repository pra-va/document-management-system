package test;

import java.io.IOException;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeGroups;
import org.testng.annotations.Test;

import page.AdminNewGroupPage;
import page.AdminNewUserPage;
import page.GroupListPage;
import page.LoginPage;
import page.MainPage;
import page.UserListPage;

public class NewDocTypeTests extends AbstractTest {
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
	
	@BeforeGroups("newDocType")
	public void login() {
		loginPage.sendKeysUserName(admin.getUserName());
		loginPage.sendKeysPassword(admin.getPassWord());
		loginPage.clickButtonLogin();
		mainPage.waitForAdminButton();
		mainPage.clickAdminButton();
		
	}
	
	@Test(groups = { "newDocType" }, priority = 1, enabled = true)
	public void createNewDocTypeTest() {
		
	}
}
