package page;

import java.util.Arrays;
import java.util.List;

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

	public List<String> creatingGroupsNames(String docTypeName) {
		String s = driver.findElement(By.xpath("//td[contains(text(), '" + docTypeName + "')]/..//td[3]//span"))
				.getAttribute("data-content");
		String s1 = s.replace(".", "");
		return Arrays.asList(s1.split(", "));
	}

	public List<String> signingGroupsNames(String docTypeName) {
		String s = driver.findElement(By.xpath("//td[contains(text(), '" + docTypeName + "')]/..//td[4]//span"))
				.getAttribute("data-content");
		String s1 = s.replace(".", "");
		return Arrays.asList(s1.split(", "));
	}
}
