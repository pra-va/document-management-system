package test;

import static org.testng.Assert.assertTrue;

import java.io.IOException;
import org.openqa.selenium.By;
import org.testng.annotations.AfterGroups;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeGroups;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import page.AdminNewDocTypePage;
import page.AdminNewGroupPage;
import page.DocTypeListPage;
import page.GroupListPage;
import page.LoginPage;
import page.MainPage;
import page.UserListPage;

public class NewDocTypeTests extends AbstractTest {
	LoginPage loginPage;
	MainPage mainPage;
	AdminNewGroupPage groupPage;
	UserListPage userPage;
	AdminNewDocTypePage newDocTypePage;
	GroupListPage groupListPage;
	DocTypeListPage docPage;
	

	@BeforeClass
	public void preconitions() throws IOException {
		loginPage = new LoginPage(driver);
		mainPage = new MainPage(driver);
		groupPage = new AdminNewGroupPage(driver);
		userPage = new UserListPage(driver);
		newDocTypePage = new AdminNewDocTypePage(driver);
		groupListPage = new GroupListPage(driver);
		docPage = new DocTypeListPage(driver);
		
	}

	@Parameters({ "adminUserName", "adminPasswrod" })
	@BeforeGroups("newDocType")
	public void login(String p1, String p2) {
		loginPage.sendKeysUserName(p1);
		loginPage.sendKeysPassword(p2);
		loginPage.clickButtonLogin();
	}

	@AfterGroups("newDocType")
	public void logout() {
		mainPage.waitForLogoutButton();
		mainPage.clickLogoutButton();
	}

	@Test(groups = { "newDocType" }, priority = 1, enabled = true)
	public void docTypeNoNameTest() {
		mainPage.clickAdminButton();
		mainPage.clickAdminNewDocTypeButton();
		newDocTypePage.waitForCreateButton();
		newDocTypePage.clickCreateButton();
		assertTrue(driver.findElement(By.xpath("//button[contains(text(),'Create')]")).isDisplayed(),
				"doc type should not be created without a name");
		newDocTypePage.clickCancelButton();
	}

	@Parameters({ "docTypeName", "groupName" })
	@Test(groups = { "newDocType" }, priority = 0, enabled = true)
	public void createNewDocTypeTest(String p1, String p2) {
		mainPage.clickAdminButton();
		mainPage.clickAdminNewDocTypeButton();
		newDocTypePage.sendKeysDocTypeName(p1);
		newDocTypePage.clickAddSpecificGroupButton(p2);
		newDocTypePage.clickCreateDocRigthsCheckBox(p2);
		newDocTypePage.clickSignDocRigthsCheckBox(p2);
		// newDocTypePage.clickCreateButton();
		newDocTypePage.clickCancelButton();
		mainPage.clickAdminButton();
		mainPage.clickAdminDocTypesButton();
		mainPage.waitForAdminButton();
		assertTrue(driver.findElement(By.xpath("//td[contains(text(),'" + p1 + "')]")).isDisplayed(),
				"doc type was not created");
	}

	@Parameters({ "docTypeName" })
	@Test(groups = { "newDocType" }, priority = 2, enabled = true)
	public void docTypeGroupsTest(String p1) {
		mainPage.clickAdminButton();
		mainPage.clickAdminDocTypesButton();
		mainPage.waitForAdminButton();
		assertTrue(
				driver.findElement(By.xpath("//td[contains(text(), '" + p1 + "')]/..//td[3]//span"))
						.getAttribute("data-content").equalsIgnoreCase("Junior developer."),
				"check if group rights were assigned properly");
		assertTrue(
				driver.findElement(By.xpath("//td[contains(text(), '" + p1 + "')]/..//td[4]//span"))
						.getAttribute("data-content").equalsIgnoreCase("Junior developer."),
				"check if group rights were assigned properly");
	}
}
