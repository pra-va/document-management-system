package page;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class AdminNewDocTypePage extends AbstractPage {

	public AdminNewDocTypePage(WebDriver driver) {
		super(driver);
	}

	/* FIELDS */

	@FindBy(id = "groupNameInput")
	private WebElement groupNameField;

	@FindBy(xpath = "//*[@aria-label='Search']")
	private List<WebElement> searchFields;

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

	/* SEND KEYS */

	public void sendKeysSearchAssignNewDocType(String groupName) {
		searchFields.get(0).sendKeys(groupName);
	}

	public void sendKeysSearchSetRigths(String groupName) {
		searchFields.get(1).sendKeys(groupName);
	}
}
