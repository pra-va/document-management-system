package test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;
import java.awt.AWTException;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.openqa.selenium.By;
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

public class NewDocumentTests extends AbstractTest {
	LoginPage loginPage;
	MainPage mainPage;
	NewDocumentPage newDocumentPage;
	MyDocumentsPage myDocumentsPage;
	EditDocumentPage editDocumentPage;
	String deleteUserApiURL;
	String date;

	@BeforeClass
	public void preconditions() {
		loginPage = new LoginPage(driver);
		mainPage = new MainPage(driver);
		newDocumentPage = new NewDocumentPage(driver);
		myDocumentsPage = new MyDocumentsPage(driver);
		editDocumentPage = new EditDocumentPage(driver);
		// deleteUserApiURL =
		// "http://akademijait.vtmc.lt:8180/dvs/api/delete/{username}";
	}

	@Parameters({ "adminUserName", "adminPassword" })
	@BeforeGroups({ "newDocumentTests" })
	public void login(String adminUserName, String adminPassword) {
		loginPage.sendKeysUserName(adminUserName);
		loginPage.sendKeysPassword(adminPassword);
		loginPage.clickButtonLogin();
	}

	@Parameters({ "newUserUserName", "newAdminUserName" })
	@AfterGroups("newDocumentTests")
	public void logout(String newUserUserName, String newAdminUserName) {
		// Unirest.delete(deleteUserApiURL).routeParam("username",
		// newUserUserName).asString();
		// Unirest.delete(deleteUserApiURL).routeParam("username",
		// newAdminUserName).asString();
		// mainPage.waitForLogoutButton();
		// mainPage.clickLogoutButton();
	}

	// TODO ADD PARAMETERS!!!!!!!!
	// @Parameters({ "docName", "docDescription", "docType","filePath", "fileName"})
	@Test(groups = { "newDocumentTests" }, priority = 1, enabled = true)
	public void createNewDocumentTest() throws InterruptedException{
		mainPage.waitForLogoutButton();
		mainPage.clickCreateDocumentButton();
		newDocumentPage.sendKeysDocNameField("newDoc12345");
		newDocumentPage.sendKeysDocDescriptionField("description");
		newDocumentPage.sendKeysSearchForDocType("docType");
		newDocumentPage.clickSelectSpecificDocTypeButton("docType");
		//WAIT!!		
		File file = new File("src/test/java/utilities/testFile.pdf");		
		newDocumentPage.sendKeysFileUploadField(file.getAbsolutePath());		
		newDocumentPage.waitForFileNameVisibility("testFile.pdf");
		newDocumentPage.clickCreateButton();
		mainPage.clickMyDocumentsButton();
		myDocumentsPage.sendKeysSearchDocument("newDoc12345");
		assertTrue(myDocumentsPage.isDocumentNameDisplayed("newDoc12345"),
				"Document name isn't displayed correctly on My documents list");
		String ID = myDocumentsPage.getIDbyDocumentName("newDoc12345"); // for doc delete
		date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());		
		//assertEquals(myDocumentsPage.getCreationDatebyDocumentName("newDoc12345"), date,
			//	"Document creation date isn't displayed correctly in ");
		assertTrue(myDocumentsPage.getStatusByDocumentName("newDoc12345").equals("CREATED"),
				"Document status isn't displayed correctly on My documents list");
		myDocumentsPage.clickEditViewDocument("newDoc12345");
		editDocumentPage.waitForEditDocumentPage();
		assertTrue(editDocumentPage.getDocName().equals("newDoc12345"),
				"Document name isn't displayed correctly on Edit document page");
		assertTrue(editDocumentPage.getDocDescription().equals("description"),
				"Document description isn't displayed correctly on Edit document page");
		
		//assertEquals("docType", editDocumentPage.getDocType(),
		//		"Document type isn't displayed correctly on Edit document page");
		 assertTrue(editDocumentPage.isFileNameDisplayed("testFile.pdf"), 
				 "Document name isn't displayed correctly on Edit document page");
		 editDocumentPage.clickCancelButton();
		 
		 //iskelti!!!!
		 //edit document test
		 
		 myDocumentsPage.sendKeysSearchDocument("newDoc12345");
		 myDocumentsPage.clickEditViewDocument("newDoc12345");
		 editDocumentPage.waitForEditDocumentPage();
		 editDocumentPage.clearDocNameField();
		 editDocumentPage.sendKeysDocNameField("newName");
		 editDocumentPage.clearDocDescriptionField();
		 editDocumentPage.sendKeysDocDescriptionField("newDescription");
		 //add new file
		 editDocumentPage.clickRemoveFileButton("test.pdf");
		 Thread.sleep(2000);
		 editDocumentPage.clickSubmitButton();
	}

}
