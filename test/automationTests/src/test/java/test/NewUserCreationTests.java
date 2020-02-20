package test;

import static org.testng.Assert.assertTrue;
import java.io.File;
import java.io.IOException;


import org.apache.commons.io.FileUtils;
import org.json.JSONObject;
import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterGroups;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeGroups;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.thoughtworks.xstream.XStream;

import page.AdminNewUserPage;
import page.EditUserPage;
import page.LoginPage;
import page.MainPage;
import page.ProfilePage;
import page.UserListPage;
import utilities.User;

public class NewUserCreationTests extends AbstractTest {
	LoginPage loginPage;
	MainPage mainPage;
	AdminNewUserPage adminNewUserPage;
	UserListPage userListPage;
	EditUserPage editUserPage;
	ProfilePage profilePage;
	WebDriverWait wait;
	XStream xstream;
	User newUser;
	User newAdmin;
	User admin;
	User wrongInfo;

	@BeforeClass
	public void preconditions() {
		loginPage = new LoginPage(driver);
		mainPage = new MainPage(driver);
		adminNewUserPage = new AdminNewUserPage(driver);
		userListPage = new UserListPage(driver);
		editUserPage = new EditUserPage(driver);
		profilePage = new ProfilePage(driver);
		wait = new WebDriverWait(driver, 2);
		xstream = new XStream();
		XStream.setupDefaultSecurity(xstream);
		xstream.allowTypesByWildcard(new String[] { "utilities.User" });
		try {
			newAdmin = (User) xstream.fromXML(FileUtils.readFileToString(new File("src/test/resources/newAdmin.xml")));
			newUser = (User) xstream.fromXML(FileUtils.readFileToString(new File("src/test/resources/newUser.xml")));
			admin = (User) xstream.fromXML(FileUtils.readFileToString(new File("src/test/resources/admin.xml")));
			wrongInfo = (User) xstream
					.fromXML(FileUtils.readFileToString(new File("src/test/resources/wrongInfo.xml")));
		} catch (IOException e) {
			e.printStackTrace();
		}
			
	}

	@BeforeGroups("newUserCreationTests")
	public void login() {
		loginPage.sendKeysUserName(admin.getUserName());
		loginPage.sendKeysPassword(admin.getPassWord());
		loginPage.clickButtonLogin();
	}
	
//	@AfterGroups("newUserCreationTests")
//	public void logout() {		
//		mainPage.clickLogoutButton();
//	}

	/*-
	 * Test creates new user with admin role, checks if all properties are saved correctly in user list, 
	 * "Edit user" page and "Profile" page, checks login to the system with new user's credentials:
	 * 1. Login to the system as an admin.
	 * 2. Click "Admin" menu, "New user" option. 
	 * 3. Fill fields in New User form: "First Name", "Last Name", "Username", "Password", 
	 * check "Yes" on "Admin" selection, search for a group name, click button "Add", click button "Create". 
	 * 4. Click "Admin" menu, "Users" option. 
	 * 5. Search for created Username. 
	 * 6. Check if properties ("First Name", "Last Name", "Username", "Role") on a list are displayed correctly . 
	 * 7. Click "Edit / View" button. 
	 * 8. Check if all properties ("First Name", "Last Name", "Username", "Role", groups) are displayed correctly. 
	 * 9. Click button "Cancel". 
	 * 10. Click button "Logout". 
	 * 11. Login to the system using new admin's username and password, click button "Login". 
	 * 12. Check if: 
	 *     - buttons "Create Document", "Sign Document", "My Documents" and same icons are visible;
	 *     - dropdown "Admin" with it's options ("New User", "New Group", "New Document Type", "Users", 
	 *       "Groups", "Document Types") is clickable; 
	 *     - buttons "Profile" and "Logout" are clickable; 
	 * 13. Check if all user data on Profile Page is displayed correctly.
	 */

	@Test(groups = { "newUserCreationTests" }, priority = 1, enabled = true)
	public void newAdminCreationTest() throws UnirestException {
				

	
		JtwigTemplate template = JtwigTemplate.classpathTemplate("user");
		JtwigModel model = JtwigModel.newModel()
                .with("name", "nnnnnnn")
                .with("password", "555")
                .with("surname", "kkkkkkk")                
                .with("username", "llllll");
		System.out.println(template.render(model).toString());
	
		String postApi = "http://akademijait.vtmc.lt:8180/dvs/api/createuser";
			Unirest.post(postApi)
		        .header("accept", "application/json")
		        .header("Content-Type", "application/json")
		        .body(template.render(model))
		        .asJson();
			
			String deleteUserApi = "http://akademijait.vtmc.lt:8180/dvs/api/delete/{username}";
			String deleteGroupApi = "http://akademijait.vtmc.lt:8180/dvs/api/group/{groupname}/delete";			
			String deleteDocTypeApi = "http://akademijait.vtmc.lt:8180/dvs/api/doct/delete/{name}";
			String deleteDocumentApi = "http://akademijait.vtmc.lt:8180/dvs/api/doc/delete/{name}";
			
			//Unirest.delete(deleteGroupApi).routeParam("groupname", "group1").asString(); //dar neveikia
			//Unirest.delete(deleteDocTypeApi).routeParam("name", "test1").asString(); //veikia
			//Unirest.delete(deleteDocumentApi).routeParam("name", "someDocName").asString(); //dar neveikia
			       
		
//		String searchQueryApi = "http://akademijait.vtmc.lt:8180/dvs/api/users";
//
//		JsonNode body = Unirest.get(searchQueryApi)
//		                        .asJson()
//		                        .getBody();
//		System.out.println(body);         // gives the full json response
		
		
		
	
		
//		mainPage.clickAdminButton();
//		mainPage.clickAdminNewUserButton();
//		adminNewUserPage.sendKeysFirstName(newAdmin.getFirstName());
//		adminNewUserPage.sendKeysLastName(newAdmin.getLastName());		
//		adminNewUserPage.sendKeysUserName(newAdmin.getUserName());		
//		adminNewUserPage.sendKeysPassword(newAdmin.getPassWord());		
//		adminNewUserPage.checkShowPassword();
//		adminNewUserPage.clickAdminRadio();
//		adminNewUserPage.sendKeysSearchGroupToAdd(newAdmin.getGroupOne());
//		adminNewUserPage.clickAddSpecificGroupButton(newAdmin.getGroupOne());
//		adminNewUserPage.clickCreateButton();
//		assertTrue(adminNewUserPage.isCreateButtonDisplayed(), "New user isn't created");
//		driver.navigate().refresh();
//		mainPage.clickAdminButton();
//		mainPage.clickAdminUsersButton();		
//		userListPage.sendKeysSearchForUser(newAdmin.getUserName());			
//		assertTrue(userListPage.getFirstNameFromUserListByUsername(newAdmin.getUserName()).equals(newAdmin.getFirstName()),
//				"Admin's First Name isn't displayed correctly in user list");
//		assertTrue(userListPage.getLastNameFromUserListByUsername(newAdmin.getUserName()).equals(newAdmin.getLastName()),
//				"Admin's Last Name isn't displayed correctly in user list");
//		assertTrue(userListPage.getRoleFromUserListByUsername(newAdmin.getUserName()).equals(newAdmin.getRole()),
//				"Admin's role isn't displayed correctly in user list");
//		userListPage.clickViewEditSpecificUserButton(newAdmin.getUserName());		
//		Thread.sleep(1000); ///////!!!!!
//		assertTrue(editUserPage.getFirstName().equals(newAdmin.getFirstName()),
//				"Admin First Name isn't displayed correctly");
//		assertTrue(editUserPage.getLastName().equals(newAdmin.getLastName()), "Admin Last Name isn't displayed correctly");
//		assertTrue(editUserPage.isRadioButtonAdminSelected(), "Admin's role isn't displayed correctly");
//		editUserPage.sendKeysSearchUsersGroups(newAdmin.getGroupOne());
//		assertTrue(
//				driver.findElement(By.xpath("//td[contains(text(), '" + newAdmin.getGroupOne() + "')]")).isDisplayed(),
//				"User was not added to the group correctly");		
//		editUserPage.clickCancelButton();
//		mainPage.clickLogoutButton();	
//		loginPage.sendKeysUserName(newAdmin.getUserName());
//		loginPage.sendKeysPassword(newAdmin.getPassWord());
//		loginPage.clickButtonLogin();
//		mainPage.clickProfileButton();		
//		assertTrue(profilePage.getTextUsername().equals(newAdmin.getUserName()),
//				"Admin's Username isn't shown correctly in profile page");
//				assertTrue(profilePage.getTextFirstName().equals(newAdmin.getFirstName()),
//				"Admin's First Name isn't shown correctly in profile page");
//		assertTrue(profilePage.getTextLastName().equals(newAdmin.getLastName()),
//				"Admin's Last Name isn't shown correctly in profile page");
//		assertTrue(profilePage.getTextRole().equals(newAdmin.getRole()),
//				"Admin's Role isn't shown correctly in profile page");	
//		assertTrue(profilePage.getTextUserGroups().equals(newAdmin.getGroupOne()),
//				"Admin's group isn't shown correctly in profile page");

	}

	/*-
	 * Test creates new user, checks if all properties are saved correctly in user list, 
	 * "Edit user" page and "Profile" page, checks logins to the system with new user's credentials: 
	 * 1. Login to the system as an admin. 
	 * 2. Click "Admin" menu, "New user" option. 
	 * 3. Fill fields in New User form: "First Name", "Last Name", "Username", "Password", search for a group name, click button
	 *    "Add", click button "Create". 
	 * 4. Click "Admin" menu, "Users" option. 
	 * 5. Search for created Username. 
	 * 6. Check if properties ("First Name", "Last Name", "Username", "Role") on a list are displayed correctly . 
	 * 7. Click "Edit / View" button. 
	 * 8. Check if all properties ("First Name", "Last Name", "Username", "Role", groups) are displayed correctly. 
	 * 9. Click button "Cancel".
	 * 10. Click button "Logout". 
	 * 11. Login to the system using new user's username and password, click button "Login".
	 * 12. Check if: 
	 *    - buttons "Create Document", "Sign Document", "My Documents" and same icons are visible; 
	 *    - dropdown "Admin" with it's options ("New User", "New Group", "New Document Type", "Users", "Groups", "Document Types") is clickable; 
	 *    - buttons "Profile" and "Logout" are clickable; 
	 *  13. Check if all user data on Profile Page is displayed correctly;
	 */

	@Test(groups = { "newUserCreationTests" }, priority = 1, enabled = false)
	public void newUserCreationTest() throws InterruptedException {
		mainPage.clickAdminButton();
		mainPage.clickAdminNewUserButton();
		adminNewUserPage.sendKeysFirstName(newUser.getFirstName());
		adminNewUserPage.sendKeysLastName(newUser.getLastName());		
		adminNewUserPage.sendKeysUserName(newUser.getUserName());		
		adminNewUserPage.sendKeysPassword(newUser.getPassWord());		
		adminNewUserPage.checkShowPassword();		
		adminNewUserPage.sendKeysSearchGroupToAdd(newAdmin.getGroupOne());
		adminNewUserPage.clickAddSpecificGroupButton(newAdmin.getGroupOne());
		adminNewUserPage.clickCreateButton();
		driver.navigate().refresh();
		mainPage.clickAdminButton();
		mainPage.clickAdminUsersButton();		
		userListPage.sendKeysSearchForUser(newAdmin.getUserName());			
		assertTrue(userListPage.getFirstNameFromUserListByUsername(newAdmin.getUserName()).equals(newAdmin.getFirstName()),
				"Admin's First Name isn't displayed correctly in user list");
		assertTrue(userListPage.getLastNameFromUserListByUsername(newAdmin.getUserName()).equals(newAdmin.getLastName()),
				"Admin's Last Name isn't displayed correctly in user list");
		assertTrue(userListPage.getRoleFromUserListByUsername(newAdmin.getUserName()).equals(newAdmin.getRole()),
				"Admin's role isn't displayed correctly in user list");
		userListPage.clickViewEditSpecificUserButton(newAdmin.getUserName());		
		Thread.sleep(1000); ///////!!!!!
		assertTrue(editUserPage.getFirstName().equals(newAdmin.getFirstName()),
				"Admin First Name isn't displayed correctly");
		assertTrue(editUserPage.getLastName().equals(newAdmin.getLastName()), "Admin Last Name isn't displayed correctly");
		assertTrue(editUserPage.isRadioButtonAdminSelected(), "Admin's role isn't displayed correctly");
		editUserPage.sendKeysSearchUsersGroups(newAdmin.getGroupOne());
		assertTrue(
				driver.findElement(By.xpath("//td[contains(text(), '" + newAdmin.getGroupOne() + "')]")).isDisplayed(),
				"User was not added to the group correctly");		
		editUserPage.clickCancelButton();
		mainPage.clickLogoutButton();	
		loginPage.sendKeysUserName(newAdmin.getUserName());
		loginPage.sendKeysPassword(newAdmin.getPassWord());
		loginPage.clickButtonLogin();
		mainPage.clickProfileButton();
		assertTrue(profilePage.getTextUsername().equals(newUser.getUserName()),
				"Users's Username isn't displayed correctly in profile page");
		assertTrue(profilePage.getTextFirstName().equals(newUser.getFirstName()),
				"User's First Name isn't displayed correctly in profile page");
		assertTrue(profilePage.getTextLastName().equals(newUser.getLastName()),
				"User's Last Name isn't displayed correctly in profile page");
		assertTrue(profilePage.getTextRole().contentEquals(newUser.getRole()),
				"User's Role isn't displayed correctly in profile page");
		assertTrue(profilePage.getTextUserGroups().equals(newAdmin.getGroupOne()),
				"Admin's group isn't shown correctly in profile page");

	}

}
