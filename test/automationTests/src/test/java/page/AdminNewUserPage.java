package page;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
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
	private WebElement searchField;
	
	@FindBy(xpath = "//input[@id='inputFirstName']/../small[contains(text(),"
			+ "'Field can not be empty and longer than 20 characters long.')]/span")	
	private  WebElement firstNameLengthValidationLabel;
	
	@FindBy(xpath = "//input[@id='inputLastName']/../small[contains(text(),"
			+ "'Field can not be empty and longer than 20 characters long.')]/span")	
	private  WebElement lastNameLengthValidationLabel;
		
	@FindBy(xpath = "//input[@id='inputUsername']/../small[contains(text(),"
			+ "'Username must be between 4 and 20 characters long.')]/span")	
	private  WebElement userNameLengthValidationLabel;
	
	@FindBy(xpath = "//input[@id='inputUsername']/../small[contains(text(),'Username must be unique.')]/span")	
	private  WebElement userNameUniquenessValidationLabel;
	
	@FindBy(xpath = "//input[@id='inputUsername']/../small[contains(text(),'Field must contain 1 word.')]/span")	
	private  WebElement userNameNoSpacesValidationLabel;
	
	@FindBy(xpath = "//input[@id='inputUsername']/../small[contains(text(),'No special characters allowed.')]/span")	
	private  WebElement userNameNoSpecCharValidationLabel;
	
	@FindBy(xpath = "//input[@id='inputPassword']/../small[contains(text(),"
			+ "'Password must be between 8 and 20 characters long.')]/span")	
	private  WebElement passwordLengthValidationLabel;
	
	@FindBy(xpath = "//input[@id='inputPassword']/../small[contains(text(),'Field must contain 1 word.')]/span")	
	private  WebElement passwordNoSpacesValidationLabel;
	
	/* BUTTONS */

	@FindBy(xpath = "//button[contains(text(),'Cancel')]")
	private WebElement buttonCancel;

	@FindBy(xpath = "//button[contains(text(),'Create')]")
	private WebElement buttonCreate;

	@FindBy(xpath = "//button[contains(text(),'Add')]")
	private WebElement buttonAddUserToGroup;

	@FindBy(xpath = "button[text()='Remove']")
	private WebElement buttonRemoveUserFromGroup;

	/* CLICK BUTTONS */

	public void clickCancelButton() {
		this.buttonCancel.click();
	}

	public void clickCreateButton() {
		this.buttonCreate.click();
		new WebDriverWait(driver, 1).until(ExpectedConditions.visibilityOf(firstNameField));
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
	
	public void clickAddRemoveSpecificGroupButton(String groupName) {
		new WebDriverWait(driver, 4).ignoring(StaleElementReferenceException.class).
		until(ExpectedConditions.elementToBeClickable(By.xpath("//td[contains(text(),'" + groupName + "')]")));	
		driver.findElement(By.xpath("//td[contains(text(),'" + groupName + "')]")).click();			
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

	public void sendKeysSearchGroup(String groupName) {
		this.searchField.sendKeys(groupName);
	}
	
	public String returnAttribute() {
		return searchField.getAttribute("aria-label");
	}

	/* IS CLICKABLE METHOD */

	public boolean isCreateButtonDisplayed() {
		return buttonCreate.isDisplayed();
	}

	/*WAITS*/

	public void waitForGroupSelection() {
		new WebDriverWait(driver, 4).until(ExpectedConditions.elementToBeSelected(By.xpath("//td[@class='selection-cell']/input")));
	}
	
	public void waitForCancelButton() {
		new WebDriverWait(driver, 4).until(ExpectedConditions.visibilityOf(this.buttonCancel));
	}
	
	/* OTHER METHODS */
	
	public String firstNameLengthValidationLabelAttribute(){
		return firstNameLengthValidationLabel.getAttribute("aria-label");
	}
	
	public String lastNameLengthValidationLabelAttribute() {
		return lastNameLengthValidationLabel.getAttribute("aria-label");
	}
	
	public String userNameLengthValidationLabelAttribute() {
		return userNameLengthValidationLabel.getAttribute("aria-label");
	}

	public String userNameUniquenessValidationLabelAttribute() {
		return userNameUniquenessValidationLabel.getAttribute("aria-label");
	}

	public String userNameNoSpacesValidationLabelAttribute() {
		return userNameNoSpacesValidationLabel.getAttribute("aria-label");
	}
	
	public String userNameNoSpecCharValidationLabelAttribute() {
		return userNameNoSpecCharValidationLabel.getAttribute("aria-label");
	}
	
	public String passwordLengthValidationLabelAttribute() {
		return passwordLengthValidationLabel.getAttribute("aria-label");
	}
	
	public String passwordNoSpacesValidationLabelAttribute() {
		return passwordNoSpacesValidationLabel.getAttribute("aria-label");
	}
}