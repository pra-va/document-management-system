package page;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

public class AdminNewDocTypePage extends AbstractPage {
	MainPage mainPage = new MainPage(driver);

	public AdminNewDocTypePage(WebDriver driver) {
		super(driver);
	}

	/* FIELDS */

	@FindBy(id = "groupNameInput")
	private WebElement docTypeNameField;

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

	public void clickAddSpecificGroupButton(String groupName) {
		driver.findElement(By.xpath(
				"//div[@id='newDocTypeAssignToGroups']//td[contains(text(),'" + groupName + "')]/..//td[3]//button"))
				.click();
	}

	public void clickCreateDocRigthsCheckBox(String sameGroupName) {
		new WebDriverWait(driver, 4).until(ExpectedConditions
				.elementToBeClickable(By.xpath("//input[@id='createRightsFor:" + sameGroupName + "']")));
		driver.findElement(By.xpath("//input[@id='createRightsFor:" + sameGroupName + "']")).click();
	}

	public void clickSignDocRigthsCheckBox(String sameGroupName) {
		new WebDriverWait(driver, 4).until(ExpectedConditions
				.elementToBeClickable(By.xpath("//input[@id='signRightsFor:" + sameGroupName + "']")));
		driver.findElement(By.xpath("//input[@id='signRightsFor:" + sameGroupName + "']")).click();
	}

	/* SEND KEYS */

	public void sendKeysDocTypeName(String name) {
		this.docTypeNameField.sendKeys(name);
	}

	public void sendKeysSearchSetRigths(String groupName) {
		searchFields.get(1).sendKeys(groupName);
	}

	/* OTHER METHODS */

	public void waitForCreateButton() {
		new WebDriverWait(driver, 4).until(ExpectedConditions.elementToBeClickable(this.buttonCreate));
	}

	public void createDocType(String docTypeName, String groupName) {
		mainPage.clickAdminButton();
		mainPage.clickAdminNewDocTypeButton();
		this.sendKeysDocTypeName(docTypeName);
		this.clickAddSpecificGroupButton(groupName);
		this.clickCreateDocRigthsCheckBox(groupName);
		this.clickSignDocRigthsCheckBox(groupName);
		this.clickCreateButton();
	}

	public void deleteDocType(String docTypeName) {
		try {
			Unirest.delete("http://akademijait.vtmc.lt:8180/dvs/api/doct/delete/{name}").routeParam("name", docTypeName)
					.asString();
		} catch (UnirestException e) {
			e.printStackTrace();
		}
	}
}
