package page;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class AdminNewUserPage extends AbstractPage {

	public AdminNewUserPage(WebDriver driver) {
		super(driver);
	}

	/* FIELDS */

	@FindBy(id = "inputFirstName")
	private WebElement firstNameField;

	@FindBy(id = "inputLastName")
	private WebElement lastNameField;

	@FindBy(id = "inputUsername")
	private WebElement userNameField;

	@FindBy(id = "inputPassword")
	private WebElement passwordField;

	@FindBy(id = "checkBoxShowPassword")
	private WebElement showPasswordCheckBox;

	@FindBy(id = "radioAdmin")
	private WebElement radioAdmin;

	@FindBy(id = "radioUser")
	private WebElement radioUser;

	/* BUTTONS */

	@FindBy(xpath = "//button[contains(text(),'Cancel')]")
	private WebElement buttonCancel;

	@FindBy(xpath = "//button[contains(text(),'Create')]")
	private WebElement buttonCreate;

	/* CLICK BUTTONS */

	public void clickCancelButton() {
		this.buttonCancel.click();
	}

	public void clickCreateButton() {
		this.buttonCreate.click();
	}

	public void checkShowPassword() {
		this.showPasswordCheckBox.click();
	}

	public void clickAdminRadio() {
		this.radioAdmin.click();
	}

	public void clickUserRadio() {
		this.radioUser.click();
	}

	/* SEND KEYS */

	public void sendKeysFirstName(String firstName) {
		this.firstNameField.sendKeys(firstName);
	}

	public void sendKeysLastName(String lastName) {
		this.lastNameField.sendKeys(lastName);
	}

	public void sendKeysUserName(String userName) {
		this.userNameField.sendKeys(userName);
	}

	public void sendKeysPassword(String password) {
		this.passwordField.sendKeys(password);
	}

}
