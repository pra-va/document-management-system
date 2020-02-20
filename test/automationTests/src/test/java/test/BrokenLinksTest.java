package test;

import static org.testng.Assert.assertTrue;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.AfterGroups;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeGroups;
import org.testng.annotations.Test;

import page.AdminNewDocTypePage;
import page.AdminNewGroupPage;
import page.AdminNewUserPage;
import page.LoginPage;
import page.MainPage;

public class BrokenLinksTest extends AbstractTest {
	LoginPage loginPage;
	MainPage mainPage;
	AdminNewUserPage userPage;
	AdminNewGroupPage groupPage;
	AdminNewDocTypePage docPage;
	List<WebElement> links;
	Iterator<WebElement> it;
	HttpURLConnection huc;
	String url;
	int respCode;

	@BeforeClass
	public void preconitions() {
		loginPage = new LoginPage(driver);
		mainPage = new MainPage(driver);
		userPage = new AdminNewUserPage(driver);
		groupPage = new AdminNewGroupPage(driver);
		docPage = new AdminNewDocTypePage(driver);
		links = new ArrayList<WebElement>();
		respCode = 200;
	}

	@BeforeGroups("brokenLinks")
	public void login() {
		loginPage.sendKeysUserName(admin.getUserName());
		loginPage.sendKeysPassword(admin.getPassWord());
		loginPage.clickButtonLogin();
	}

	@AfterGroups("brokenLinks")
	public void logout() {
		mainPage.waitForLogoutButton();
		mainPage.clickLogoutButton();
	}

	@Test(groups = { "brokenLinks" }, priority = 1, enabled = true)
	public void brokenLinksTest() {
		mainPage.clickAdminButton();
		mainPage.clickAdminUsersButton();
		mainPage.waitForAdminButton();
		links = driver.findElements(By.tagName("a"));
		it = links.iterator();

		while (it.hasNext()) {
			url = it.next().getAttribute("href");
			if (url == null || url.isEmpty()) {
				System.out.println("URL is either not configured for anchor tag or it is empty");
				continue;
			}

			try {
				huc = (HttpURLConnection) (new URL(url).openConnection());
				huc.setRequestMethod("HEAD");
				huc.connect();
				respCode = huc.getResponseCode();
				assertTrue(respCode == 200, "one or more links are broken");

				if (respCode >= 400) {
					System.out.println(url + " is a broken link");
					System.out.println(respCode);
				}
			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	@Test(groups = { "brokenLinks" }, priority = 0, enabled = true)
	public void brokeButtonsTest() {
		mainPage.clickAdminButton();
		mainPage.clickAdminNewUserButton();
		userPage.waitForCancelButton();
		assertTrue(driver.findElement(By.xpath("//button[contains(text(),'Create')]")).isDisplayed(),
				"New user button is broken");
		userPage.clickCancelButton();
		mainPage.clickAdminButton();
		mainPage.clickAdminNewGroupButton();
		groupPage.waitForcancelButton();
		assertTrue(driver.findElement(By.xpath("//button[contains(text(),'Create')]")).isDisplayed(),
				"New group button is broken");
		groupPage.clickCancelButton();
		mainPage.clickAdminButton();
		mainPage.clickAdminNewDocTypeButton();
		docPage.waitForCreateButton();
		assertTrue(driver.findElement(By.xpath("//button[contains(text(),'Create')]")).isDisplayed(),
				"New doc type button is broken");
		docPage.clickCancelButton();
	}
}
