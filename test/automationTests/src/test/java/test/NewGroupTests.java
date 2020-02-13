package test;

import static org.testng.Assert.assertTrue;

import java.io.IOException;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeGroups;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import page.AdminNewGroupPage;
import page.LoginPage;
import page.MainPage;
import utilities.TestData;

public class NewGroupTests extends AbstractTest {
	LoginPage loginPage;
	MainPage mainPage;
	AdminNewGroupPage groupPage;
	List<String> description;
	WebDriverWait wait;

	@BeforeClass
	public void preconitions() throws IOException {
		loginPage = new LoginPage(driver);
		mainPage = new MainPage(driver);
		groupPage = new AdminNewGroupPage(driver);
		wait = new WebDriverWait(driver, 2);
		description = TestData.getTestData("src/test/resources/groupDescription.txt");
	}

	@BeforeGroups("createGroup")
	public void login() {
		loginPage.sendKeysUserName(admin.getUserName());
		loginPage.sendKeysPassword(admin.getPassWord());
		loginPage.clickButtonLogin();
	}

	@BeforeMethod
	public void navigateToNewGroupPage() {
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[contains(text(),'Admin')]")));
		mainPage.clickAdminButton();
		mainPage.clickAdminNewGroupButton();
	}

	@AfterMethod
	public void afterTest() {
		mainPage.navigateToMainPage();
	}

	@Test(groups = { "createGroup" }, priority = 0, enabled = false)
	public void createGroupTest() {
		groupPage.sendKeysGroupName("Junior developer");
		groupPage.sendKeysGroupDescription(description.get(0));
		groupPage.clickAddSpecificUserButton("admin");
		// groupPage.clickCreateButton();
		groupPage.clickCancelButton();
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[contains(text(),'Admin')]")));
		mainPage.clickAdminButton();
		mainPage.clickAdminGroupsButton();
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[contains(text(),'Admin')]")));
		assertTrue(driver.findElement(By.xpath("//td[contains(text(), 'Junior developer')]")).isDisplayed(),
				"Created group name isn't displayed or group wasn't created");
	}

	@Test(groups = { "createGroup" }, priority = 1, enabled = false)
	public void groupWithoutNameTest() {
		groupPage.clickCreateButton();
		assertTrue(driver.findElement(By.xpath("//div[contains(text(), 'New Group')]")).isDisplayed(),
				"Group should not be created without a name");
		groupPage.clickCancelButton();
	}

	@Test(groups = { "createGroup" }, priority = 1, enabled = true)
	public void userInGroupTest() throws InterruptedException {
		groupPage.clickCancelButton();
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[contains(text(),'Admin')]")));
		mainPage.clickAdminButton();
		mainPage.clickAdminUsersButton();
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[contains(text(),'Admin')]")));
		driver.findElement(By.xpath("//td[contains(text(),'admin')]/..//td[6]//button")).click();
		Thread.sleep(1000);
		assertTrue(driver.findElement(By.xpath("//td[contains(text(), 'Junior developer')]")).isDisplayed(),
				"User was not added to the group correctly");
		Thread.sleep(3000);
	}
}
