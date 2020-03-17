package page;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class EditDocumentPage extends AbstractPage {

	public EditDocumentPage(WebDriver driver) {
		super(driver);		
	}
	
	/* FIELDS */

	@FindBy(id = "inputDocName")
	private WebElement docNameField;
	
	@FindBy(id = "inputDocDescription")
	private WebElement docDescriptionFied;
	
	/* SELECT */
	
	@FindBy(id = "selectDocType")
	private Select drpdwDocumentType;
	
//	@FindBy(xpath = "//*[@aria-label='Search']")
//	private List<WebElement> searchFields;
//	
//	@FindBy(id = "documentFileUpload")
//	private WebElement fileUploadField;
//		
//	/* BUTTONS */
//	
	@FindBy(xpath = "//button[contains(text(),'Submit')]")
	private WebElement buttonSubmit;
	
	@FindBy(xpath = "//button[contains(text(),'Cancel')]")
	private WebElement buttonCancel;
		
	@FindBy(id = "documentFileUpload")
	private WebElement fileUploadField;
		
	/* SEND KEYS */
	
	public void sendKeysDocNameField(String docName) {
		this.docNameField.sendKeys(docName);
	}
	
	public void sendKeysDocDescriptionField(String docDescription) {
		this.docDescriptionFied.sendKeys(docDescription);
	}
	
	public void sendKeysFileUploadField(String filePath) {
		this.fileUploadField.sendKeys(filePath);
	}
	
	/*CLEAR FIELDS*/
			
	public void clearDocNameField() {
		this.docNameField.clear();
	}
	
	public void clearDocDescriptionField() {
		this.docDescriptionFied.clear();
	}
		
	/* CLICK BUTTONS */
	
	public void clickSubmitButton() {		
		this.buttonSubmit.click();
	}
	
	public void clickCancelButton() {		
		this.buttonCancel.click();
	}
	
	public void clickRemoveFileButton(String fileName) {		
		this.driver.findElement(By.xpath("//div[contains(text(),"+ fileName +")]//button[@aria-label='Close']")).click();		
	}
	
	/* SELECT METHOD*/

	public void selectDocumentType(String documentType) {
	this.drpdwDocumentType.selectByVisibleText(documentType);
}	

	/* GET TEXT METHODS */

	public String getDocName() {
		return this.docNameField.getAttribute("value");
	}

	public String getDocDescription() {
		return this.docDescriptionFied.getAttribute("value");
	}
		
	public String getDocType() {
		return this.drpdwDocumentType.getFirstSelectedOption().getText();
	}
	
	/* WAITS */

	public void waitForVisibility(WebElement webElement) {
		new WebDriverWait(driver, 2).until(ExpectedConditions.visibilityOf(webElement));
	}
	
	public void waitForEditDocumentPage() {
		new WebDriverWait(driver, 2).until(ExpectedConditions.visibilityOf(docNameField));
	}
	
	public void waitForFileNameVisibility(String fileName) {
		new WebDriverWait(driver, 2).until(ExpectedConditions.visibilityOf
				(driver.findElement(By.xpath("//div[contains(text(),"+ fileName + ")]"))));
	}
	
	/* IS DISPLAYED METHOD */
	
	public boolean isFileNameDisplayed(String fileName) {
		return driver.findElement(By.xpath("//div[contains(text()," + fileName + ")]")).isDisplayed();
	}
	
	
}
