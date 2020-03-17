package page;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

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
	
	/* BUTTONS */
	
	@FindBy(xpath = "//button[contains(text(),'Close')]")
	private WebElement buttonClose;
	
	/* CLICK BUTTONS */
	
	public void clickButtonClose() {
		buttonClose.click();
	}
		
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
	
	/* WAITS */
	
	public void waitForVisibility(WebElement element) {
		new WebDriverWait(driver, 2).until(ExpectedConditions.visibilityOf(element));
	}

	public void waitForHeaderUsernameVisibility() {
		waitForVisibility(this.headerUsername);
	}	
}
