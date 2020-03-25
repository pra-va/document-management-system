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

import page.LoginPage;
import page.MainPage;
import page.MyDocumentsPage;
import page.NewDocumentPage;
import page.StatisticsPage;
import utilities.API;
import utilities.GetSessionId;

public class StatisticsTests extends AbstractTest {
	LoginPage loginPage;
	MainPage mainPage;
	NewDocumentPage newDocumentPage;
	MyDocumentsPage myDocumentsPage;
	StatisticsPage statsPage;
	String documentID;
	String fileID;
	String sessionID;

	@Parameters({ "newAdminFirstName", "newAdminLastName", "newAdminPassword", "newAdminUserName", "groupName",
			"docTypeName" })
	@BeforeClass
	public void preconditions(String newAdminFirstName, String newAdminLastName, String newAdminPassword,
			String newAdminUserName, String groupName, String docTypeName) throws IOException {

		loginPage = new LoginPage(driver);
		mainPage = new MainPage(driver);
		newDocumentPage = new NewDocumentPage(driver);
		myDocumentsPage = new MyDocumentsPage(driver);
		statsPage = new StatisticsPage(driver);
		sessionID = GetSessionId.login("admin", "adminadmin");
		API.createGroup("Group One description", "[]", "[]", groupName, "[]", sessionID);
		API.createDocType("[\"" + groupName + "\"]", "[\"" + groupName + "\"]", docTypeName, sessionID);
		API.createAdmin("[\"" + groupName + "\"]", newAdminFirstName, newAdminLastName, newAdminPassword,
				newAdminUserName, sessionID);
	}

	@Parameters({ "docTypeName", "groupName", "newAdminUserName", "docName" })
	@AfterClass
	public void deleteEntities(String docTypeName, String groupName, String userName, String docName)
			throws IOException {
		sessionID = GetSessionId.login("admin", "adminadmin");
//		fileID = API.getFileDetails(documentID, sessionID);
//		API.deleteFile(fileID, sessionID);
		API.deleteDocument(documentID, sessionID);
		API.deleteUser(userName, sessionID);
		API.deleteGroup(groupName, sessionID);
		API.deleteDoctype(docTypeName, sessionID);
	}

	@Parameters({ "newAdminUserName", "newAdminPassword" })
	@BeforeGroups("statsTests")
	public void login(String p1, String p2) {
		loginPage.sendKeysUserName(p1);
		loginPage.sendKeysPassword(p2);
		loginPage.clickButtonLogin();
	}

	@AfterGroups("statsTests")
	public void logout() {
		mainPage.waitForLogoutButton();
		mainPage.clickLogoutButton();
	}

	/**-	
	 * Preconditions: <br>
	 *   - admin is logged in the system;<br>
	 *   - one doc type, group and user are created using api calls for testing purpose.<br>
	 * Test steps:<br>
	 * 1. Open create document page.<br>
	 * 2. fill out the required fields.<br>
	 * 3. Click create.<br>
	 * 4. Navigate Statistics page.<br>
	 * Expected results:<br>
	 *   - Doc type you created is displayed in the statistics page.<br>
	 */
	@Parameters({ "docName", "docTypeName", "filePath", "fileName" })
	@Test(groups = { "statsTests" }, priority = 0, enabled = true)
	public void statisticsTest(String docName, String docTypeName, String filePath, String fileName)
			throws InterruptedException {
		mainPage.waitForLogoutButton();
		mainPage.clickCreateDocumentButton();
		System.out.println(filePath);
		newDocumentPage.createDocument(docName, "test", docTypeName, filePath, fileName);
		driver.navigate().refresh();
		mainPage.waitForAdminButton();
		mainPage.clickMyDocumentsButton();
		mainPage.waitForAdminButton();
		documentID = myDocumentsPage.getIDbyDocumentName(docName);
		mainPage.clickStatisticsButton();
		mainPage.waitForAdminButton();
		statsPage.waitForStatistics(docTypeName);
		assertTrue(driver.findElement(By.xpath("//td[contains(text(), '" + docTypeName + "')]")).isDisplayed(),
				"created document should be displayed in statistics");
	}
}
