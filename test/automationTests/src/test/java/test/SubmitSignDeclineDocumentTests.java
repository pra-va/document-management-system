package test;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

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

public class SubmitSignDeclineDocumentTests extends AbstractTest {
	LoginPage loginPage;
	MainPage mainPage;
	NewDocumentPage newDocumentPage;
	MyDocumentsPage myDocumentsPage;
	EditDocumentPage editDocumentPage;
	SignDocumentPage signDocumentPage;
	SignDeclineDocumentPage signDeclineDocumentPage;

	@BeforeClass
	public void preconditions() {
		loginPage = new LoginPage(driver);
		mainPage = new MainPage(driver);
		newDocumentPage = new NewDocumentPage(driver);
		myDocumentsPage = new MyDocumentsPage(driver);
		editDocumentPage = new EditDocumentPage(driver);
		signDocumentPage = new SignDocumentPage(driver);
		signDeclineDocumentPage = new SignDeclineDocumentPage(driver);
		
		// deleteUserApiURL =
		// "http://akademijait.vtmc.lt:8180/dvs/api/delete/{username}";
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
	
	@AfterClass
	public void deleteEntities() {
		
	}

	// TODO ADD PARAMETERS!!!!!!!!
	// create docType, create two groups, one that creates, other that signs docs,
	// create two users in different groups, create three docs, signs as user that
	// signs docs

	// @Parameters({ "docName", "docDescription", "docType","filePath", "fileName"})
	@Test(groups = { "submitSignDeclineDocument" }, priority = 1, enabled = true)
	public void submitDocumentTest() throws InterruptedException {
		mainPage.waitForLogoutButton();
		mainPage.clickMyDocumentsButton();
		myDocumentsPage.sendKeysSearchDocument("DOCtest");
		myDocumentsPage.clickButtonSubmit("DOCtest");
		myDocumentsPage.clickButtonSubmitted();
		assertTrue(myDocumentsPage.getStatusByDocumentName("DOCtest").equals("SUBMITTED"),
				"Submitted Document status isn't displayed correctly on My Documents page");
		// TODO login as other user
		mainPage.clickSignDocumentButton();

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
