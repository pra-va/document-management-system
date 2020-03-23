package page;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class EditGroupPage extends AbstractPage {

	public EditGroupPage(WebDriver driver) {
		super(driver);
	}

	/* FIELDS */

	@FindBy(id = "inputGroupName")
	private WebElement groupNameField;

	@FindBy(xpath = "//*[@placeholder='Find by Name']")
	private List<WebElement> searchFields;

	/* BUTTONS */

	@FindBy(xpath = "//button[contains(text(),'Update')]")
	private WebElement buttonUpdate;

	@FindBy(xpath = "//button[contains(text(),'Cancel')]")
	private WebElement buttonCancel;

	/* SEND KEYS */

	public void sendKeysGroupName(String groupName) {
		this.groupNameField.sendKeys(groupName);
	}

	public void sendKeysSearchGroupsUsers(String userName) {
		this.searchFields.get(1).sendKeys(userName);
	}

	public void sendKeysSearchDocTypes(String docTypeName) {
		this.searchFields.get(2).sendKeys(docTypeName);
	}

	/* CLEAR FIELDS */

	public void clearGroupNameField() {
		this.groupNameField.clear();
	}

	public void clearSearchGroupsUsers(String userName) {
		this.searchFields.get(0).clear();
	}

	public void clearSearchDocTypes(String docTypeName) {
		this.searchFields.get(1).clear();
	}

	/* CLICK BUTTONS */

	public void clickUpdateButton() {
		this.buttonUpdate.click();
	}

	public void clickCancelButton() {
		this.buttonCancel.click();
	}

	public void clickSpecificUserCheckBox(String userName) {
		driver.findElement(
				By.xpath("//div[@id='newUserGroups']//td[contains(text(), '" + userName + "')]/..//td[1]//input"))
				.click();
	}

	public void clickSpecificDocTypeCreateCheckBox(String docTypeName) {
		driver.findElement(
				By.xpath("//div[@id='newUserGroups']//td[contains(text(), '" + docTypeName + "')]/..//td[2]//input"))
				.click();
	}

	public void clickSpecificDocTypeSignCheckBox(String docTypeName) {
		driver.findElement(
				By.xpath("//div[@id='newUserGroups']//td[contains(text(), '" + docTypeName + "')]/..//td[3]//input"))
				.click();
	}

	/* WAITS */

	public void waitForCancelButton() {
		new WebDriverWait(driver, 2).until(ExpectedConditions.elementToBeClickable(buttonCancel));
	}

}
