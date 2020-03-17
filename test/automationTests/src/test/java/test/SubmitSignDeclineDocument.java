package test;

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

public class SubmitSignDeclineDocument extends AbstractTest{
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
	//create docType, create two groups, one that creates, other that signs docs, 
	//create two users in different groups, create three docs, signs as user that signs docs
	
	// @Parameters({ "docName", "docDescription", "docType","filePath", "fileName"})
	@Test(groups = { "submitSignDeclineDocumentTests" }, priority = 1, enabled = true)
	public void submitDocumentTest(){
		
	}
	
	
	@Test(groups = { "submitSignDeclineDocumentTests" }, priority = 1, enabled = true)
	public void signDocumentTest(){
		
	}
	
	@Test(groups = { "submitSignDeclineDocumentTests" }, priority = 1, enabled = true)
	public void declineDocumentTest(){
		
	}
		
	
}
