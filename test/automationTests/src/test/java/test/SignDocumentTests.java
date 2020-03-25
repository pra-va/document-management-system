package test;

import static org.testng.Assert.assertTrue;

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
import page.SignDeclineDocumentPage;
import page.SignDocumentPage;
import utilities.API;
import utilities.GetSessionId;

public class SignDocumentTests extends AbstractTest {

	LoginPage loginPage;
	MainPage mainPage;
	NewDocumentPage newDocumentPage;
	MyDocumentsPage myDocumentsPage;
	EditDocumentPage editDocumentPage;
	SignDocumentPage signDocumentPage;
	SignDeclineDocumentPage signDeclineDocumentPage;
	String sessionID = "";
	String fileID;
	String documentID;

	@Parameters({ "adminUserName", "adminPassword", "groupDescriptionCR", "groupNameCR", "groupDescriptionSIGN",
			"groupNameSIGN", "userFirstNameCR", "userLastNameCR", "userPasswordCR", "userUserNameCR", "docTypeName",
			"UserFirstNameSIGN", "UserLastNameSIGN", "UserUserNameSIGN", "UserPassswordSIGN" })
	@BeforeClass
	public void preconditions(String adminUserName, String adminPassword, String groupDescriptionCR, String groupNameCR,
			String groupDescriptionSIGN, String groupNameSIGN, String userFirstNameCR, String userLastNameCR,
			String userPasswordCR, String userUserNameCR, String docTypeName, String UserFirstNameSIGN,
			String UserLastNameSIGN, String UserUserNameSIGN, String UserPassswordSIGN) throws IOException {
		loginPage = new LoginPage(driver);
		mainPage = new MainPage(driver);
		newDocumentPage = new NewDocumentPage(driver);
		myDocumentsPage = new MyDocumentsPage(driver);
		editDocumentPage = new EditDocumentPage(driver);
		signDocumentPage = new SignDocumentPage(driver);
		signDeclineDocumentPage = new SignDeclineDocumentPage(driver);
		sessionID = GetSessionId.login(adminUserName, adminPassword);
		API.createGroup(groupDescriptionCR, "[]", "[]", groupNameCR, "[]", sessionID);
		API.createGroup(groupDescriptionSIGN, "[]", "[]", groupNameSIGN, "[]", sessionID);
		API.createUser("[\"" + groupNameCR + "\"]", userFirstNameCR, userLastNameCR, userPasswordCR, userUserNameCR,
				sessionID);
		API.createDocType("[\"" + groupNameSIGN + "\"]", "[\"" + groupNameCR + "\"]", docTypeName, sessionID);
		API.createUser("[\"" + groupNameSIGN + "\"]", UserFirstNameSIGN, UserLastNameSIGN, UserPassswordSIGN,
				UserUserNameSIGN, sessionID);
	}

	@Parameters({ "userUserNameCR", "userPasswordCR" })
	@BeforeGroups({ "signDocument" })
	public void login(String userUserNameCR, String userPasswordCR) {
		loginPage.sendKeysUserName(userUserNameCR);
		loginPage.sendKeysPassword(userPasswordCR);
		loginPage.clickButtonLogin();
	}

	@AfterGroups("signDocument")
	public void logout() {
		mainPage.waitForLogoutButton();
		mainPage.clickLogoutButton();
	}

	@Parameters({ "adminUserName", "adminPassword", "userUserNameCR", "UserUserNameSIGN", "groupNameCR",
			"groupNameSIGN", "docTypeName" })
	@AfterClass
	public void deleteEntities(String adminUserName, String adminPassword, String userUserNameCR,
			String UserUserNameSIGN, String groupNameCR, String groupNameSIGN, String docTypeName) throws IOException {
		sessionID = GetSessionId.login(adminUserName, adminPassword);
		API.deleteUser(userUserNameCR, sessionID);
		API.deleteUser(UserUserNameSIGN, sessionID);
		API.deleteGroup(groupNameCR, sessionID);
		API.deleteGroup(groupNameSIGN, sessionID);
		API.deleteDoctype(docTypeName, sessionID);

	}

	/**-	 
	 * Preconditions: <br>
	 * - two group were created;<br>
	 * - two users were created and added to groups, one user per group;<br>
	 * - one document type was created, one group rights were set to "Create", other to "Sign";<br>
	 * - user with document creation rights is logged in the system.<br>
	 * 	
	 * Test steps: 	 <br>	  
	 * 1. Click "Create document" button. <br>
	 * 2. Fill fields in form: "Document Name", "Document Description", search for document type, click on document type name,<br>
	 *    click "Choose File", select file, click "Open".<br>
	 * 3. Click "Create" button. 	<br> 
	 * 4. Open "My Documents" list.<br>
	 * 5. Click button "Submit".<br>
	 * 6. Click button "Log out".<br>
	 * 7. Log in as user who can sign document: fill "Username", "Password", click "Log in".<br>
	 * 8. Click button "Sign Document".<br>
	 * 9. Click button "Sign / Decline".<br>
	 * 10. Click button "Log out".<br>
	 * 11.  Log in as user created document: fill "Username", "Password", click "Log in".<br>
	 * 12. Click "My Documents" button.	<br>
	 * Expected results: document status is "ACCEPTED" on My Documents list.  		 
	 */
	@Parameters({ "adminUserName", "adminPassword", "documentName", "documentDescription", "docTypeName", "filePath",
			"fileName", "UserUserNameSIGN", "UserPassswordSIGN", "userUserNameCR", "userPasswordCR" })
	@Test(groups = { "signDocument" }, priority = 1, enabled = true)
	public void signDocumentTest(String adminUserName, String adminPassword, String documentName,
			String documentDescription, String docTypeName, String filePath, String fileName, String UserUserNameSIGN,
			String UserPassswordSIGN, String userUserNameCR, String userPasswordCR)
			throws InterruptedException, IOException {

		mainPage.waitForLogoutButton();
		mainPage.clickCreateDocumentButton();
		newDocumentPage.createDocument(documentName, documentDescription, docTypeName, filePath, fileName);
		mainPage.waitForLogoutButton();
		mainPage.clickMyDocumentsButton();
		myDocumentsPage.sendKeysSearchDocument(documentName);
		sessionID = GetSessionId.login(adminUserName, adminPassword);
		documentID = myDocumentsPage.getIDbyDocumentName(documentName);
		fileID = API.getFileDetails(documentID, sessionID);
		myDocumentsPage.waitForDocumentVisibility(documentName);
		myDocumentsPage.waitForButtonSubmitBeClickable();
		myDocumentsPage.clickButtonSubmit(documentName);
		mainPage.clickLogoutButton();
		loginPage.waitForLoginButton();
		loginPage.sendKeysUserName(UserUserNameSIGN);
		loginPage.sendKeysPassword(UserPassswordSIGN);
		loginPage.clickButtonLogin();
		mainPage.waitForLogoutButton();
		mainPage.clickSignDocumentButton();
		signDocumentPage.sendKeysSearchDocument(documentName);
		signDocumentPage.clickButtonSignDecline(documentName);
		signDeclineDocumentPage.waitForSignDeclineDocumentPage();
		signDeclineDocumentPage.clickSignButton();
		mainPage.waitForLogoutButtonToBeClickable();
		mainPage.clickLogoutButton();
		loginPage.waitForLoginButton();
		loginPage.sendKeysUserName(userUserNameCR);
		loginPage.sendKeysPassword(userPasswordCR);
		loginPage.clickButtonLogin();
		mainPage.waitForLogoutButton();
		mainPage.clickMyDocumentsButton();
		myDocumentsPage.sendKeysSearchDocument(documentName);
		assertTrue(myDocumentsPage.getStatusByDocumentName(documentName).equals("ACCEPTED"),
				"Document status is't \"ACCEPTED\", it wasn't signed");
		API.deleteFile(fileID, sessionID);
		API.deleteDocument(documentID, sessionID);
	}

}
