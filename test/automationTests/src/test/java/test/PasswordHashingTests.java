package test;

import static org.testng.Assert.assertFalse;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.Base64;

import org.apache.commons.io.IOUtils;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.json.JSONException;
import org.json.JSONString;
import org.json.simple.JSONObject;
import org.json.JSONTokener;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;
import org.openqa.selenium.By;
import org.openqa.selenium.json.Json;
import org.testng.annotations.AfterGroups;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeGroups;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.google.common.io.Files;
import com.mashape.unirest.http.Headers;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.ObjectMapper;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.mashape.unirest.http.options.Option;
import com.mashape.unirest.http.options.Options;
import com.mashape.unirest.request.GetRequest;
import com.mashape.unirest.request.body.Body;
import com.mashape.unirest.request.body.MultipartBody;

import page.LoginPage;
import page.MainPage;
import utilities.API;
import utilities.GetSessionId;

public class PasswordHashingTests extends AbstractTest {
	LoginPage loginPage;
	MainPage mainPage;
	String createUserApiURL;
	String deleteUserApiURL;
	String updateUserApiURL;
	String getPasswordApiURL;
	String postUserApi;
	String createAdminApiURL;

	@BeforeClass
	public void preconditions() {
		loginPage = new LoginPage(driver);
		mainPage = new MainPage(driver);
	}

	@Parameters({ "adminUserName", "adminPassword" })
	@BeforeGroups({ "passwordHashingTests" })
	public void login(String adminUserName, String adminPassword) {
		loginPage.sendKeysUserName(adminUserName);
		loginPage.sendKeysPassword(adminPassword);
		loginPage.clickButtonLogin();
	}

	/*-
	 * Test checks if:
	 *   - new User Password is hashed.
	 * Preconditions:  
	 *   - new user is created.
	 * Test steps:
	 * 1. Get HTTP response of user password by username.
	 * 2. Check if HTTP response is not equal to password.
	 */
	@Parameters({ "adminUserName", "adminPassword" })
	@Test(groups = { "passwordHashingTests" }, priority = 1, enabled = true)
	public void passwordHashingTest(String adminUserName, String adminPassword)
			throws UnirestException, ClientProtocolException, IOException {

//		String hashedPassword = Unirest.get("http://akademijait.vtmc.lt:8180/dvs/api/testingonly/returnpass/{username}")
//				.routeParam("username", adminUserName).asString().getBody();
//		
//		assertFalse(hashedPassword.equals(adminPassword), "User password isn't hashed");	
		   
//		GetSessionId getSessionID = new GetSessionId();
//		String sessionID = getSessionID.login("admin", "adminadmin");
//		System.out.println(sessionID);
//		API.createUser("[\"" + "" + "\"]", "testApi", "testApi", "testApi", "123456789", sessionID);
		//API.createUser("[\"" + groupName + "\"]", userFirstName, userLastName, userPassword, userUserName);	
		
		String sessionID =  GetSessionId.login("admin", "adminadmin");
		System.out.println(sessionID);
	//	String id = API.getFileDetails("1584905808250", sessionID);
	//	System.out.println(id.substring(10, 20));
		API.createUser("[]", "APItestFirstName", "APItestLastName", "12345678", "testAPIuserName4", sessionID);
		//API.createAdmin("[\"\"]", "APItesAdminFirstName", "APItestAdminName", "12345678", "testAdmin", sessionID);
		//API.createGroup("description", "[\"\"]", "[\"\"]", "testGroup", "[\"\"]", sessionID);
		API.createDocType("[]", "[]", "testDocType10", sessionID); //neveikia!!!!1
		//API.deleteUser("testAPIuserName3", sessionID);
		//API.deleteGroup("testGroup", sessionID);docType14
	//	API.deleteDoctype("testDocType", sessionID);
		//API.deleteFile("20200322191504050", sessionID);
		//API.deleteDocument("1584904503935", sessionID);
				
				
	}
	
	/*-
	 * Test checks if:
	 *   - User Password is hashed after edition.
	 * Preconditions: 
	 *   - admin is logged in the system (API URL's are locked for other users);
	 *   - user was created and his/her password was edited.
	 * Test steps:
	 * 1. Edit user password.
	 * 2. Get HTTP response of user password by username.
	 * 3. Check if HTTP response is not equal to password.
	 */
	 @Test(groups = { "passwordHashingTests" }, priority = 1, enabled = false)
	 public void editedPasswordHashingTest() throws IOException {

//		String hashedPassword = Unirest.get("http://akademijait.vtmc.lt:8180/dvs/api/testingonly/returnpass/{username}")
//				.routeParam("username", adminUserName).asString().getBody();
//
//		assertFalse(hashedPassword.equals(adminPassword), "Edited password isn't hashed");

	// String inputJson = {

	// }
		
	}

	@AfterGroups("passwordHashingTests")
	public void logout() {
		mainPage.clickLogoutButton();
	}
}
