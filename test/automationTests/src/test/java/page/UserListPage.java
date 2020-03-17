package page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class UserListPage extends MainPage {

	public UserListPage(WebDriver driver) {
		super(driver);
	}

	/* FIELDS */

	@FindBy(xpath = "//*[@aria-label='Search']")
	private WebElement searchField;

	/* CLICK BUTTONS */

	public void clickEditSpecificUserButton(String user) {
		driver.findElement(By.xpath("//td[contains(text(),'" + user + "')]/..//td[5]//button")).click();
	}

	/* SEND KEYS */

	public void sendKeysSearchForUser(String userInformation) {
		searchField.sendKeys(userInformation);
	}

	/* CLICK BUTTONS */

	public void clickViewEditSpecificUserButton(String userInformation) {		
		driver.findElement(By.xpath("//td[contains(text()," + userInformation + ")]/..//td[5]//button")).click();
	}

	/* GET TEXT METHODS */
	
	public String getFirstNameFromUserListByUsername(String username) {

		return driver.findElement(By.xpath("//td[3][text()='" + username + "']/..//td[1]")).getText();
	}
	
	public String getLastNameFromUserListByUsername(String username) {

		return driver.findElement(By.xpath("//td[3][text()='" + username + "']/..//td[2]")).getText();
	}
	
	public String getRoleFromUserListByUsername(String username) {

		return driver.findElement(By.xpath("//td[3][text()='" + username + "']/..//td[4]")).getText();
	}
		
}

