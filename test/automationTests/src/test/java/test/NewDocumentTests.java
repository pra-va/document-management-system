package test;

import static org.testng.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterGroups;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeGroups;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.exceptions.UnirestException;

import page.AdminNewUserPage;
import page.EditDocumentPage;
import page.LoginPage;
import page.MainPage;
import page.MyDocumentsPage;
import page.NewDocumentPage;
import utilities.API;
import utilities.GetSessionId;

public class NewDocumentTests extends AbstractTest {
	LoginPage loginPage;
	MainPage mainPage;
	NewDocumentPage newDocumentPage;
	MyDocumentsPage myDocumentsPage;
	AdminNewUserPage newUserPage;
	EditDocumentPage editDocumentPage;
	HttpResponse<JsonNode> documentInfoJson;
	String documentID;
	String fileID;
	File file;
	Date date;
	String modifiedDate;
	String sessionID;

	@Parameters({ "groupName", "userFirstName", "userLastName", "userPassword", "userUserName", "docTypeName" })
	@BeforeClass
	public void preconditions(String groupName, String userFirstName, String userLastName, String userPassword,
			String userUserName, String docTypeName) throws IOException {
		loginPage = new LoginPage(driver);
		mainPage = new MainPage(driver);
		newUserPage = new AdminNewUserPage(driver);
		newDocumentPage = new NewDocumentPage(driver);
		myDocumentsPage = new MyDocumentsPage(driver);
		editDocumentPage = new EditDocumentPage(driver);
		sessionID = GetSessionId.login("admin", "adminadmin");
		API.createGroup("Group Two description", "[]", "[]", groupName, "[]", sessionID);
		API.createAdmin("[\"" + groupName + "\"]", userFirstName, userLastName, userPassword, userUserName, sessionID);
		API.createDocType("[]", "[\"" + groupName + "\"]", docTypeName, sessionID);
	}

	@Parameters({ "userUserName", "userPassword" })
	@BeforeGroups({ "newDocumentTests" })
	public void login(String userUserName, String userPassword) {
		loginPage.sendKeysUserName(userUserName);
		loginPage.sendKeysPassword(userPassword);
		loginPage.clickButtonLogin();
	}

	@AfterGroups("newDocumentTests")
	public void logout() {
		mainPage.waitForLogoutButton();
		mainPage.clickLogoutButton();
	}

	@Parameters({ "adminUserName", "adminPassword", "userUserName", "groupName", "docTypeName" })
	@AfterClass
	public void deleteEntities(String adminUserName, String adminPassword, String userUserName, String groupName,
			String docTypeName) throws IOException {
		sessionID = GetSessionId.login(adminUserName, adminPassword);
		API.deleteUser(userUserName, sessionID);
		API.deleteGroup(groupName, sessionID);
		API.deleteDoctype(docTypeName, sessionID);
	}

	/*-	 
	 * Preconditions: 
	 * - one group was created;
	 * - one user was created and added to this group;
	 * - one document type was created and previously, group was added to list of groups that can create this type; 
	 * - user with document creation rights is logged in the system.
	 * 	
	 * Test steps: 	 
	 * 1. Log in as previously created user.
	 * 1. Click "Create document" button. 
	 * 2. Fill fields in form: "Document Name", "Document Description", search for document type, click on document type name,
	 *    click "Choose File", select file, click "Open".
	 * 3. Click "Create" button. 	 
	 *    Expected conditions: all properties ("Name", "Type", "Status", creation date) on "My Documents" list are displayed correctly. 
	 * 5. Click "Edit / View" button.	
	 *    Expected conditions: all properties ("Name", "Type", file name) are displayed correctly. 		 
	 */

	@Parameters({ "adminUserName", "adminPassword", "documentName", "documentDescription", "filePath", "fileName",
			"docTypeName" })
	@Test(groups = { "newDocumentTests" }, priority = 1, enabled = true)
	public void createNewDocumentTest(String adminUserName, String adminPassword, String documentName,
			String documentDescription, String filePath, String fileName, String docTypeName)
			throws InterruptedException, IOException {
		sessionID = GetSessionId.login(adminUserName, adminPassword);
		mainPage.waitForLogoutButton();
		mainPage.clickCreateDocumentButton();
		newDocumentPage.sendKeysDocNameField(documentName);
		newDocumentPage.sendKeysDocDescriptionField(documentDescription);
		newDocumentPage.sendKeysSearchForDocType(docTypeName);
		newDocumentPage.clickSelectSpecificDocTypeButton(docTypeName);
		file = new File(filePath);
		newDocumentPage.sendKeysFileUploadField(file.getAbsolutePath());
		newDocumentPage.waitForFileNameVisibility(fileName);
		newDocumentPage.clickCreateButton();
		mainPage.clickMyDocumentsButton();
		myDocumentsPage.sendKeysSearchDocument(documentName);
		assertTrue(myDocumentsPage.isDocumentNameDisplayed(documentName),
				"Document name isn't displayed correctly on My documents list");
		assertTrue(myDocumentsPage.getTypeByDocumentName(documentName).equals(docTypeName),
				"Document type isn't displayed correctly on My documents list");
		documentID = myDocumentsPage.getIDbyDocumentName(documentName);
		fileID = API.getFileDetails(documentID, sessionID);
		date = new Date();
		modifiedDate = new SimpleDateFormat("yyyy-MM-dd").format(date);
		assertTrue(myDocumentsPage.getCreationDatebyDocumentName(documentName).equals(modifiedDate),
				"Document creation date isn't displayed correctly on My documents list");
		assertTrue(myDocumentsPage.getFileNameByDocumentName(documentName).equals(fileName),
				"Attached file name isn't displayed correctly in File icon tooltip");
		assertTrue(myDocumentsPage.getStatusByDocumentName(documentName).equals("CREATED"),
				"Document status isn't displayed correctly on My documents list");
		myDocumentsPage.clickEditViewDocument(documentName);
		editDocumentPage.waitForEditDocumentPage();
		assertTrue(editDocumentPage.getDocName().equals(documentName),
				"Document name isn't displayed correctly on Edit document page");
		assertTrue(editDocumentPage.getDocDescription().equals(documentDescription),
				"Document description isn't displayed correctly on Edit/View document page");
		assertTrue(editDocumentPage.isFileNameDisplayed(fileName),
				"Attached file name isn't displayed correctly on Edit/View document page");
		editDocumentPage.clickCancelButton();
		API.deleteFile(fileID, sessionID);
		API.deleteDocument(documentID, sessionID);
	}

}
