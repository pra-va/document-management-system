package page;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class NewDocumentPage extends AbstractPage {

	public NewDocumentPage(WebDriver driver) {
		super(driver);
	}

	/* FIELDS */

	@FindBy(id = "inputDocName")
	private WebElement docNameField;

	@FindBy(id = "inputDocDescription")
	private WebElement docDescriptionFied;

	@FindBy(xpath = "//*[@aria-label='Search']")
	private WebElement searchDocTypeField;

	@FindBy(id = "documentFileUpload")
	private WebElement fileUploadField;

	/* BUTTONS */

	@FindBy(xpath = "//button[contains(text(),'Create')]")
	private WebElement buttonCreate;

	/* SEND KEYS */

	public void sendKeysFileUploadField(String filePath) {
		this.fileUploadField.sendKeys(filePath);
	}

	public void sendKeysDocNameField(String docName) {
		this.docNameField.sendKeys(docName);
	}

	public void sendKeysDocDescriptionField(String docDescription) {
		this.docDescriptionFied.sendKeys(docDescription);
	}

	public void sendKeysSearchForDocType(String docType) {
		this.searchDocTypeField.sendKeys(docType);
	}

	/* CLICK BUTTONS */

	public void clickCreateButton() {
		this.buttonCreate.click();
	}

	public void clickSelectSpecificDocTypeButton(String docTypeName) {
		driver.findElement(By.xpath("//td[contains(text()," + docTypeName + ")]")).click();
	}

	/* WAITS */

	public void waitForFileNameVisibility(String fileName) {
		new WebDriverWait(driver, 2).until(ExpectedConditions.visibilityOf
				(driver.findElement(By.xpath("//div[contains(text(),"+ fileName + ")]"))));
	}
}
