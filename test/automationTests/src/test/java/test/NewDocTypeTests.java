package test;

import static org.testng.Assert.assertTrue;

import java.io.IOException;

import org.openqa.selenium.By;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterGroups;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeGroups;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import page.AdminNewDocTypePage;
import page.AdminNewGroupPage;
import page.DocTypeListPage;
import page.EditDocTypePage;
import page.GroupListPage;
import page.LoginPage;
import page.MainPage;
import page.UserListPage;
import utilities.API;
import utilities.GetSessionId;

public class NewDocTypeTests extends AbstractTest {
	LoginPage loginPage;
	MainPage mainPage;
	AdminNewGroupPage groupPage;
	UserListPage userPage;
	AdminNewDocTypePage newDocTypePage;
	GroupListPage groupListPage;
	DocTypeListPage docPage;
	EditDocTypePage editDocPage;
	String sessionID;

	@Parameters({ "groupName" })
	@BeforeClass
	public void preconitions(String groupName) throws IOException {
		loginPage = new LoginPage(driver);
		mainPage = new MainPage(driver);
		groupPage = new AdminNewGroupPage(driver);
		userPage = new UserListPage(driver);
		newDocTypePage = new AdminNewDocTypePage(driver);
		groupListPage = new GroupListPage(driver);
		docPage = new DocTypeListPage(driver);
		editDocPage = new EditDocTypePage(driver);
		sessionID = GetSessionId.login("admin", "adminadmin");
		API.createGroup("Random description", "[]", "[]", groupName, "[]", sessionID);
	}

	@Parameters({ "docTypeName", "groupName" })
	@AfterClass
	public void deleteDocTypeCreatedForTest(String docTypeName, String groupName) throws IOException {
		sessionID = GetSessionId.login("adminas", "adminadmin");
		API.deleteGroup(groupName, sessionID);
		API.deleteDoctype(docTypeName, sessionID);
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

	/*-
	 * Preconditions: 
	 *   - admin is logged in the system;
	 * Test steps:
	 * 1. Open new document type page.
	 * 2. Leave document types name field empty.
	 * 3. Click create button.
	 * Expected results:
	 *   - New document type should not be created without a name.
	 */
	@Test(groups = { "newDocType" }, priority = 0, enabled = true)
	public void docTypeNoNameTest() {
		mainPage.clickAdminButton();
		mainPage.clickAdminNewDocTypeButton();
		newDocTypePage.waitForCreateButton();
		newDocTypePage.clickCreateButton();
		assertTrue(driver.findElement(By.xpath("//button[contains(text(),'Create')]")).isDisplayed(),
				"doc type should not be created without a name");
		newDocTypePage.clickCancelButton();
	}

	/*-
	 * Preconditions: 
	 *   - admin is logged in the system;
	 *   - at least one group is created for testing purpose.
	 * Test steps:
	 * 1. Open new document type page.
	 * 2. Enter document types name.
	 * 3. Check create and sign checkboxes on the group that was created for testing purpose.
	 * 4. Click create button.
	 * Expected results:
	 *   - New document type is created.
	 */
	@Parameters({ "docTypeName", "groupName" })
	@Test(groups = { "newDocType" }, priority = 1, enabled = true)
	public void createNewDocTypeTest(String p1, String p2) {
		newDocTypePage.createDocType(p1, p2);
		driver.navigate().refresh();
		mainPage.clickAdminButton();
		mainPage.clickAdminDocTypesButton();
		mainPage.waitForAdminButton();
		docPage.sendKeysDocTypeSearch(p1);
		mainPage.waitForAdminButton();
		assertTrue(driver.findElement(By.xpath("//td[contains(text(),'" + p1 + "')]")).isDisplayed(),
				"doc type was not created");
		docPage.clearDocTypeSearch();
	}

	/*-
	 * Preconditions: 
	 *   - admin is logged in the system;
	 *   - at lease one document type was created for testing purpose with a test group create/sign rights set.
	 * Test steps:
	 * 1. Open admin document types page.
	 * Expected results:
	 *   - Create/sign rights where set properly for the selected group.
	 */
	@Parameters({ "docTypeName", "groupName" })
	@Test(groups = { "newDocType" }, priority = 2, enabled = true)
	public void docTypeGroupsTest(String p1, String p2) {
		mainPage.clickAdminButton();
		mainPage.clickAdminDocTypesButton();
		mainPage.waitForAdminButton();
		docPage.sendKeysDocTypeSearch(p1);
		docPage.clickEditSpecificDocType(p1);
		editDocPage.waitForCancelButton();
		editDocPage.sendKeysSearchField(p2);
		assertTrue(driver.findElement(By.xpath("//td[contains(text(), '" + p2 + "')]/..//td[2]//input")).isSelected(),
				"group rights weren't assigned properly");
		assertTrue(driver.findElement(By.xpath("//td[contains(text(), '" + p2 + "')]/..//td[3]//input")).isSelected(),
				"group rights weren't assigned properly");
		docPage.clickCancelButton();
	}
}
