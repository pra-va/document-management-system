package page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

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

	/* OTHER METHODS */

	public void waitForLoginButton() {
		new WebDriverWait(driver, 4).until(ExpectedConditions.elementToBeClickable(this.buttonLogin));
	}

	public void waitForWrongLoginText() {
		new WebDriverWait(driver, 4).until(ExpectedConditions.visibilityOf(
				driver.findElement(By.xpath("//*[contains(text(), 'Incorrect Username or Password!')]"))));
	}

	public void clearLoginFields() {
		this.userNameField.clear();
		this.passwordField.clear();
	}
}
