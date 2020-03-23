package page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class DocTypeListPage extends MainPage {

	public DocTypeListPage(WebDriver driver) {
		super(driver);
	}

	/* BUTTONS */

	@FindBy(xpath = "//button[contains(text(),'Cancel')]")
	private WebElement buttonCancel;

	/* CLICK BUTTONS */

	public void clickCancelButton() {
		this.buttonCancel.click();
	}

	public void clickEditSpecificDocType(String docType) {
		driver.findElement(By.xpath("//td[contains(text(),'" + docType + "')]/..//td[4]//button")).click();
	}

	/* SEND KEYS */

	public void sendKeysDocTypeSearch(String name) {
		driver.findElement(By.xpath("//input")).sendKeys(name);
	}

	/* OTHER METHODS */

	public void clearDocTypeSearch() {
		driver.findElement(By.xpath("//input")).clear();
	}

}
