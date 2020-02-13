package test;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.thoughtworks.xstream.XStream;

import page.LoginPage;
import page.MainPage;
import utilities.User;

public class loginTests extends AbstractTest {
	LoginPage loginPage;
	MainPage mainPage;
	WebDriverWait wait;
	XStream xstream;
	User user;
	User admin;
	User wrongInfo;

	@BeforeClass
	public void preconditions() {
		loginPage = new LoginPage(driver);
		mainPage = new MainPage(driver);
		wait = new WebDriverWait(driver, 2);
		xstream = new XStream();
		XStream.setupDefaultSecurity(xstream);
		xstream.allowTypesByWildcard(new String[] { "utilities.User" });
		try {
			user = (User) xstream.fromXML(FileUtils.readFileToString(new File("src/test/resources/user.xml")));
			admin = (User) xstream.fromXML(FileUtils.readFileToString(new File("src/test/resources/admin.xml")));
			wrongInfo = (User) xstream
					.fromXML(FileUtils.readFileToString(new File("src/test/resources/wrongInfo.xml")));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Test(groups = { "loginTests" })
	public void adminLoginTest() throws InterruptedException {
		loginPage.sendKeysUserName(admin.getUserName());
		loginPage.sendKeysPassword(admin.getPassWord());
		loginPage.clickButtonLogin();
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[contains(text(),'Admin')]")));
		assertTrue(driver.findElement(By.xpath("//a[contains(text(),'Admin')]")).isDisplayed(),
				"admin dropdown isn't displayed");
		mainPage.clickLogoutButton();
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
