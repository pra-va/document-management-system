package test;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import page.LoginPage;
import page.MainPage;

public class loginTests extends AbstractTest {
	LoginPage loginPage;
	MainPage mainPage;
	WebDriverWait wait;

	@BeforeClass
	public void preconditions() {
		loginPage = new LoginPage(driver);
		mainPage = new MainPage(driver);
		wait = new WebDriverWait(driver, 2);
	}

	@Test(groups = { "loginTests" })
	public void adminLoginTest() {
		loginPage.sendKeysUserName(admin.getUserName());
		loginPage.sendKeysPassword(admin.getPassWord());
		loginPage.clickButtonLogin();
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[contains(text(),'Admin')]")));
		assertTrue(driver.findElement(By.xpath("//a[contains(text(),'Admin')]")).isDisplayed(),
				"admin dropdown isn't displayed");
		mainPage.clickLogoutButton();
		driver.navigate().back();
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[@type='submit']")));
		assertTrue(driver.findElement(By.xpath("//button[@type='submit']")).isDisplayed(),
				"You should no be able to navigate back after logging out");
	}

	@Test(groups = { "loginTests" })
	public void userLoginTest() throws InterruptedException {
		loginPage.sendKeysUserName(user.getUserName());
		loginPage.sendKeysPassword(user.getPassWord());
		loginPage.clickButtonLogin();
		Thread.sleep(100);
		assertFalse(driver.findElements(By.xpath("//a[contains(text(),'Admin')]")).size() == 1,
				"admin dropdown should not be displayed");
		mainPage.clickLogoutButton();
		driver.navigate().back();
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[@type='submit']")));
		assertTrue(driver.findElement(By.xpath("//button[@type='submit']")).isDisplayed(),
				"You should not be able to navigate back after logging out");
	}

	@Test(groups = { "loginTests" })
	public void emptyLoginFieldsTest() {
		loginPage.clickButtonLogin();
		List<WebElement> list = driver
				.findElements(By.xpath("//*[contains(text(), 'Incorrect Username or Password!')]"));
		assertTrue(list.size() > 0, "Incorrect Username or Password! text is not displayed");
	}

	@Test(groups = { "loginTests" })
	public void wrongLoginTest() {
		loginPage.sendKeysUserName(wrongInfo.getUserName());
		loginPage.sendKeysPassword(wrongInfo.getPassWord());
		loginPage.clickButtonLogin();
		List<WebElement> list = driver
				.findElements(By.xpath("//*[contains(text(), 'Incorrect Username or Password!')]"));
		assertTrue(list.size() > 0, "Incorrect Username or Password! text is not displayed");
	}
}
