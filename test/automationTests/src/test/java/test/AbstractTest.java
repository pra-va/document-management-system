package test;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

import com.thoughtworks.xstream.XStream;

import utilities.User;

public abstract class AbstractTest {

	protected static WebDriver driver;
	protected static XStream xstream;
	protected static User user;
	protected static User admin;
	protected static User wrongInfo;

	@BeforeClass
	public static void setUp() {
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
		System.setProperty("webdriver.chrome.driver", "src/test/resources/chromedriver.exe");
		driver = new ChromeDriver();
		driver.get("http://akademijait.vtmc.lt:8180/dvs/#/");
		driver.manage().window().maximize();
	}

	@AfterClass
	public static void closeBrowser() {
		driver.quit();
	}
}
