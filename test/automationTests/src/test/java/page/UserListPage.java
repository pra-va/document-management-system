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

	/* BUTTONS */

	/* CLICK BUTTONS */

	public void clickEditSpecificUserButton(String user) {
		driver.findElement(By.xpath("//td[contains(text(),'" + user + "')]/..//td[6]//button")).click();
	}

	/* SEND KEYS */

	public void sendKeysSearchForUser(String userInformation) {
		searchField.sendKeys(userInformation);
	}

}
