package page;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class EditUserPage extends AbstractPage {

	public EditUserPage(WebDriver driver) {
		super(driver);
	}

	/* BUTTONS */

	@FindBy(xpath = "button[text()='Add']")
	private WebElement buttonAddUserToGroup;

	@FindBy(xpath = "button[text()='Remove']")
	private WebElement buttonRemoveUserFromGroup;
	
	@FindBy(xpath = "//button[contains(text(),'Update')]")
	private WebElement buttonUpdate;
	
	@FindBy(xpath = "//button[contains(text(),'Cancel')]")
	private WebElement buttonCancel;

	/* FIELDS */

	@FindBy(id = "inputFirstName")
	private WebElement firstNameField;

	@FindBy(id = "inputLastName")
	private WebElement lastNameField;

	@FindBy(id = "checBoxUpdatePassword")
	private WebElement checkBoxUpdatePassword;

	@FindBy(id = "inputPassword")
	private WebElement passwordField;

	@FindBy(id = "radioUser")
	private WebElement radioUser;

	@FindBy(id = "radioAdmin")
	private WebElement radioAdmin;
	
	@FindBy(xpath = "//input[@placeholder='Find by Name']")
	private WebElement searchGroups;
	
	@FindBy(xpath = "//*[@aria-label='Search']")
	private WebElement searchField;
	
	@FindBy(xpath = "//div[@id='newUserGroups']//input[@placeholder='Search']")
	private WebElement searchField2;


	@FindBy(xpath = "//input[@placeholder='Search']")
	private WebElement searchGroupField;

	
	@FindBy(xpath = "//th[text()='Name']")
	private WebElement tableHeader;
		
	/* SEND KEYS */

	public void sendKeysUpdateFirstName(String firstName) {
		this.firstNameField.sendKeys(firstName);
	}

	public void sendKeysUpdateLastName(String lastName) {
		this.lastNameField.sendKeys(lastName);
	}
	
	public void sendKeysUpdatePassword(String password) {
		this.passwordField.sendKeys(password);
	}

	public void sendKeysSearchGroups(String groupName) {
		searchGroupField.sendKeys(groupName);
	}
	
	public void sendKeysSearchGroups2(String groupName) {
		searchField2.sendKeys(groupName);
	}
		
	/* CLICK BUTTONS */

	public void checkUpdatePassword() {
		this.checkBoxUpdatePassword.click();
	}
	
	public void clickAdminRadio() {
		this.radioAdmin.click();
	}
	
	public void clickUserRadio() {
		this.radioUser.click();
	}
		
	public void clickAddRemoveSpecificGroupButton(String groupName) {
		driver.findElement(By.xpath("//td[contains(text(),'" + groupName + "')]")).click();			
	}

	public void clickUpdateButton() {
		this.buttonUpdate.click();
	}
	
	public void clickCancelButton() {
		this.buttonCancel.click();
	}
	
	public void sortByGroupName() {		
		JavascriptExecutor jse2 = (JavascriptExecutor)driver;
		jse2.executeScript("arguments[0].scrollIntoView()", tableHeader); 
		tableHeader.click();
	}
	
	/*CLEAR FIELDS*/
	
	public void clearFirstNameFiel() {
		this.firstNameField.clear();
	}
	
	public void clearLastNameFiel() {
		this.lastNameField.clear();
	}
	
	public void clearSearchGroupsField() {
		this.searchGroupField.clear();
	}

	/* GET TEXT*/

	public String getFirstName() {
		return firstNameField.getAttribute("value");
	}

	public String getLastName() {
		return lastNameField.getAttribute("value");
	}

	/* IS SELECTED */

	public boolean isRadioButtonAdminSelected() {
		return radioAdmin.isSelected();
	}

	public boolean isRadioButtonUserSelected() {
		return radioUser.isSelected();
	}
	
	public boolean isUserAddedToGroup(String groupName) {
		return driver.findElement(By.xpath("//td[contains(text(), '" + groupName + "')]/..//input")).isSelected();
	}
	
	
	/* WAITS */

	public void waitForVisibility(WebElement element) {
		new WebDriverWait(driver, 2).until(ExpectedConditions.visibilityOf(element));
	}
	
	public void waitForEditUserPage() {
		new WebDriverWait(driver, 2).until(ExpectedConditions.visibilityOf(firstNameField));
	}
}

