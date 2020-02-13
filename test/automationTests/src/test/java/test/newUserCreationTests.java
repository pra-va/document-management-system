package test;

import static org.testng.Assert.assertTrue;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.thoughtworks.xstream.XStream;

import page.AdminNewUserPage;
import page.LoginPage;
import page.MainPage;
import page.UserListPage;
import utilities.User;

public class newUserCreationTests extends AbstractTest {
	LoginPage loginPage;
	MainPage mainPage;
	AdminNewUserPage adminNewUserPage;
	UserListPage userListPage;
	WebDriverWait wait;
	XStream xstream;
	User newAdmin;
	User admin;
	User wrongInfo;
	
	@BeforeClass
	public void preconditions() {
		loginPage = new LoginPage(driver);
		mainPage = new MainPage(driver);
		adminNewUserPage = new AdminNewUserPage(driver);
		userListPage = new UserListPage(driver);
		wait = new WebDriverWait(driver, 2);
		xstream = new XStream();
		XStream.setupDefaultSecurity(xstream);
		xstream.allowTypesByWildcard(new String[] { "utilities.User" });
		try {
			newAdmin = (User) xstream.fromXML(FileUtils.readFileToString(new File("src/test/resources/newAdmin.xml")));
			admin = (User) xstream.fromXML(FileUtils.readFileToString(new File("src/test/resources/admin.xml")));
			wrongInfo = (User) xstream
					.fromXML(FileUtils.readFileToString(new File("src/test/resources/wrongInfo.xml")));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		loginPage.sendKeysUserName(admin.getUserName());
		loginPage.sendKeysPassword(admin.getPassWord());	
		loginPage.clickButtonLogin();
		
	}
	
	@Test(groups = { "newUserCreationTests" })
	public void newUserCreationTest() throws InterruptedException {
		mainPage.clickAdminButton();
		mainPage.clickAdminNewUserButton();
		adminNewUserPage.sendKeysFirstName(newAdmin.getFirstName());
		adminNewUserPage.sendKeysLastName(newAdmin.getLastName());
		adminNewUserPage.sendKeysUserName(newAdmin.getUserName());
		adminNewUserPage.sendKeysPassword(newAdmin.getPassWord());
		adminNewUserPage.checkShowPassword();
		adminNewUserPage.clickAdminRadio();
		//adminNewUserPage.sendKeysSearchGroupToAdd("!juniors");
		adminNewUserPage.clickAddSpecificGroupButton("juniors");
		//adminNewUserPage.clickButtonAddUserToGroup();
	//	adminNewUserPage.sendKeysSearchGroupToRemove("!juniors");
	//	adminNewUserPage.clickButtonRemoveUserFromGroup();		
		adminNewUserPage.clickCreateButton();
		mainPage.clickAdminButton();
		mainPage.clickAdminUsersButton();
		userListPage.sendKeysSearchForUser(newAdmin.getUserName());
		
		
		//adminNewUserPage.getAlertMessage();
		
//		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[contains(text(),'Admin')]")));
//		assertTrue(driver.findElement(By.xpath("//a[contains(text(),'Admin')]")).isDisplayed(),
//				"admin dropdown isn't displayed");
		
		
		//mainPage.clickLogoutButton();
		
	}
	
//	@Test(groups = { "newUserCreationTests" })
//	public void adminLoginTest() throws InterruptedException {
//		loginPage.sendKeysUserName(admin.getUserName());
//		loginPage.sendKeysPassword(admin.getPassWord());
//		loginPage.clickButtonLogin();
//		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[contains(text(),'Admin')]")));
//		assertTrue(driver.findElement(By.xpath("//a[contains(text(),'Admin')]")).isDisplayed(),
//				"admin dropdown isn't displayed");
//		mainPage.clickLogoutButton();
//	}
	
	
	
	
	
}
