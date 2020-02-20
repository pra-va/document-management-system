package page;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class ProfilePage extends AbstractPage {

	public ProfilePage(WebDriver driver) {
		super(driver);		
	}
	
	/* HEADERS */

	@FindBy(tagName = "h4")
	private WebElement headerUsername;
	
	@FindBy(xpath = "//h5[contains(text(),'First Name:')]")
	private WebElement headerFirstName;
		
	@FindBy(xpath = "//h5[contains(text(),'Last Name:')]")
	private WebElement headerLastName;
	
	@FindBy(xpath = "//h5[contains(text(),'Role:')]")
	private WebElement headerRole;
	
	@FindBy(xpath = "//h5[contains(text(),'Users groups:')]")
	private WebElement headerUserGroups;
	
	/* GET TEXT METHODS*/
	
	public String getTextUsername() {
		return this.headerUsername.getText();
	}
	
	public String getTextFirstName() {
		return this.headerFirstName.getText().substring(12);
	}

	public String getTextLastName() {
		return this.headerLastName.getText().substring(11);
	}
	
	public String getTextRole() {
		return this.headerRole.getText().substring(6);
	}
	
	public String getTextUserGroups() {
		return this.headerUserGroups.getText().substring(14);
	}

}
