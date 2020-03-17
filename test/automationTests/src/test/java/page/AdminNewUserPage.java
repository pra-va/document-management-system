package page;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

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

	@FindBy(xpath = "//*[@aria-label='Search']")
	private List<WebElement> searchFields;

	/* BUTTONS */

	@FindBy(xpath = "//button[contains(text(),'Cancel')]")
	private WebElement buttonCancel;

	@FindBy(xpath = "//button[contains(text(),'Create')]")
	private WebElement buttonCreate;

	@FindBy(xpath = "button[text()='Add']")
	private WebElement buttonAddUserToGroup;

	@FindBy(xpath = "button[text()='Remove']")
	private WebElement buttonRemoveUserFromGroup;

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

	public void clickButtonAddUserToGroup() {
		this.buttonAddUserToGroup.click();
	}

	public void clickButtonRemoveUserFromGroup() {
		this.buttonRemoveUserFromGroup.click();
	}

	public void clickUserRadio() {
		this.radioUser.click();
	}

	public void clickAddSpecificGroupButton(String groupName) {
		driver.findElement(By.xpath("//td[contains(text(),'" + groupName + "')]/..//td[3]//button")).click();
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

	public void sendKeysSearchGroupToAdd(String groupName) {
		searchFields.get(0).sendKeys(groupName);
	}

	public void sendKeysSearchGroupToRemove(String groupName) {
		searchFields.get(1).sendKeys(groupName);

	}

	/* IS CLICKABLE METHOD */

	public boolean isCreateButtonDisplayed() {
		return buttonCreate.isDisplayed();
	}

	/* OTHER METHODS */

	public void waitForCancelButton() {
		new WebDriverWait(driver, 4).until(ExpectedConditions.visibilityOf(this.buttonCancel));
	}

//	public void clickRemoveSpecificGroupButton(String groupName) {
//		driver.findElement(By.xpath("//td[contains(text()," + groupName + ")]/..//td[3]//button")).click();
//	}

}