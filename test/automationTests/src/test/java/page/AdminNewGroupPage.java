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

public class AdminNewGroupPage extends AbstractPage {
	MainPage mainPage = new MainPage(driver);

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

	public void clickAddSpecificDocTypeButton(String docType) {
		driver.findElement(By.xpath("//div[@id='docTypes']//td[contains(text(),'" + docType + "')]//..//td[5]//button"))
				.click();
	}

	public void clickCreateDocRigthsCheckBox(String sameAsDocType) {
		new WebDriverWait(driver, 4).until(ExpectedConditions
				.elementToBeClickable(By.xpath("//input[@id='createRightsFor:" + sameAsDocType + "']")));
		driver.findElement(By.xpath("//input[@id='createRightsFor:" + sameAsDocType + "']")).click();
	}

	public void clickSignDocRigthsCheckBox(String sameAsDocType) {
		new WebDriverWait(driver, 4).until(ExpectedConditions
				.elementToBeClickable(By.xpath("//input[@id='signRightsFor:" + sameAsDocType + "']")));
		driver.findElement(By.xpath("//input[@id='signRightsFor:" + sameAsDocType + "']")).click();
	}

	public void clickAddSpecificUserButton(String user) {
		driver.findElement(By.xpath("//td[contains(text()," + user + ")]/..//td[1]//input")).click();
	}

	public void clickRemoveSpecificUserButton(String user) {
		driver.findElement(By.xpath("//td[contains(text()," + user + ")]/..//td[5]//button[contains(text(),'Remove')]"))
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

	/* OTHER METHODS */

	public void waitForcancelButton() {
		new WebDriverWait(driver, 4).until(ExpectedConditions.visibilityOf(this.buttonCancel));
	}

	public void createGroup(String userName, String groupName, String groupDescription) {
		this.mainPage.clickAdminButton();
		this.mainPage.clickAdminNewGroupButton();
		this.sendKeysGroupName(groupName);
		this.sendKeysGroupDescription(groupDescription);
		this.clickAddSpecificUserButton(userName);
		this.clickCreateButton();
	}

	public void deleteGroup(String groupName) {
		try {
			Unirest.delete("http://akademijait.vtmc.lt:8180/dvs/api/group/{groupname}/delete")
					.routeParam("groupname", groupName).asString();
		} catch (UnirestException e) {
			e.printStackTrace();
		}
	}
}
