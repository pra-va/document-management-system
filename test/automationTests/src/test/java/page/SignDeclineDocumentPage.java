package page;

import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
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
	
	/* BUTTONS */
		
	@FindBy(xpath = "//button[contains(text(),'Close')]")
	private WebElement buttonClose;
	
	@FindBy(xpath = "//button[text()='Sign']")
	private WebElement buttonSign;
		
	@FindBy(xpath = "//button[text()='Decline']")
	private WebElement buttonDecline;
		
	/* SEND KEYS */
	
	public void sendKeysDeclineReasonField(String declineReason) {
		this.declineReasonField.sendKeys(declineReason);
	}	
		
	/* CLICK BUTTONS */
	
	public void clickCloseButton() {		
		this.buttonClose.click();
	}

	public void clickDeclineButton() {
		new WebDriverWait(driver, 5).ignoring(StaleElementReferenceException.class).
		until(ExpectedConditions.elementToBeClickable(buttonDecline));		
		this.buttonDecline.click();
	}
	
	public void clickSignButton() throws InterruptedException {	
		Thread.sleep(1000);
		new WebDriverWait(driver, 3).ignoring(StaleElementReferenceException.class).
		until(ExpectedConditions.elementToBeClickable(By.xpath("//button[contains(text(),'Sign')]")));
		this.buttonSign.click();
		
	}
	
	/* WAITS */

	public void waitForSignDeclineDocumentPage() {
		new WebDriverWait(driver, 2).until(ExpectedConditions.visibilityOf(buttonSign));
	}
	
	/* IS ENABLED */
	
	public boolean isDeclineButtonEnabled() {		
		return this.buttonDecline.isEnabled();
	}
}
