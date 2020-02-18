package page;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class LoginPage extends AbstractPage {

	public LoginPage(WebDriver driver) {
		super(driver);
	}
	
	/* FIELDS */
	
	@FindBy(id = "username")
	private WebElement userNameField;
	
	@FindBy(id = "password")
	private WebElement passwordField;
	
	/* BUTTONS */
	
	@FindBy(xpath = "//button[@type='submit']")
	private WebElement buttonLogin;
	
	/* CLICK BUTTONS */
	
	public void clickButtonLogin() {
		this.buttonLogin.click();
	}
	
	/* SEND KEYS */
	
	public void sendKeysUserName(String str) {
		this.userNameField.sendKeys(str);
	}
	
	public void sendKeysPassword(String str) {
		this.passwordField.sendKeys(str);
	}
}
