package page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class SignDeclineDocumentPage extends AbstractPage{

	public SignDeclineDocumentPage(WebDriver driver) {
		super(driver);	
	}

	/* FIELDS */

	@FindBy(id = "inputDocDescription")
	private WebElement declineReasonField;
	
//	@FindBy(id = "inputDocDescription")
//	private WebElement docDescriptionFied;
	
//	@FindBy(id = "documentFileUpload")
//	private WebElement fileUploadField;
	
//	/* SELECT */
//	
//	@FindBy(id = "selectDocType")
//	private Select drpdwDocumentType;
//		
//	/* BUTTONS */
		
	@FindBy(xpath = "//button[contains(text(),'Close')]")
	private WebElement buttonClose;
	
	@FindBy(xpath = "//button[contains(text(),'Sign')]")
	private WebElement buttonSign;
	
	@FindBy(xpath = "//button[contains(text(),'Decline')]")
	private WebElement buttonDecline;
		
	/* SEND KEYS */
	
	public void sendKeysDeclineReasonField(String declineReason) {
		this.declineReasonField.sendKeys(declineReason);
	}
	
//	public void sendKeysDocDescriptionField(String docDescription) {
//		this.docDescriptionFied.sendKeys(docDescription);
//	}
//	
//	public void sendKeysFileUploadField(String filePath) {
//		this.fileUploadField.sendKeys(filePath);
//	}
//		
//	/*CLEAR FIELDS*/
//			
//	public void clearDocNameField() {
//		this.docNameField.clear();
//	}
//	
//	public void clearDocDescriptionField() {
//		this.docDescriptionFied.clear();
//	}
		
	/* CLICK BUTTONS */
	
	public void clickCloseButton() {		
		this.buttonClose.click();
	}

	public void clickDeclineButton() {		
		this.buttonDecline.click();
	}
	
	public void clickSignButton() {		
		this.buttonSign.click();
	}
//	
//	public void clickRemoveFileButton(String fileName) {		
//		this.driver.findElement(By.xpath("//div[contains(text(),"+ fileName +")]//button[@aria-label='Close']")).click();		
//	}
	
	/* SELECT METHOD*/
//	public void selectDocumentType(String documentType) {
//	this.drpdwDocumentType.selectByVisibleText(documentType);
//}	

	/* GET TEXT METHODS */

//	public String getDocName() {
//		return this.docNameField.getAttribute("value");
//	}

//	public String getDocDescription() {
//		return this.docDescriptionFied.getAttribute("value");
//	}
		
//	public String getDocType() {
//		return this.drpdwDocumentType.getFirstSelectedOption().getText();
//	}
	
	/* WAITS */

	public void waitForSignDeclineDocumentPage() {
		new WebDriverWait(driver, 2).until(ExpectedConditions.visibilityOf(buttonSign));
	}
	
	/* IS ENABLED */
	
	public boolean isDeclineButtonEnabled() {		
		return this.buttonDecline.isEnabled();
	}
}
