package page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class StatisticsPage extends AbstractPage {

	public StatisticsPage(WebDriver driver) {
		super(driver);
	}

	/* FIELDS */

	/* BUTTONS */

	/* SEND KEYS */

	/* CLICK BUTTONS */

	/* WAITS */

	public void waitForStatistics(String docTypeName) throws InterruptedException {
		Thread.sleep(100);
		new WebDriverWait(driver, 6).until(ExpectedConditions
				.visibilityOf(driver.findElement(By.xpath("//td[contains(text(), '" + docTypeName + "')]"))));
	}

	/* OTHER METHODS */
}
