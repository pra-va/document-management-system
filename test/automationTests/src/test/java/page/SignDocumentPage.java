package page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class SignDocumentPage extends AbstractPage{

	public SignDocumentPage(WebDriver driver) {
		super(driver);		
	}

	/* FIELDS */

	@FindBy(xpath = "//*[@aria-label='Search']")
	private WebElement searchDocumentField;
	
	/* CLICK BUTTONS */

	public void clickButtonSignDecline(String documentName) {
		driver.findElement(By.xpath("//td[2][text()='" + documentName + "']/..//button")).click();
	}

	/* SEND KEYS */

	public void sendKeysSearchDocument(String documentName) {
		this.searchDocumentField.sendKeys(documentName);
	}

	/* CLEAR FIELDS */
	
	public void clearSearchDocumentFied() {
		this.searchDocumentField.clear();
	}

	/* GET TEXT METHODS */
    
	public String getIDbyDocumentName(String documentName) {
		return driver.findElement(By.xpath("//td[2][text()='" + documentName + "']/..//td[1]")).getText();
	}
	
	public String getTypeByDocumentName(String documentName) {
		return driver.findElement(By.xpath("//td[2][text()='" + documentName + "']/..//td[3]")).getText();
	}

	public String getSubmissionDatebyDocumentName(String documentName) {
		return driver.findElement(By.xpath("//td[2][text()='" + documentName + "']/..//td[4]")).getText();
	}

	public String getCreatorByDocumentName(String documentName) {
		return driver.findElement(By.xpath("//td[2][text()='" + documentName + "']/..//td[5]")).getText();
	}
	
	/* IS DISPLAYED METHOD */
	
	public boolean isDocumentNameDisplayed(String documentName) {
		return driver.findElement(By.xpath("//td[2][text()='" + documentName + "']")).isDisplayed();
	}
	
	
}
