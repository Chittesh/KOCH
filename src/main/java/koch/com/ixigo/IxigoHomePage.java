package koch.com.ixigo;

import java.util.ResourceBundle;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class IxigoHomePage extends BasePage {
	WebDriver driver;
	static ResourceBundle urls = ResourceBundle.getBundle("urls");

	@FindBy(xpath = "(//*[contains(text(),'From')]/following-sibling::input[@placeholder='Enter city or airport'])[1]")
	private WebElement webElmFromInput;

	public IxigoHomePage(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);
	}

	public boolean verifyFromInputIsPresent() {
		return verifyElementIsPresent(getLocatorInfo(webElmFromInput));
	}

}
