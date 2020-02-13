package page;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class AdminNewGroupPage extends AbstractPage {

	public AdminNewGroupPage(WebDriver driver) {
		super(driver);
	}
	
	/* FIELDS */
	
	@FindBy(id = "inputGroupName")
	private WebElement groupNameField;
	
	@FindBy(id = "inputGroupDescription")
	private WebElement groupDescriptionField;
	
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
	
	public void sendKeysGroupName(String name) {
		this.groupNameField.sendKeys(name);
	}
	
	public void sendKeysGroupDescription(String description) {
		this.groupDescriptionField.sendKeys(description);
	}
}
