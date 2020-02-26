package page;

import java.util.Arrays;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class DocTypeListPage extends MainPage {

	public DocTypeListPage(WebDriver driver) {
		super(driver);
	}
	
	/* OTHER METHODS */

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
