package test;

import java.io.IOException;

import org.testng.annotations.AfterGroups;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeGroups;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import page.DocTypeListPage;
import page.EditDocTypePage;
import page.LoginPage;
import page.MainPage;
import utilities.API;

public class EditDocTypeTests extends AbstractTest {
	LoginPage loginPage;
	MainPage mainPage;
	EditDocTypePage editDocPage;
	DocTypeListPage docListPage;

	@BeforeClass
	public void preconitions() {
		loginPage = new LoginPage(driver);
		mainPage = new MainPage(driver);
		docListPage = new DocTypeListPage(driver);
		editDocPage = new EditDocTypePage(driver);
	}

	@Parameters({ "adminUserName", "adminPasswrod" })
	@BeforeGroups("editDocType")
	public void login(String p1, String p2) {
		loginPage.sendKeysUserName(p1);
		loginPage.sendKeysPassword(p2);
		loginPage.clickButtonLogin();
	}

	@AfterGroups("editDocType")
	public void logout() {
		mainPage.waitForLogoutButton();
		mainPage.clickLogoutButton();
	}

	@Parameters({ "docTypeName", "groupName" })
	@Test(groups = { "editDocType" }, priority = 0, enabled = true)
	public void editDocTypeNameTest(String p1, String p2) throws IOException {
		System.out.println("labas");
		// editDocPage.createDocType("Crossroads", "Crossroads", "test1");
		// editDocPage.createGroup("asd", "newDoctype", "newDoctype", "qwerty",
		// "adminas");
		//API.createGroup("asd", " ", " ", "qwerty", " ");
	}
}
