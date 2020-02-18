package test;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeGroups;
import org.testng.annotations.Test;

import page.LoginPage;
import page.MainPage;

public class BrokenLinksTest extends AbstractTest {
	LoginPage loginPage;
	MainPage mainPage;
	List<WebElement> links;
	Iterator<WebElement> it;
	HttpURLConnection huc;
	String url;
	int respCode;

	@BeforeClass
	public void preconitions() {
		loginPage = new LoginPage(driver);
		mainPage = new MainPage(driver);
		links = new ArrayList<WebElement>();
		respCode = 200;
	}

	@BeforeGroups("brokenLinks")
	public void login() {
		loginPage.sendKeysUserName(admin.getUserName());
		loginPage.sendKeysPassword(admin.getPassWord());
		loginPage.clickButtonLogin();
	}

	@Test(groups = { "brokenLinks" }, enabled = true)
	public void brokenLinksTest() {
		mainPage.waitForLogoutButton();
		mainPage.clickAdminButton();
		links = driver.findElements(By.tagName("a"));
		it = links.iterator();
		System.out.println(links.size());

		while (it.hasNext()) {

			url = it.next().getAttribute("href");

			System.out.println(url);

			if (url == null || url.isEmpty()) {
				System.out.println("URL is either not configured for anchor tag or it is empty");
				continue;
			}

			try {
				huc = (HttpURLConnection) (new URL(url).openConnection());

				huc.setRequestMethod("HEAD");

				huc.connect();

				respCode = huc.getResponseCode();
				System.out.println(respCode);

				if (respCode >= 400) {
					System.out.println(url + " is a broken link");
				} else {
					System.out.println(url + " is a valid link");
				}

			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}
}
