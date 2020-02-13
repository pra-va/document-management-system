package page;

import java.util.List;

import org.openqa.selenium.By;
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

	public void clickAddSpecificUserButton(String user) {
		driver.findElement(By.xpath("//td[contains(text()," + user + ")]/..//td[6]//button")).click();
	}

	public void clickRemoveSpecificUserButton(String user) {
		driver.findElement(By.xpath("//td[contains(text()," + user + ")]/..//td[6]//button[contains(text(),'Remove')]"))
				.click();
	}

	/* SEND KEYS */

	public void sendKeysGroupName(String name) {
		this.groupNameField.sendKeys(name);
	}

	public void sendKeysGroupDescription(String description) {
		this.groupDescriptionField.sendKeys(description);
	}

	public void sendKeysSearchUserToAdd(String groupName) {
		searchFields.get(0).sendKeys(groupName);
	}

	public void sendKeysSearchUserToRemove(String groupName) {
		searchFields.get(1).sendKeys(groupName);
	}
}
