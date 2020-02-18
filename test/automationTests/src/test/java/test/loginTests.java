package test;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import org.openqa.selenium.By;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import page.LoginPage;
import page.MainPage;

public class loginTests extends AbstractTest {
	LoginPage loginPage;
	MainPage mainPage;

	@BeforeClass
	public void preconditions() {
		loginPage = new LoginPage(driver);
		mainPage = new MainPage(driver);
	}

	@Test(groups = { "loginTests" }, priority = 1, enabled = true)
	public void adminLoginTest() {
		loginPage.clearLoginFields();
		loginPage.sendKeysUserName(admin.getUserName());
		loginPage.sendKeysPassword(admin.getPassWord());
		loginPage.clickButtonLogin();
		mainPage.waitForAdminButton();
		assertTrue(driver.findElement(By.xpath("//a[contains(text(),'Admin')]")).isDisplayed(),
				"admin dropdown isn't displayed");
		mainPage.clickLogoutButton();
		driver.navigate().back();
		assertTrue(driver.findElement(By.xpath("//button[@type='submit']")).isDisplayed(),
				"You should no be able to navigate back after logging out");
	}

	@Test(groups = { "loginTests" }, priority = 2, enabled = true)
	public void userLoginTest() {
		loginPage.clearLoginFields();
		loginPage.sendKeysUserName(user.getUserName());
		loginPage.sendKeysPassword(user.getPassWord());
		loginPage.clickButtonLogin();
		mainPage.waitForLogoutButton();
		assertFalse(driver.findElements(By.xpath("//a[contains(text(),'Admin')]")).size() == 1,
				"admin dropdown should not be displayed");
		mainPage.clickLogoutButton();
		driver.navigate().back();
		assertTrue(driver.findElement(By.xpath("//button[@type='submit']")).isDisplayed(),
				"You should not be able to navigate back after logging out");
	}

	@Test(groups = { "loginTests" }, priority = 0, enabled = true)
	public void emptyLoginFieldsTest() {
		loginPage.waitForLoginButton();
		loginPage.clickButtonLogin();
		loginPage.waitForLoginButton();
		assertTrue(
				driver.findElement(By.xpath("//*[contains(text(), 'Incorrect Username or Password!')]")).isDisplayed(),
				"Incorrect Username or Password! text is not displayed");
	}

	@Test(groups = { "loginTests" }, priority = 0, enabled = true)
	public void wrongLoginTest() {
		loginPage.waitForLoginButton();
		loginPage.sendKeysUserName(wrongInfo.getUserName());
		loginPage.sendKeysPassword(wrongInfo.getPassWord());
		loginPage.clickButtonLogin();
		loginPage.waitForLoginButton();
		assertTrue(
				driver.findElement(By.xpath("//*[contains(text(), 'Incorrect Username or Password!')]")).isDisplayed(),
				"Incorrect Username or Password! text is not displayed");
	}
}
