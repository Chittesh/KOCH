package koch.com.ixigo;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class IxigoHomePage extends BasePage {

	@FindBy(xpath = "//*[@id='ixiLogoImg']")
	private WebElement elmIxiLogo;
	@FindBy(xpath = "(//*[contains(text(),'From')]/following-sibling::input[@placeholder='Enter city or airport'])[1]")
	private WebElement webElmFromInput;
	@FindBy(xpath = "(//*[contains(text(),'To')]/following-sibling::input[@placeholder='Enter city or airport'])[1]")
	private WebElement webElmToInput;
	@FindBy(xpath = "//*[@placeholder='Depart']")
	private WebElement webElmDepart;
	@FindBy(xpath = "//*[@placeholder='Return']")
	private WebElement webElmReturn;
	@FindBy(xpath = "//*[contains(text(),'Travellers | Class')]/following-sibling::input")
	private WebElement webElmTarvellers;
	@FindBy(xpath = "//button[contains(text(),'Search')]")
	private WebElement webElmSearchButton;
	@FindBy(xpath = "//*[contains(@class,'rd-date')]//table")
	private WebElement webElmDateTableFrom;
	@FindBy(xpath = "(//*[contains(@class,'rd-date')]//table)[last()]")
	private WebElement webElmDateTableTo;
	@FindBy(xpath = "//*[contains(text(),'Adult')]/ancestor::*[contains(@class,'number-counter')]//*[@data-val='2']")
	private WebElement webElmDateNoOfTravellers;

	public IxigoHomePage(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);
	}

	public boolean verifyFromInputIsPresent() {
		return verifyElementIsPresent(getLocatorInfo(webElmFromInput));
	}

	public boolean verifyIxiLogoIsPresent() {
		return verifyElementIsPresent(getLocatorInfo(elmIxiLogo));
	}

	public String getDefaultDepartureDate() {
		verifyElementIsPresent(getLocatorInfo(webElmDepart));
		return webElmDepart.getAttribute("value");
	}

	public String getDefaultTravellers() {
		verifyElementIsPresent(getLocatorInfo(webElmTarvellers));
		return webElmTarvellers.getAttribute("value");
	}

	public void enterTravelDetails(String from, String to, String tommorowsDate, String dateAfterOneWeek)
			throws InterruptedException {
		System.out.println("Entering details to Search flights");

		System.out.println("Entering From Place");
		webElmFromInput.clear();
		Thread.sleep(2000);
		webElmFromInput.sendKeys(from);
		Thread.sleep(2000);
		webElmFromInput.sendKeys(Keys.ENTER);

		System.out.println("Entering To Place");
		webElmToInput.clear();
		Thread.sleep(2000);
		webElmToInput.sendKeys(to);
		Thread.sleep(2000);
		webElmToInput.sendKeys(Keys.ENTER);
	
		Thread.sleep(2000);
		System.out.println("Selecting Depart Date");
		webElmDepart.click();
		Thread.sleep(2000);
		verifyElementIsPresent(getLocatorInfo(webElmDateTableFrom));
		WebElement fromDate = driver
				.findElement(By.xpath("//*[contains(@class,'rd-date')]//table//*[@data-date='" + tommorowsDate + "']"));
		fromDate.click();
		Thread.sleep(2000);
		System.out.println("Selecting Return Date");
		webElmReturn.click();
		Thread.sleep(2000);
		verifyElementIsPresent(getLocatorInfo(webElmDateTableTo));
		WebElement toDate = driver.findElement(
				By.xpath("(//*[contains(@class,'rd-date')]//table//*[@data-date='" + dateAfterOneWeek + "'])[last()]"));
		toDate.click();
		Thread.sleep(2000);

		System.out.println("Selecting No of Travellers");
		webElmTarvellers.click();
		Thread.sleep(2000);
		verifyElementIsPresent(getLocatorInfo(webElmDateNoOfTravellers));
		webElmDateNoOfTravellers.click();
		Thread.sleep(2000);

		System.out.println("Clicking Search Button");
		webElmSearchButton.click();

	}

}
