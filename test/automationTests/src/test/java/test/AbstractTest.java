package test;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import com.thoughtworks.xstream.XStream;

import utilities.User;

public abstract class AbstractTest {

	protected static WebDriver driver;
	protected static XStream xstream;
	protected static User user;
	protected static User admin;
	protected static User wrongInfo;

	@BeforeSuite
	public void beforeSuite() {
		System.setProperty("webdriver.chrome.driver", "src/test/resources/chromedriver.exe");
		driver = new ChromeDriver();
		driver.get("http://akademijait.vtmc.lt:8180/dvs/#/");
		driver.manage().window().maximize();
	}
	
	@AfterSuite
	public void afterSuite() {
		driver.quit();
	}
}
