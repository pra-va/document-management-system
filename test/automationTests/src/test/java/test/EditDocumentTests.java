package test;

import static org.testng.Assert.assertTrue;

import java.io.File;
import java.io.IOException;

import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterGroups;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeGroups;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import page.EditDocumentPage;
import page.LoginPage;
import page.MainPage;
import page.MyDocumentsPage;
import page.NewDocumentPage;
import utilities.API;
import utilities.GetSessionId;

public class EditDocumentTests extends AbstractTest {
	LoginPage loginPage;
	MainPage mainPage;
	NewDocumentPage newDocumentPage;
	MyDocumentsPage myDocumentsPage;
	EditDocumentPage editDocumentPage;
	String sessionID;
	String documentID;
	String fileID;

	@Parameters({ "adminUserName", "adminPassword", "groupDescription", "groupName", "userFirstName", "userLastName",
			"userPassword", "userUserName", "docTypeName" })
	@BeforeClass
	public void preconditions(String adminUserName, String adminPassword, String groupDescription, String groupName,
			String userFirstName, String userLastName, String userPassword, String userUserName, String docTypeName)
			throws IOException {
		sessionID = GetSessionId.login(adminUserName, adminPassword);
		loginPage = new LoginPage(driver);
		mainPage = new MainPage(driver);
		newDocumentPage = new NewDocumentPage(driver);
		myDocumentsPage = new MyDocumentsPage(driver);
		editDocumentPage = new EditDocumentPage(driver);
		API.createGroup(groupDescription, "[]", "[]", groupName, "[]", sessionID);
		API.createUser("[\"" + groupName + "\"]", userFirstName, userLastName, userPassword, userUserName, sessionID);
		API.createDocType("[]", "[\"" + groupName + "\"]", docTypeName, sessionID);
	}

	@Parameters({ "userUserName", "userPassword" })
	@BeforeGroups({ "editDocumentTests" })
	public void login(String userUserName, String userPassword) {
		loginPage.sendKeysUserName(userUserName);
		loginPage.sendKeysPassword(userPassword);
		loginPage.clickButtonLogin();
	}

	@AfterGroups("editDocumentTests")
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

	/**-	 
	 * Preconditions:<br> 
	 * - one group was created;<br>
	 * - one user was created and added to this group;<br>
	 * - one document type was create and previously created group was added to list of groups that can create this type;<br>
	 * - one document was created;<br>
	 * - user with document creation rights is logged in the system.<br>
	 * 	
	 * Test steps:	<br>  
	 * 1. Click "My documents" button. <br>
	 * 2. Search for document name.<br>
	 * 3. Click "Edit / View button".<br>
	 * 2. Clear fields and enter new inputs in form: "Document Name", "Document Description", remove file, click "Choose File", select file,<br>
	 * click "Open".<br>
	 * 3. Click "Update" button.<br>
	 * Expected conditions: ("Document Name", "Type", "Status", creation date)<br> 
	 *  on "My Documents" list are displayed correctly . <br>
	 * 4. Click "Edit / View" button. <br>
	 * Expected results: "Document Name", "Document Description", file name) are displayed correctly. <br>	 	 
	 */

	@Parameters({ "userUserName", "groupName", "docTypeName", "documentName", "updatedDocumentName",
			"documentDescription", "updatedDocumentDescription", "filePath", "fileName", "updatedFilePath",
			"updatedFileName" })

	@Test(groups = { "editDocumentTests" }, priority = 1, enabled = true)
	public void editDocumentTest(String userUserName, String groupName, String docTypeName, String documentName,
			String updatedDocumentName, String documentDescription, String updatedDocumentDescription, String filePath,
			String fileName, String updatedFilePath, String updatedFileName) throws InterruptedException, IOException {

		mainPage.waitForLogoutButton();
		mainPage.clickCreateDocumentButton();
		newDocumentPage.createDocument(documentName, documentDescription, docTypeName, filePath, fileName);
		mainPage.clickMyDocumentsButton();
		myDocumentsPage.sendKeysSearchDocument(documentName);
		myDocumentsPage.clickEditViewDocument(documentName);
		editDocumentPage.waitForEditDocumentPage();
		editDocumentPage.clearDocNameField();
		editDocumentPage.sendKeysDocNameField(updatedDocumentName);
		editDocumentPage.clearDocDescriptionField();
		editDocumentPage.sendKeysDocDescriptionField(updatedDocumentDescription);
		editDocumentPage.waitForFileNameVisibility(fileName);
		editDocumentPage.clickRemoveFileButton(fileName);
		File file2 = new File(updatedFilePath);
		editDocumentPage.sendKeysFileUploadField(file2.getAbsolutePath());
		editDocumentPage.waitForFileNameVisibility(updatedFileName);
		editDocumentPage.clickUpdateButton();
		mainPage.waitForLogoutButton();
		myDocumentsPage.clearSearchDocumentField();
		myDocumentsPage.sendKeysSearchDocument(updatedDocumentName);
		myDocumentsPage.waitForButtonEditViewToBeClickable();
		assertTrue(myDocumentsPage.isDocumentNameDisplayed(updatedDocumentName),
				"Document name isn't displayed correctly on My documents list");
		documentID = myDocumentsPage.getIDbyDocumentName(updatedDocumentName);
		fileID = API.getFileDetails(documentID, sessionID);
		assertTrue(myDocumentsPage.getStatusByDocumentName(updatedDocumentName).equals("CREATED"),
				"Document status isn't displayed correctly on My documents list");
		myDocumentsPage.clickEditViewDocument(updatedDocumentName);
		editDocumentPage.waitForEditDocumentPage();
		System.out.println(editDocumentPage.getDocName());
		assertTrue(editDocumentPage.getDocName().equals(updatedDocumentName),
				"Document name isn't displayed correctly on Edit/View document page");
		assertTrue(editDocumentPage.getDocDescription().equals(updatedDocumentDescription),
				"Document description isn't displayed correctly on Edit/View document page");
		assertTrue(editDocumentPage.isFileNameDisplayed(updatedFileName),
				"Attached file name isn't displayed correctly on Edit/View document page");
		editDocumentPage.clickCancelButton();
		sessionID = GetSessionId.login("admin", "adminadmin");
		API.deleteFile(fileID, sessionID);
		API.deleteDocument(documentID, sessionID);
	}

}
