package koch.com.ixigo;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;

public class BrowserStackPage extends BasePage {

	WebDriver driver;
	@FindBy(xpath = "//*[@id='product-nav']//ul//li[contains(@class,'dropdown-holder dropdown-holder-desktop')]//button")
	private List<WebElement> HeadersWithDropDown;

	WebDriver objWebDriver;

	public BrowserStackPage(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);
	}

	public List<String> getValuesFromHeader() {
		List<String> objList = new ArrayList<String>();
		verifyElementIsPresent(getLocatorInfo(HeadersWithDropDown.get(0)));
		System.out.println(HeadersWithDropDown.size());
		Iterator<WebElement> it = HeadersWithDropDown.iterator();
		while (it.hasNext()) {

			String value = it.next().getAttribute("aria-label");
			objList.add(value);
		}
		return objList;
	}

	public void explictWait(String xpath) {
		WebDriverWait wait = new WebDriverWait(driver, 20);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)));
	}
}
