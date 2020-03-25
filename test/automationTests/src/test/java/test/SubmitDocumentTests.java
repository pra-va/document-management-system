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

public class SubmitDocumentTests extends AbstractTest {

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
	@BeforeGroups({ "submitDocument" })
	public void login(String userUserNameCR, String userPasswordCR) {
		loginPage.sendKeysUserName(userUserNameCR);
		loginPage.sendKeysPassword(userPasswordCR);
		loginPage.clickButtonLogin();
	}

	@AfterGroups("submitDocument")
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

	@Parameters({ "documentName", "documentDescription", "docTypeName", "filePath", "fileName", "UserUserNameSIGN",
			"UserPassswordSIGN" })
	@Test(groups = { "submitDocument" }, priority = 1, enabled = true)
	public void submitDocumentTest(String documentName, String documentDescription, String docTypeName, String filePath,
			String fileName, String UserUserNameSIGN, String UserPassswordSIGN)
			throws InterruptedException, IOException {

		mainPage.waitForLogoutButton();
		mainPage.clickCreateDocumentButton();
		newDocumentPage.createDocument(documentName, documentDescription, docTypeName, filePath, fileName);
		mainPage.waitForLogoutButton();
		mainPage.clickMyDocumentsButton();
		myDocumentsPage.sendKeysSearchDocument(documentName);
		sessionID = GetSessionId.login("admin", "adminadmin");
		documentID = myDocumentsPage.getIDbyDocumentName(documentName);
		fileID = API.getFileDetails(documentID, sessionID);
		myDocumentsPage.waitForDocumentVisibility(documentName);
		//myDocumentsPage.waitForButtonSubmitBeClickable();
		myDocumentsPage.clickButtonSubmit(documentName);
		//myDocumentsPage.waitForButtonSubmitNotBeClickable();

		myDocumentsPage.clearSearchDocumentField();
		myDocumentsPage.sendKeysSearchDocument(documentName);
		myDocumentsPage.waitForDocumentVisibility(documentName);
		assertTrue(myDocumentsPage.getStatusByDocumentName(documentName).equals("SUBMITTED"),
				"Submitted Document status isn't displayed correctly on My Documents page");
		mainPage.clickLogoutButton();
		loginPage.waitForLoginButton();
		loginPage.sendKeysUserName(UserUserNameSIGN);
		loginPage.sendKeysPassword(UserPassswordSIGN);
		loginPage.clickButtonLogin();
		mainPage.waitForLogoutButton();
		mainPage.clickSignDocumentButton();
		myDocumentsPage.waitForDocumentVisibility(documentName);
		signDocumentPage.sendKeysSearchDocument(documentName);
		assertTrue(signDocumentPage.isDocumentNameDisplayed(documentName),
				"Document isn't displayed on \"Sign Document\" page for User that can sign this type of document, it was not submitted correctly");
		API.deleteFile(fileID, sessionID);
		API.deleteDocument(documentID, sessionID);
	}

}
