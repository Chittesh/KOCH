package koch.com.ixigo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class BookingPage extends BasePage {

	@FindBy(xpath = "//*[contains(@class,'more-fltrs u-link')]")
	private WebElement elmMoreFilters;
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

	@FindBy(xpath = "//*[contains(text(),'Stops')]/parent::*[@class='fltr']")
	private WebElement webElmStopsSection;
	@FindBy(xpath = "//*[contains(text(),'Departure from')]/parent::*[contains(@class,'fltr')]")
	private WebElement webElmDeparturefromSection;
	@FindBy(xpath = "//*[contains(text(),'Airlines')]/parent::*[contains(@class,'fltr')]")
	private WebElement webAirlinesSection;

	@FindBy(xpath = "//*[@class='result-col outr']//*[contains(@class,'c-flight-listing-split-row')]")
	private List<WebElement> WebNoOFDepartureFlights;
	@FindBy(xpath = "//*[@class='result-col outr']//*[contains(@class,'c-flight-listing-split-row')]//*[@class='stop']")
	private List<WebElement> WebNoOFDepartureFlightsStop;
	@FindBy(xpath = "//*[@class='result-col']//*[contains(@class,'c-flight-listing-split-row')]")
	private List<WebElement> WebNoOFReturnFlights;
	@FindBy(xpath = "//*[@class='result-col']//*[contains(@class,'c-flight-listing-split-row')]//*[@class='stop']")
	private List<WebElement> WebNoOFReturnFlightsStop;
	@FindBy(xpath = "//*[contains(text(),'Non stop')]/../..//*[contains(@class,'check-icon')]")
	private WebElement WebNonStopCheckIcon;

	public BookingPage(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);
	}

	public boolean verifyStopsSection() {
		System.out.println("Verify Stops section is present");
		return verifyElementIsPresent(getLocatorInfo(webElmStopsSection));
	}

	public boolean verifyDepartureFromSection() {
		System.out.println("Verify Departure section is present");
		return verifyElementIsPresent(getLocatorInfo(webElmDeparturefromSection));
	}

	public boolean verifyAirlinesSection() {
		System.out.println("Verify Airline section is present");
		return verifyElementIsPresent(getLocatorInfo(webAirlinesSection));
	}

	public boolean verifyMoreFiltersLinkIsPresent() {
		System.out.println("Verify More Filter link is present");
		return verifyElementIsPresent(getLocatorInfo(elmMoreFilters));
	}

	public HashMap<String, String> getPassangerDetails() {
		System.out.println("Getting Passanger Deatils from Booking Page Header");
		HashMap<String, String> hm = new HashMap<String, String>();
		hm.put("From Place", webElmFromInput.getAttribute("value"));
		hm.put("To Place", webElmToInput.getAttribute("value"));
		hm.put("Depart Date", webElmDepart.getAttribute("value"));
		hm.put("Return Date", webElmReturn.getAttribute("value"));
		hm.put("No Of Passangers", webElmTarvellers.getAttribute("value"));
		return hm;
	}

	public int getNoOFDepartureFlights() {
		return WebNoOFDepartureFlights.size();
	}

	public int getNoOFReturnFlights() {
		return WebNoOFReturnFlights.size();
	}

	public void checkNonStopIcon() throws InterruptedException {
		WebNonStopCheckIcon.click();
		Thread.sleep(2000);
	}

	public List<String> getDepartureStopsFromFlights() {
		List<String> ls = new ArrayList<String>();
		for (int i = 0; i < WebNoOFDepartureFlightsStop.size(); i++) {
			ls.add(WebNoOFDepartureFlightsStop.get(i).getText());
		}
		return ls;
	}
	
	public List<String> getReturnStopsFromFlights() {
		List<String> ls = new ArrayList<String>();
		for (int i = 0; i < WebNoOFReturnFlightsStop.size(); i++) {
			ls.add(WebNoOFReturnFlightsStop.get(i).getText());
		}
		return ls;
	}

}
