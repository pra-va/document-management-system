package page;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class EditUserPage extends AbstractPage {

	public EditUserPage(WebDriver driver) {
		super(driver);
	}

	/* BUTTONS */

	@FindBy(xpath = "//button[contains(text(),'Cancel')]")
	private WebElement buttonCancel;

	/* FIELDS */

	@FindBy(id = "inputFirstName")
	private WebElement firstNameField;

	@FindBy(id = "inputLastName")
	private WebElement lastNameField;

	@FindBy(xpath = "//div[@id='newUserAddedGroups']//input[@aria-label='Search']")
	private WebElement searchUsersGroupsField;

	@FindBy(id = "radioUser")
	private WebElement radioUser;

	@FindBy(id = "radioAdmin")
	private WebElement radioAdmin;

	/* SEND KEYS */

	public void sendKeysSearchUsersGroups(String groupName) {
		searchUsersGroupsField.sendKeys(groupName);
	}

	/* CLICK BUTTONS */

	public void clickCancelButton() {
		buttonCancel.click();
	}

	/* GET METHODS */

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

}
