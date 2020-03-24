package page;

import java.io.IOException;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import utilities.API;

public class EditDocTypePage extends AbstractPage {

	public EditDocTypePage(WebDriver driver) {
		super(driver);
	}

	/* FIELDS */

	/* BUTTONS */

	@FindBy(xpath = "//button[contains(text(),'Update')]")
	private WebElement buttonUpdate;

	@FindBy(xpath = "//button[contains(text(),'Cancel')]")
	private WebElement buttonCancel;

	/* SEND KEYS */

	/* CLICK BUTTONS */

	/* WAITS */

	public void waitForCancelButton() {
		new WebDriverWait(driver, 2).until(ExpectedConditions.elementToBeClickable(buttonCancel));
	}

	/* OTHER METHODS */

	public void createDocType(String groupsThatApprove, String groupsThatCreate, String docTypeName)
			throws IOException {
		//API.createDocType(groupsThatApprove, groupsThatCreate, docTypeName);
	}

	public void createGroup(String description, String docTypesToCreate, String docTypesToSign, String groupName,
			String userList) throws IOException {
		//API.createGroup(description, docTypesToCreate, docTypesToSign, groupName, userList);
	}

}
