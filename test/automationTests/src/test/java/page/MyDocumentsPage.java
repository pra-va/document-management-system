package page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class MyDocumentsPage extends AbstractPage {

	public MyDocumentsPage(WebDriver driver) {
		super(driver);
	}

	/* FIELDS */

	@FindBy(xpath = "//*[@aria-label='Search']")
	private WebElement searchDocumentField;

	/* BUTTONS */

	@FindBy(id = "all")
	private WebElement buttonAllDocuments;

	@FindBy(id = "created")
	private WebElement buttonCreated;

	@FindBy(id = "submitted")
	private WebElement buttonSubmitted;

	@FindBy(id = "declined")
	private WebElement buttonDeclined;

	@FindBy(id = "accepted")
	private WebElement buttonAccepted;

	@FindBy(id = "popover")
	private WebElement fileIcon;

	/* CLICK BUTTONS */

	public void clickButtonAll() {
		this.buttonAllDocuments.click();
	}

	public void clickButtonCreated() {
		this.buttonCreated.click();
	}

	public void clickButtonSubmitted() {
		this.buttonSubmitted.click();
	}

	public void clickButtonDeclined() {
		this.buttonDeclined.click();
	}

	public void clickButtonAccepted() {
		this.buttonAccepted.click();
	}

	public void clickEditViewDocument(String documentName) {
		driver.findElement(By.xpath("//td[7]//button")).click();
	}

	public void clickButtonSubmit(String documentName) {
		driver.findElement(By.xpath("//td[2][text()='" + documentName + "']/..//button[text()='Submit']")).click();
	}

	/* CLEAR FIELDS */

	public void clearSearchDocumentField() {
		this.searchDocumentField.clear();
	}

	/* SEND KEYS */

	public void sendKeysSearchDocument(String documentInformation) {
		this.searchDocumentField.sendKeys(documentInformation);
	}

	/* GET TEXT */

	public String getIDbyDocumentName(String documentName) {
		return driver.findElement(By.xpath("//td[2][text()='" + documentName + "']/..//td[1]")).getText();
	}

	public String getTypeByDocumentName(String documentName) {
		return driver.findElement(By.xpath("//td[2][text()='" + documentName + "']/..//td[3]")).getText();
	}

	public String getStatusByDocumentName(String documentName) {
		return driver.findElement(By.xpath("//td[2][text()='" + documentName + "']/..//td[4]")).getText();
	}

	public String getCreationDatebyDocumentName(String documentName) {
		return driver.findElement(By.xpath("//td[2][text()='" + documentName + "']/..//td[5]")).getText();
	}

	public String getFileNameByDocumentName(String documentName) {
		return driver.findElement(By.xpath("//td[2][text()='" + documentName + "']/..//td[6]/span"))
				.getAttribute("data-content");
	}

	/* IS DISPLAYED */

	public boolean isDocumentNameDisplayed(String documentName) {
		return driver.findElement(By.xpath("//td[2][text()='" + documentName + "']")).isDisplayed();
	}

	/* IS CREATED */

	public boolean isButtonSubmitEnabled(String documentName) {
		return driver.findElement(By.xpath("//td[2][text()='" + documentName + "']/..//button[text()='Submit']"))
				.isEnabled();
	}
	
	/* WAIT */
	
	public void waitForDocumentVisibility(String documentName) {
		new WebDriverWait(driver, 6).until(ExpectedConditions.visibilityOf
				(driver.findElement(By.xpath("//td[text()='" + documentName + "']"))));
	}
	

}