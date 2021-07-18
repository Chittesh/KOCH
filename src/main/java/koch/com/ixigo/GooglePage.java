package koch.com.ixigo;

import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;

public class GooglePage extends BasePage {
	WebDriver driver;
	@FindBy(xpath = "(//input[@name='btnK'])[last()]")
	private WebElement elmButton;
	@FindBy(xpath = "//*[@id='res']//*[@class='g']//a")
	private WebElement elmLink;
	@FindBy(xpath = "//input[@name='q']")
	private WebElement elmSearch;

	WebDriver objWebDriver;

	public GooglePage(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);
	}

	public void enterValueInTextBox(String text) {
		elmSearch.sendKeys(text);
		elmSearch.sendKeys(Keys.DOWN);
		elmSearch.sendKeys(Keys.TAB);
	}

	public void clickOnSearchButton() {
		elmButton.click();
	}

	public void clickOnLink() {
		elmLink.click();
	}

}
