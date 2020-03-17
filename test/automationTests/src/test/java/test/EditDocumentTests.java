package test;

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
		// deleteUserApiURL =
		// "http://akademijait.vtmc.lt:8180/dvs/api/delete/{username}";
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
		mainPage.clickCreateDocumentButton();
		newDocumentPage.sendKeysDocNameField("newDoc12345");
		newDocumentPage.sendKeysDocDescriptionField("description");
		newDocumentPage.sendKeysSearchForDocType("docType");
		newDocumentPage.clickSelectSpecificDocTypeButton("docType");
		// WAIT
		File file = new File("src/test/java/utilities/testFile.pdf");
		newDocumentPage.sendKeysFileUploadField(file.getAbsolutePath());
		newDocumentPage.waitForFileNameVisibility("testFile.pdf");
		newDocumentPage.clickCreateButton();
		mainPage.clickMyDocumentsButton();
		myDocumentsPage.sendKeysSearchDocument("newDoc12345");
		myDocumentsPage.clickEditViewDocument("newDoc12345");
		editDocumentPage.waitForEditDocumentPage();
		editDocumentPage.clearDocNameField();
		editDocumentPage.sendKeysDocNameField("newName");
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
		Thread.sleep(5000);
		driver.findElement(By.xpath("//button[@type='submit']")).click();
		//editDocumentPage.clickSubmitButton();
		myDocumentsPage.sendKeysSearchDocument("newName");
		assertTrue(myDocumentsPage.isDocumentNameDisplayed("newName"),
				"Document name isn't displayed correctly on My documents list");
		// ar pasikeicia id ? String ID =
		// myDocumentsPage.getIDbyDocumentName("newDoc12345"); // for doc delete
		assertTrue(myDocumentsPage.getStatusByDocumentName("newName").equals("CREATED"),
				"Document status isn't displayed correctly on My documents list");
		myDocumentsPage.clickEditViewDocument("newName");
		editDocumentPage.waitForEditDocumentPage();
		assertTrue(editDocumentPage.getDocName().equals("newName"),
				"Document name isn't displayed correctly on Edit document page");
		assertTrue(editDocumentPage.getDocDescription().equals("newDescription"),
				"Document description isn't displayed correctly on Edit document page");
		// assertEquals("docType", editDocumentPage.getDocType(),
		// "Document type isn't displayed correctly on Edit document page");
		assertTrue(editDocumentPage.isFileNameDisplayed("testFile2.pdf"),
				"Document name isn't displayed correctly on Edit document page");
		editDocumentPage.clickCancelButton();
	}

}
