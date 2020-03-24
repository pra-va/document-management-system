package test;

import static org.testng.Assert.assertFalse;
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

public class SubmitSignDeclineDocumentTests extends AbstractTest {
	LoginPage loginPage;
	MainPage mainPage;
	NewDocumentPage newDocumentPage;
	MyDocumentsPage myDocumentsPage;
	EditDocumentPage editDocumentPage;
	SignDocumentPage signDocumentPage;
	SignDeclineDocumentPage signDeclineDocumentPage;
	String sessionID;

	
	@Parameters({ "groupDescription","groupName", "userFirstName", "userLastName", "userPassword", 
		"userUserName", "docTypeName", "signingUserFirstName", "signingUserLastName", "signingUserUserName", "signingUserPasssword"})
	@BeforeClass
	public void preconditions() throws IOException {
		loginPage = new LoginPage(driver);
		mainPage = new MainPage(driver);
		newDocumentPage = new NewDocumentPage(driver);
		myDocumentsPage = new MyDocumentsPage(driver);
		editDocumentPage = new EditDocumentPage(driver);
		signDocumentPage = new SignDocumentPage(driver);
		signDeclineDocumentPage = new SignDeclineDocumentPage(driver);	
		sessionID =  GetSessionId.login("admin", "adminadmin");	
//		API.createGroup(groupDescription, "[]", "[]", groupName, "[]", sessionID);
//		API.createUser("[\"" + groupName + "\"]", userFirstName, userLastName, userPassword, userUserName, sessionID);
//		API.createDocType("[]", "[\"" + groupName + "\"]", docTypeName, sessionID); 	
		//API.createUser("[\"" + groupName + "\"]", userFirstName, userLastName, userPassword, userUserName, sessionID);
	}

	@Parameters({ "adminUserName", "adminPassword" })
	@BeforeGroups({ "submitSignDeclineDocument" })
	public void login(String adminUserName, String adminPassword) {
		loginPage.sendKeysUserName(adminUserName);
		loginPage.sendKeysPassword(adminPassword);
		loginPage.clickButtonLogin();
	}

	// @Parameters({ "newUserUserName", "newAdminUserName" })
	@AfterGroups("submitSignDeclineDocument")
	public void logout() {
		// Unirest.delete(deleteUserApiURL).routeParam("username",
		// newUserUserName).asString();
		// Unirest.delete(deleteUserApiURL).routeParam("username",
		// newAdminUserName).asString();
		mainPage.waitForLogoutButton();
		mainPage.clickLogoutButton();
	}
	
	// @Parameters({ "userUserName", "groupName", "docTypeName"})
	@AfterClass
	public void deleteEntities() {
//		sessionID =  GetSessionId.login("admin", "adminadmin");				
//		API.deleteUser(userUserName, sessionID);
//		API.deleteGroup(groupName, sessionID);
//		API.deleteDoctype(docTypeName, sessionID);		
	}

	// TODO ADD PARAMETERS!!!!!!!!
	// create docType, create two groups, one that creates, other that signs docs,
	// create two users in different groups, create three docs, signs as user that
	// signs docs

	@Parameters({ "documentName", "documentDescription", "filePath", "fileName", "docTypeName"})
	@Test(groups = { "submitSignDeclineDocument" }, priority = 1, enabled = true)
	public void submitDocumentTest(String documentName, String documentDescription, String docTypeName, 
			String filePath, String fileName) throws InterruptedException {
		mainPage.clickCreateDocumentButton();
		newDocumentPage.createDocument(documentName, documentDescription, docTypeName, filePath, fileName);
		mainPage.waitForLogoutButton();
		mainPage.clickMyDocumentsButton();
		myDocumentsPage.sendKeysSearchDocument(documentName);
		myDocumentsPage.clickButtonSubmit(documentName);
		myDocumentsPage.clickButtonSubmitted();
		assertTrue(myDocumentsPage.getStatusByDocumentName(documentName).equals("SUBMITTED"),
				"Submitted Document status isn't displayed correctly on My Documents page");
		// TODO login as other user
		mainPage.clickLogoutButton();
		//loginPage.sendKeysUserName(adminUserName);
		//loginPage.sendKeysPassword(adminPassword);
		//loginPage.clickButtonLogin();
		
		mainPage.clickSignDocumentButton();
//		sessionID =  GetSessionId.login("admin", "adminadmin");	
//		API.deleteFile(fileID, sessionID);
//		API.deleteDocument(documentID, sessionID);
	}

	@Test(groups = { "submitSignDeclineDocument" }, priority = 1, enabled = true)
	public void submitDocumentWithoutFileTest() {
		// create doc without file
		mainPage.waitForLogoutButton();
		mainPage.clickMyDocumentsButton();
		myDocumentsPage.sendKeysSearchDocument("DOCtest");
		assertFalse(myDocumentsPage.isButtonSubmitEnabled(""),
				"It should't be possible to submit document without attached file");

	}

	@Test(groups = { "submitSignDeclineDocument" }, priority = 1, enabled = false)
	public void signDocumentTest() {

		// Create document
		// Submit document
		// log in as user of signing group
		mainPage.waitForLogoutButton();
		mainPage.clickSignDocumentButton();
		signDocumentPage.clickButtonSignDecline("");
		signDeclineDocumentPage.waitForSignDeclineDocumentPage();
		// TODO assert data

		signDeclineDocumentPage.clickSignButton();
		// TODO log in as created user
		mainPage.waitForLogoutButton();
		mainPage.clickMyDocumentsButton();
		myDocumentsPage.clickButtonAccepted();
		assertTrue(myDocumentsPage.isDocumentNameDisplayed(""),
				"Signed document isn't displayed on My Document list filtered by status \"ACCEPTED\"");
		assertTrue(myDocumentsPage.getStatusByDocumentName("").equals("ACCEPTED"),
				"Document status is't didplayed correctly on My Document list filtered by status \"ACCEPTED\"");
	}

	@Test(groups = { "submitSignDeclineDocument" }, priority = 1, enabled = false)
	public void declineDocumentTest() {
		// Create document
		// Submit document
		// log in as user of signing group
		mainPage.waitForLogoutButton();
		mainPage.clickSignDocumentButton();
		signDocumentPage.clickButtonSignDecline("");
		signDeclineDocumentPage.sendKeysDeclineReasonField("Bad timing");
		signDeclineDocumentPage.clickDeclineButton();
		// log in as user who created doc
		mainPage.waitForLogoutButton();
		mainPage.clickMyDocumentsButton();
		myDocumentsPage.clickButtonDeclined();
		assertTrue(myDocumentsPage.isDocumentNameDisplayed(""),
				"Declined document isn't displayed on My Document list filtered by status \"DECLINED\"");
		assertTrue(myDocumentsPage.getStatusByDocumentName("").equals("DECLINED"),
				"Document status is't didplayed correctly on My Document list filtered by status \"DECLINED\"");

	}
	
	@Test(groups = { "submitSignDeclineDocument" }, priority = 1, enabled = false)
	public void declineDocumentEmptyDeclineReasonFieldTest() {
		// Create document
		// Submit document
		// log in as user of signing group
		mainPage.waitForLogoutButton();
		mainPage.clickSignDocumentButton();
		signDocumentPage.clickButtonSignDecline("");
		signDeclineDocumentPage.waitForSignDeclineDocumentPage();		
		signDeclineDocumentPage.clickDeclineButton();
		// log in as user who created doc
		mainPage.waitForLogoutButton();
		mainPage.clickMyDocumentsButton();
		myDocumentsPage.clickButtonDeclined();
		
		assertFalse(myDocumentsPage.isDocumentNameDisplayed(""),
				"It should be impossible to decline document with empty Decline reason field");
		
	}
	
	

}
