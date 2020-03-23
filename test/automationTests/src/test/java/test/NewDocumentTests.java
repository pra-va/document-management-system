package test;

import org.testng.annotations.AfterClass;
import static org.testng.Assert.assertTrue;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterGroups;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeGroups;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import page.AdminNewUserPage;
import page.EditDocumentPage;
import page.LoginPage;
import page.MainPage;
import page.MyDocumentsPage;
import page.NewDocumentPage;
import utilities.API;
import utilities.GetSessionId;

public class NewDocumentTests extends AbstractTest {
	LoginPage loginPage;
	MainPage mainPage;
	NewDocumentPage newDocumentPage;
	MyDocumentsPage myDocumentsPage;
	AdminNewUserPage adminNewUserPage;
	EditDocumentPage editDocumentPage;	
	HttpResponse<JsonNode> documentInfoJson;
	String documentID;
	String fileID;	
	File file;
	Date date;
	String modifiedDate;
	String sessionID;

	@Parameters({ "groupName", "userFirstName", "userLastName", "userPassword", "userUserName", "docTypeName" })
	@BeforeClass
	public void preconditions(String groupName, String userFirstName, String userLastName, String userPassword,
			String userUserName, String docTypeName) throws IOException {
		loginPage = new LoginPage(driver);
		mainPage = new MainPage(driver);
		adminNewUserPage = new AdminNewUserPage(driver);
		newDocumentPage = new NewDocumentPage(driver);
		myDocumentsPage = new MyDocumentsPage(driver);
		editDocumentPage = new EditDocumentPage(driver);
		sessionID =  GetSessionId.login("admin", "adminadmin");
		API.createGroup("Group Two description", "[]", "[]", groupName, "[]", sessionID);
		API.createUser("[\"" + groupName + "\"]", userFirstName, userLastName, userPassword, userUserName, sessionID);
		API.createDocType("[]", "[\"" + groupName + "\"]", docTypeName, sessionID);
	}

	@Parameters({ "userUserName", "userPassword" })
	@BeforeGroups({ "newDocumentTests" })
	public void login(String userUserName, String userPassword) {
		loginPage.sendKeysUserName(userUserName);
		loginPage.sendKeysPassword(userPassword);
		loginPage.clickButtonLogin();
	}

	@AfterGroups("newDocumentTests")
	public void logout() {
		mainPage.waitForLogoutButton();
		mainPage.clickLogoutButton();
	}
	
	@Parameters({ "groupName", "docTypeName" })
	@AfterClass
	public void deleteEntities(String userUserName, String groupName, String docTypeName) throws UnirestException, IOException{				
		//API.deleteFile(fileID);
		//API.deleteDocument(documentID);
		API.deleteUser(userUserName, sessionID);
		API.deleteGroup(groupName, sessionID);
		API.deleteDoctype(docTypeName, sessionID);
	}
			
	/*-
	 * Test creates new document with attached file, checks if all properties are saved correctly in "My Documents" list 
	 * and "Edit Document" page.
	 * 
	 * Preconditions: admin is logged in the system, at least one group was created.
	 * 
	 * 
	 */
	
	// TODO ADD PARAMETERS!!!!!!!!
	@Parameters({ "filePath", "fileName" })
	/// @Parameters({ "docName", "docDescription", "docTypeName","filePath",
	/// "fileName"})
	@Test(groups = { "newDocumentTests" }, priority = 1, enabled = true)
	public void createNewDocumentTest(String filePath, String fileName) throws UnirestException, InterruptedException {
		mainPage.waitForLogoutButton();
		mainPage.clickCreateDocumentButton();			
		newDocumentPage.sendKeysDocNameField("7newDoc7");
		newDocumentPage.sendKeysDocDescriptionField("description");
		newDocumentPage.sendKeysSearchForDocType("docType14");
		newDocumentPage.clickSelectSpecificDocTypeButton("docType14");		
		file = new File(filePath);
		newDocumentPage.sendKeysFileUploadField(file.getAbsolutePath());
		newDocumentPage.waitForFileNameVisibility(fileName);		
		newDocumentPage.clickCreateButton();		
		mainPage.clickMyDocumentsButton();
		myDocumentsPage.sendKeysSearchDocument("7newDoc7");
		Thread.sleep(2000);
		assertTrue(myDocumentsPage.isDocumentNameDisplayed("7newDoc7"),
				"Document name isn't displayed correctly on My documents list");
		assertTrue(myDocumentsPage.getTypeByDocumentName("7newDoc7").equals("docType14"),
				"Document type isn't displayed correctly on My documents list");
		//This parameter is used for Delete Document API call
		documentID = myDocumentsPage.getIDbyDocumentName("7newDoc7"); 
	//	documentInfoJson = API.getFileDetails(documentID);
		//This parameter is used for Delete File API call
		//fileID = documentInfoJson.getBody().toString().substring(9, 26);
		System.out.println(fileID);
		date = new Date();
		modifiedDate= new SimpleDateFormat("yyyy-MM-dd").format(date);
		assertTrue(myDocumentsPage.getCreationDatebyDocumentName("7newDoc7").equals(modifiedDate),
		"Document creation date isn't displayed correctly on My documents list");		
		assertTrue(myDocumentsPage.getFileNameByDocumentName("7newDoc7").equals(fileName),
				"Attached file name isn't displayed correctly in File icon tooltip");
		assertTrue(myDocumentsPage.getStatusByDocumentName("7newDoc7").equals("CREATED"),
				"Document status isn't displayed correctly on My documents list");
		myDocumentsPage.clickEditViewDocument("7newDoc7");		
		editDocumentPage.waitForEditDocumentPage();		
		assertTrue(editDocumentPage.getDocName().equals("7newDoc7"),
				"Document name isn't displayed correctly on Edit document page");
		assertTrue(editDocumentPage.getDocDescription().equals("description"),
				"Document description isn't displayed correctly on Edit/View document page");
	
//		assertTrue(editDocumentPage.getDocType().equals("docType14"),
//				"Document type isn't displayed correctly on Edit document page");
		assertTrue(editDocumentPage.isFileNameDisplayed(fileName),
				"Attached file name isn't displayed correctly on Edit/View document page");
		editDocumentPage.clickCancelButton();
	}

}
