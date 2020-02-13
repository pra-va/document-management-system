package page;

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
	

	/* SEND KEYS */
	
	public void sendKeysSearchForUser(String userInformation) {
		searchField.sendKeys(userInformation);
	}
	
	
}
