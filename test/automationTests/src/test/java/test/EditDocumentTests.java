package test;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

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
import utilities.API;

public class EditDocumentTests extends AbstractTest {
	LoginPage loginPage;
	MainPage mainPage;
	NewDocumentPage newDocumentPage;
	MyDocumentsPage myDocumentsPage;
	EditDocumentPage editDocumentPage;

	@BeforeClass
	public void preconditions() {
		loginPage = new LoginPage(driver);
		mainPage = new MainPage(driver);
		newDocumentPage = new NewDocumentPage(driver);
		myDocumentsPage = new MyDocumentsPage(driver);
		editDocumentPage = new EditDocumentPage(driver);
		
//		deleteUserApiURL = "http://akademijait.vtmc.lt:8180/dvs/api/delete/{username}";
//		deleteGroupApiURL = "http://akademijait.vtmc.lt:8180/dvs/api/group/{groupname}/delete";
//		deleteDocTypeApiURL = "http://akademijait.vtmc.lt:8180/dvs/api/doct/delete/{name}";
//		documentDetailsApiURL = "http://akademijait.vtmc.lt:8180/dvs/api/files/info/docname/{docname}";
//		deleteFileApiURL = "http://akademijait.vtmc.lt:8180/dvs/api/files/delete/{UID}";
//		deleteDocumentApi = "http://akademijait.vtmc.lt:8180/dvs/api/doc/delete/{name}";
//		API.createGroup("Group Two description", "[\"\"]", "[\"\"]", groupName, "[\"\"]");
//		API.createUser("[\"" + groupName + "\"]", userFirstName, userLastName, userPassword, userUserName);
//		API.createDocType("[\"\"]", "[\"" + groupName + "\"]", docTypeName); 
		
		
	}

	@Parameters({ "adminUserName", "adminPassword" })
	@BeforeGroups({ "editDocumentTests" })
	public void login(String adminUserName, String adminPassword) {
		loginPage.sendKeysUserName(adminUserName);
		loginPage.sendKeysPassword(adminPassword);
		loginPage.clickButtonLogin();
	}

	@Parameters({ "newUserUserName", "newAdminUserName" })
	@AfterGroups("editDocumentTests")
	public void logout(String newUserUserName, String newAdminUserName) {
		// Unirest.delete(deleteUserApiURL).routeParam("username",
		// newUserUserName).asString();
		// mainPage.waitForLogoutButton();
		// mainPage.clickLogoutButton();
	}

	// @Parameters({ "", "newAdminUserName" })
	@Test(groups = { "editDocumentTests" }, priority = 1, enabled = true)
	public void editDocumentTest() throws InterruptedException {

		// !! sukurti dok per API
		mainPage.waitForLogoutButton();
//		mainPage.clickCreateDocumentButton();
//		newDocumentPage.sendKeysDocNameField("7newDoc");
//		newDocumentPage.sendKeysDocDescriptionField("description");
//		newDocumentPage.sendKeysSearchForDocType("docType");
//		newDocumentPage.clickSelectSpecificDocTypeButton("docType");
//		// WAIT
//		File file = new File("src/test/java/utilities/testFile.pdf");
//		newDocumentPage.sendKeysFileUploadField(file.getAbsolutePath());
//		newDocumentPage.waitForFileNameVisibility("testFile.pdf");
//		newDocumentPage.clickCreateButton();
		mainPage.clickMyDocumentsButton();
		myDocumentsPage.sendKeysSearchDocument("7newDoc");
		myDocumentsPage.clickEditViewDocument("7newDoc");
		editDocumentPage.waitForEditDocumentPage();
		editDocumentPage.clearDocNameField();
		editDocumentPage.sendKeysDocNameField("DOCtest");
		editDocumentPage.clearDocDescriptionField();
		editDocumentPage.sendKeysDocDescriptionField("newDescription");
		// add new file
		Thread.sleep(3000);
		editDocumentPage.clickRemoveFileButton("testFile.pdf");
		Thread.sleep(3000);
		// WAIT!
		File file2 = new File("src/test/java/utilities/testFile2.pdf");
		editDocumentPage.sendKeysFileUploadField(file2.getAbsolutePath());
		editDocumentPage.waitForFileNameVisibility("testFile2.pdf");		
		editDocumentPage.clickUpdateButton();
	
		driver.findElement(By.xpath("//th[contains(text(),'Created')]")).click();
		myDocumentsPage.clearSearchDocumentField();
		myDocumentsPage.sendKeysSearchDocument("DOCtest");			
		assertTrue(myDocumentsPage.isDocumentNameDisplayed("DOCtest"),
				"Document name isn't displayed correctly on My documents list");
		String ID = myDocumentsPage.getIDbyDocumentName("newDoc12345"); // for doc delete
		assertTrue(myDocumentsPage.getStatusByDocumentName("DOCtest").equals("CREATED"),
				"Document status isn't displayed correctly on My documents list");
		myDocumentsPage.clickEditViewDocument("DOCtest");
		editDocumentPage.waitForEditDocumentPage();
		System.out.println(editDocumentPage.getDocName());
		assertTrue(editDocumentPage.getDocName().equals("DOCtest"),
				"Document name isn't displayed correctly on Edit/View document page");
		assertTrue(editDocumentPage.getDocDescription().equals("newDescription"),
				"Document description isn't displayed correctly on Edit/View document page");
		// assertEquals("docType", editDocumentPage.getDocType(),
		// "Document type isn't displayed correctly on Edit document page");
		assertTrue(editDocumentPage.isFileNameDisplayed("testFile2.pdf"),
				"Attached file name isn't displayed correctly on Edit/View document page");
		assertFalse(editDocumentPage.isFileNameDisplayed("testFile.pdf"),
				"File was not removed");
		editDocumentPage.clickCancelButton();
	}

}
