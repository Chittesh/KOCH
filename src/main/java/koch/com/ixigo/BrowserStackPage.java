package koch.com.ixigo;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.ResourceBundle;


public class BrowserStackPage extends BasePage {

	static ResourceBundle objResourceBundle = ResourceBundle.getBundle("urls");

	@FindBy(xpath = "//div[contains(@class,'row product-cards-wrapper')]//a")
	private List<WebElement> listOfWebsites;

	public BrowserStackPage(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);
	}

	public void launchPage() {
		System.out.println("Launching URL");
		driver.get(objResourceBundle.getString("baseUrl_BrowserStack"));
	}

	public List<String> getValuesFromHeader() {
		List<String> objList = new ArrayList<String>();
		verifyElementIsPresent(getLocatorInfo(listOfWebsites.get(0)));
		System.out.println(listOfWebsites.size());
		Iterator<WebElement> it = listOfWebsites.iterator();
		while (it.hasNext()) {

			String value = it.next().getAttribute("title");
			objList.add(value);
		}
		return objList;
	}
}
