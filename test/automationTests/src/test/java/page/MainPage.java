package page;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class MainPage extends AbstractPage {

	public MainPage(WebDriver driver) {
		super(driver);
	}

	/* BUTTONS */

	@FindBy(xpath = "//a[@href='/dvs/home']")
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

	@FindBy(id = "showCreateDoc")
	private WebElement buttonAdminNewDocType;
	
	@FindBy(id = "")
	private WebElement buttonAdminDocTypes;

	@FindBy(xpath = "//a[contains(text(),'Users')]")
	private WebElement buttonAdminUsers;

	@FindBy(xpath = "//a[contains(text(),'Groups')]")
	private WebElement buttonAdminGroups;

	@FindBy(xpath = "//a[contains(text(),'Document Types')]")
	private WebElement buttonDocTypes;

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
		waitForVisibility(buttonAdmin);
		this.buttonAdmin.click();
	}

	public void clickAdminNewUserButton() {
		waitForVisibility(buttonAdminNewUser);
		this.buttonAdminNewUser.click();
	}

	public void clickAdminNewGroupButton() {
		waitForVisibility(buttonAdminNewGroup);
		this.buttonAdminNewGroup.click();
	}

	public void clickAdminUsersButton() {
		waitForVisibility(buttonAdminUsers);
		this.buttonAdminUsers.click();
	}

	public void clickAdminGroupsButton() {
		waitForVisibility(buttonAdminGroups);
		this.buttonAdminGroups.click();
	}

	public void clickLogoutButton() {
		this.buttonLogout.click();
	}

	/* OTHER METHODS */

	public void waitForVisibility(WebElement element) {
		new WebDriverWait(driver, 4).until(ExpectedConditions.visibilityOf(element));
	}

	public void navigateToMainPage() {
		this.clickDmsButton();
	}

	public void waitForAdminButton() {
		this.waitForVisibility(this.buttonAdmin);
	}

	public void waitForLogoutButton() {
		this.waitForVisibility(this.buttonLogout);
	}
}
