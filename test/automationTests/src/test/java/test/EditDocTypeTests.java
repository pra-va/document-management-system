package test;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import org.openqa.selenium.By;
import org.testng.annotations.AfterGroups;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeGroups;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import page.DocTypeListPage;
import page.EditDocTypePage;
import page.LoginPage;
import page.MainPage;

public class EditDocTypeTests extends AbstractTest {
	LoginPage loginPage;
	MainPage mainPage;
	EditDocTypePage editDocPage;
	DocTypeListPage docListPage;

	@BeforeClass
	public void preconitions() {
		loginPage = new LoginPage(driver);
		mainPage = new MainPage(driver);
		docListPage = new DocTypeListPage(driver);
		editDocPage = new EditDocTypePage(driver);
	}

	@Parameters({ "adminUserName", "adminPasswrod" })
	@BeforeGroups("editDocType")
	public void login(String p1, String p2) {
		loginPage.sendKeysUserName(p1);
		loginPage.sendKeysPassword(p2);
		loginPage.clickButtonLogin();
	}

	@AfterGroups("editDocType")
	public void logout() {
		mainPage.waitForLogoutButton();
		mainPage.clickLogoutButton();
	}

	/*-
	 * Preconditions: 
	 *   - admin is logged in the system;
	 *   - at least one doc type is created for testing purpose.
	 * Test steps:
	 * 1. Open admin doc types page.
	 * 2. Enter name of the doc type you are searching for.
	 * 3. Click edit that doc type and change its name.
	 * 4. Click update button.
	 * 5. Change the doc type name back
	 * Expected results:
	 *   - Doc type name is changed correctly.
	 */
	@Parameters({ "docTypeName", "newDocTypeName" })
	@Test(groups = { "editDocType" }, priority = 1, enabled = true)
	public void editDocTypeNameTest(String p1, String p2) {
		mainPage.clickAdminButton();
		mainPage.clickAdminDocTypesButton();
		docListPage.clearDocTypeSearch();
		docListPage.sendKeysDocTypeSearch(p1);
		docListPage.clickEditSpecificDocType(p1);
		editDocPage.clearDocNameField();
		editDocPage.sendKeysDocName(p2);
		editDocPage.clickUpdateButton();
		docListPage.clearDocTypeSearch();
		docListPage.sendKeysDocTypeSearch(p2);
		assertTrue(driver.findElement(By.xpath("//td[contains(text(), '" + p2 + "')]")).isDisplayed(),
				"Doc type name wasn't changed correctly");
		docListPage.clickEditSpecificDocType(p2);
		editDocPage.clearDocNameField();
		editDocPage.sendKeysDocName(p1);
		editDocPage.clickUpdateButton();
		docListPage.clearDocTypeSearch();
	}

	/*-
	 * Preconditions: 
	 *   - admin is logged in the system;
	 *   - at least one doc type and group are created for testing purpose.
	 * Test steps:
	 * 1. Open admin groups page.
	 * 2. Enter name of the doc type you are searching for.
	 * 3. Click edit that doc type and add assign group with creating/signing rights.
	 * 4. Click update button.
	 * 5. Click edit the doc type again and remove creating/signing rights for the same group.
	 * Expected results:
	 *   - Creating/signing rights assigned and removed for the group correctly.
	 */
	@Parameters({ "docTypeName", "groupName" })
	@Test(groups = { "editDocType" }, priority = 0, enabled = true)
	public void editDocTypeGroupTest(String p1, String p2) {
		mainPage.clickAdminButton();
		mainPage.clickAdminDocTypesButton();
		docListPage.sendKeysDocTypeSearch(p1);
		docListPage.clickEditSpecificDocType(p1);
		editDocPage.sendKeysSearchField(p2);
		editDocPage.clickSpecificGroupCreateCheckBox(p2);
		editDocPage.clickSpecificGroupSignCheckBox(p2);
		editDocPage.clickUpdateButton();
		driver.navigate().refresh();
		docListPage.sendKeysDocTypeSearch(p1);
		docListPage.clickEditSpecificDocType(p1);
		editDocPage.sendKeysSearchField(p2);
		assertTrue(driver
				.findElement(
						By.xpath("//div[@id='newUserGroups']//td[contains(text(), '" + p2 + "')]/..//td[2]//input"))
				.isSelected(), "doc type wasn't assigned to the group correctly");
		assertTrue(driver
				.findElement(
						By.xpath("//div[@id='newUserGroups']//td[contains(text(), '" + p2 + "')]/..//td[3]//input"))
				.isSelected(), "doc type wasn't assigned to the group correctly");
		editDocPage.clickSpecificGroupCreateCheckBox(p2);
		editDocPage.clickSpecificGroupSignCheckBox(p2);
		editDocPage.clickUpdateButton();
		driver.navigate().refresh();
		docListPage.sendKeysDocTypeSearch(p1);
		docListPage.clickEditSpecificDocType(p1);
		editDocPage.sendKeysSearchField(p2);
		assertFalse(driver
				.findElement(
						By.xpath("//div[@id='newUserGroups']//td[contains(text(), '" + p2 + "')]/..//td[2]//input"))
				.isSelected(), "doc type wasn't assigned to the group correctly");
		assertFalse(driver
				.findElement(
						By.xpath("//div[@id='newUserGroups']//td[contains(text(), '" + p2 + "')]/..//td[3]//input"))
				.isSelected(), "doc type wasn't assigned to the group correctly");
		editDocPage.clickCancelButton();
	}
}
