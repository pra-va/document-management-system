package test;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeGroups;
import org.testng.annotations.Test;

import com.mashape.unirest.http.exceptions.UnirestException;
import com.thoughtworks.xstream.XStream;

import page.AdminNewGroupPage;
import page.AdminNewUserPage;
import page.EditUserPage;
import page.LoginPage;
import page.MainPage;
import page.ProfilePage;
import page.UserListPage;

public class EditUserTests extends AbstractTest {

	LoginPage loginPage;
	MainPage mainPage;
	UserListPage userListPage;
	EditUserPage editUserPage;
//	User newUser;
//	User newAdmin;
//	User admin;

	@BeforeClass
	public void preconditions() throws IOException {
		loginPage = new LoginPage(driver);
		mainPage = new MainPage(driver);
		userListPage = new UserListPage(driver);
		editUserPage = new EditUserPage(driver);
//		xstream = new XStream();
//		XStream.setupDefaultSecurity(xstream);
//		xstream.allowTypesByWildcard(new String[] { "utilities.User" });
//		try {
//			newAdmin = (User) xstream.fromXML(FileUtils.readFileToString(new File("src/test/resources/newAdmin.xml")));
//			newUser = (User) xstream.fromXML(FileUtils.readFileToString(new File("src/test/resources/newUser.xml")));
//			admin = (User) xstream.fromXML(FileUtils.readFileToString(new File("src/test/resources/admin.xml")));
//			wrongInfo = (User) xstream
//					.fromXML(FileUtils.readFileToString(new File("src/test/resources/wrongInfo.xml")));
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
	}

	@BeforeGroups("editUser")
	public void login() {
//		loginPage.sendKeysUserName(admin.getUserName());
//		loginPage.sendKeysPassword(admin.getPassWord());
		loginPage.clickButtonLogin();
	}

	
	/*-
	 * Test edits user properties, checks if all properties are saved correctly in user list, 
	 * "Edit user" page and "Profile" page, checks logins to the system with new credentials: 
	 * 1. Login to the system as an admin. 
	 * 2. Click "Admin" menu, "Users" option. 
	 * 3. Search for specific user and click "Edit / view" page.
	 * 4. Fill fields in Edit user form: "First Name", "Last Name", check box "Update password",  fill field "Password", click "No" on "Admin" selection, search for a group name 
	 * in section "Add user to groups", click button "Add", search for a group name in section "User's groups" click button "Remove", click button "Submit". 
	 * 5. Click "Admin" menu, "Users" option. 
	 * 6. Search for edited Username. 
	 * 7. Check if new properties ("First Name", "Last Name", "Username", "Role") on a list are displayed correctly. 
	 * 8. Click "Edit / View" button. 
	 * 9. Check if all properties ("First Name", "Last Name", "Username", "Role", groups) are displayed correctly. 
	 * 10. Click button "Cancel".
	 * 11. Click button "Logout". 
	 * 12. Login to the system using new user's username and password, click button "Login".
	 * 13. Check if: 
	 *    - buttons "Create Document", "Sign Document", "My Documents" and same icons are visible; 
	 *    - dropdown "Admin" with it's options ("New User", "New Group", "New Document Type", "Users", "Groups", "Document Types") is clickable; 
	 *    - buttons "Profile" and "Logout" are clickable; 
	 *  14. Check if all new user data on Profile Page is displayed correctly;
	 */
	@Test(groups = { "editUser" }, priority = 1, enabled = true)
	public void editUserTest(){
		
		mainPage.clickAdminButton();
		mainPage.clickAdminUsersButton();
		userListPage.clickViewEditSpecificUserButton("user3");
	
		
		
	}
}