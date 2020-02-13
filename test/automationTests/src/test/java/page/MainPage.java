package page;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class MainPage extends AbstractPage {

	public MainPage(WebDriver driver) {
		super(driver);
	}

	/* BUTTONS */

	@FindBy(xpath = "//a[@href='#/home']")
	private WebElement dmsButton;

	@FindBy(xpath = "//a[contains(text(),'Create Document ')]")
	private WebElement buttonCreateDocument;

	@FindBy(xpath = "//a[contains(text(),'Sign Document ')]")
	private WebElement buttonSignDocument;

	@FindBy(xpath = "//a[contains(text(),'My Documents ')]")
	private WebElement buttonMyDocuments;

	@FindBy(xpath = "//a[contains(text(),'Admin')]")
	private WebElement buttonAdmin;

	@FindBy(xpath = "//button[contains(text(),'New User')]")
	private WebElement buttonAdminNewUser;

	@FindBy(xpath = "//button[contains(text(),'New Group')]")
	private WebElement buttonAdminNewGroup;

	@FindBy(xpath = "//a[contains(text(),'Users')]")
	private WebElement buttonAdminUsers;

	@FindBy(xpath = "//a[contains(text(),'Groups')]")
	private WebElement buttonAdminGroups;

	@FindBy(xpath = "//a[contains(text(),'Logout ')]")
	private WebElement buttonLogout;

	/* CLICK BUTTONS */

	public void clickDmsButton() {
		this.dmsButton.click();
	}

	public void clickCreateDocumentButton() {
		this.buttonCreateDocument.click();
	}

	public void clickSignDocumentButton() {
		this.buttonSignDocument.click();
	}

	public void clickMyDocumentsButton() {
		this.buttonMyDocuments.click();
	}

	public void clickAdminButton() {
		this.buttonAdmin.click();
	}

	public void clickAdminNewUserButton() {
		this.buttonAdminNewUser.click();
	}

	public void clickAdminNewGroupButton() {
		this.buttonAdminNewGroup.click();
	}

	public void clickAdminUsersButton() {
		this.buttonAdminUsers.click();
	}

	public void clickAdminGroupsButton() {
		this.buttonAdminGroups.click();
	}

	public void clickLogoutButton() {
		this.buttonLogout.click();
	}
	
	/* OTHER METHODS */

	public void navigateToMainPage() {
		driver.get("http://akademijait.vtmc.lt:8180/dvs/#/home");
	}
}
