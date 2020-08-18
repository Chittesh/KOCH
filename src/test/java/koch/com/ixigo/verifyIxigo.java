package koch.com.ixigo;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.testng.Assert;
import org.testng.annotations.Test;

public class verifyIxigo extends TestEnvironment {

	static DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd MMM, EEE");
	static final String expectedDepartureDate = dtf.format(LocalDateTime.now());

	@Test()
	public void verifyIxigoPageIsLoaded() throws InterruptedException {
		IxigoHomePage objIxigoHomePage = new IxigoHomePage(driver);
		objIxigoHomePage.launchPage();
		System.out.println("Verifying Ixi Logo is present");
		Assert.assertTrue(objIxigoHomePage.verifyIxiLogoIsPresent(), "Verify Ixi Logo is present");
		System.out.println("Verifying From Place Input is present");
		Assert.assertTrue(objIxigoHomePage.verifyFromInputIsPresent(), "Verify From Date Input filed is present");
		System.out.println("Getting Default Departure Date : " + objIxigoHomePage.getDefaultDepartureDate());
		Assert.assertEquals(objIxigoHomePage.getDefaultDepartureDate(), expectedDepartureDate,
				"Verify Departure Date Acutual : " + objIxigoHomePage.getDefaultDepartureDate() + " Expected is : "
						+ expectedDepartureDate);
		System.out.println("Getting Default Travellers text : " + objIxigoHomePage.getDefaultTravellers());
		Assert.assertEquals(objIxigoHomePage.getDefaultTravellers(), StaticConstantClass.expectedDefaultTravellers,
				"Verify Default Travllers Acutual : " + objIxigoHomePage.getDefaultTravellers() + " Expected is : "
						+ StaticConstantClass.expectedDefaultTravellers);

	}

	@Test(dependsOnMethods = "verifyIxigoPageIsLoaded")
	public void verifyPassangerDetailsAreAdded() throws InterruptedException {
		IxigoHomePage objIxigoHomePage = new IxigoHomePage(driver);
		SimpleDateFormat sdf = new SimpleDateFormat("ddMMYYYY");
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DAY_OF_MONTH, 1);
		String tommorowsDate = sdf.format(cal.getTime());
		cal.add(Calendar.DAY_OF_MONTH, 6);
		String dateAfterOneWeek = sdf.format(cal.getTime());

		objIxigoHomePage.enterTravelDetails(StaticConstantClass.fromLocation, StaticConstantClass.toLocation,
				tommorowsDate, dateAfterOneWeek);

		BookingPage objBookingPage = new BookingPage(driver);
		Assert.assertTrue(objBookingPage.verifyMoreFiltersLinkIsPresent(), "Verify More Filter link is present");

		HashMap<String, String> hm = new HashMap<String, String>();
		hm = objBookingPage.getPassangerDetails();
		SimpleDateFormat actualsdf = new SimpleDateFormat("dd MMM, EEE");
		Calendar actualcal = Calendar.getInstance();
		actualcal.add(Calendar.DAY_OF_MONTH, 1);
		String actualcaltommorowsDate = actualsdf.format(actualcal.getTime());
		actualcal.add(Calendar.DAY_OF_MONTH, 6);
		String actualcaldateAfterOneWeek = actualsdf.format(actualcal.getTime());

		System.out.println("Verification Passanger Details starts here");
		System.out.println("Passanger Details captured from Booking page are");
		for (Entry<String, String> entry : hm.entrySet()) {
			System.out.println(entry.getKey());
		}
		Assert.assertEquals(hm.get("From Place"), StaticConstantClass.fromLocation, "Verify From Place Acutual : "
				+ hm.get("From Place") + " Expected is : " + StaticConstantClass.fromLocation);
		Assert.assertEquals(hm.get("To Place"), StaticConstantClass.toLocation,
				"Verify To Place Acutual : " + hm.get("To Place") + " Expected is : " + StaticConstantClass.toLocation);
		Assert.assertEquals(hm.get("Depart Date"), actualcaltommorowsDate,
				"Verify Depart Date Acutual : " + hm.get("Depart Date") + " Expected is : " + actualcaltommorowsDate);
		Assert.assertEquals(hm.get("Return Date"), actualcaldateAfterOneWeek, "Verify Return Date Acutual : "
				+ hm.get("Return Date") + " Expected is : " + actualcaldateAfterOneWeek);
		Assert.assertEquals(hm.get("No Of Passangers"), StaticConstantClass.expectedTravellers,
				"Verify No Of Passangers Acutual : " + hm.get("No Of Passangers") + " Expected is : "
						+ StaticConstantClass.expectedTravellers);
		System.out.println("Verification Passanger Details ends here");
	}

	@Test(dependsOnMethods = "verifyPassangerDetailsAreAdded")
	public void verifyNonStopFlights() throws InterruptedException {
		BookingPage objBookingPage = new BookingPage(driver);
		Assert.assertTrue(objBookingPage.verifyStopsSection(), "Verify Stops Section is present");
		Assert.assertTrue(objBookingPage.verifyDepartureFromSection(), "Verify Departure From Section is present");
		Assert.assertTrue(objBookingPage.verifyAirlinesSection(), "Verify Airline Section is present");

		System.out.println("Getting Deatils of Departure Flights before applying Non Stop Check Box");
		System.out.println("No of Departure Flights " + objBookingPage.getNoOFDepartureFlights());
		List<String> lsDepartureStops = new ArrayList<String>();
		lsDepartureStops = objBookingPage.getDepartureStopsFromFlights();
		System.out.println("Stop Details of Deaprture flight");
		lsDepartureStops.stream().distinct().forEach(System.out::println);
		lsDepartureStops.clear();
		System.out.println("Getting Deatils of Return Flights before applying Non Stop Check Box");
		System.out.println("No of Return Flights " + objBookingPage.getNoOFReturnFlights());
		List<String> lsReturnStops = new ArrayList<String>();
		lsReturnStops = objBookingPage.getReturnStopsFromFlights();
		System.out.println("Stop Details of Return flight");
		lsReturnStops.stream().distinct().forEach(System.out::println);
		lsReturnStops.clear();

		System.out.println("*****************************************************************");
		System.out.println("Checking Non Stop ICON");
		objBookingPage.checkNonStopIcon();
		System.out.println("*****************************************************************");

		System.out.println("Getting Deatils of Departure Flights after applying Non Stop Check Box");
		System.out.println("No of Departure Flights " + objBookingPage.getNoOFDepartureFlights());
		lsDepartureStops = objBookingPage.getDepartureStopsFromFlights();
		System.out.println("Stop Details of Deaprture flight");
		lsDepartureStops.stream().distinct().forEach(System.out::println);
		System.out.println("Getting Deatils of Return Flights after applying Non Stop Check Box");
		System.out.println("No of Return Flights " + objBookingPage.getNoOFReturnFlights());
		lsReturnStops = objBookingPage.getReturnStopsFromFlights();
		System.out.println("Stop Details of Return flight");
		lsReturnStops.stream().distinct().forEach(System.out::println);

		String non_Stop = "non-stop";
		String stop_1 = "1 stop";
		String stop_2 = "2 stops";

		System.out.println("Verifying non-stop Flights are only present");
		Assert.assertTrue(lsDepartureStops.stream().anyMatch(non_Stop::contains),
				"Verify In Departure Flight list has non stop");
		Assert.assertTrue(lsReturnStops.stream().anyMatch(non_Stop::contains),
				"Verify In Return Flight list has non stop");
		System.out.println("Verifying 1 stop Flights are not present");
		Assert.assertFalse(lsDepartureStops.stream().anyMatch(stop_1::contains),
				"Verify In Departure Flight list dosen't have 1 stop");
		Assert.assertFalse(lsReturnStops.stream().anyMatch(stop_1::contains),
				"Verify In Return Flight list dosen't have 1 stop");
		System.out.println("Verifying 2 stops Flights are not present");
		Assert.assertFalse(lsDepartureStops.stream().anyMatch(stop_2::contains),
				"Verify In Departure Flight list dosen't have 2 stops");
		Assert.assertFalse(lsReturnStops.stream().anyMatch(stop_2::contains),
				"Verify In Return Flight list dosen't have 2 stops");

		System.out.println("Checking Non Stop ICON");
		objBookingPage.checkNonStopIcon();
	}

	@Test(dependsOnMethods = "verifyNonStopFlights")
	public void verifyFlightsWithFareLessThanFiveThosuand() throws InterruptedException {
		BookingPage objBookingPage = new BookingPage(driver);
		System.out.println("Getting Fare Details of Departure Flights");
		List<String> lsDepartureFares = new ArrayList<String>();
		lsDepartureFares = objBookingPage.getDepartureFareFromFlights();
		System.out.println("Fare Details Details of Deaprture flight");
		lsDepartureFares.stream().forEach(System.out::println);
		lsDepartureFares.clear();
		System.out.println("*****************************************************************");
		System.out.println("Getting Fare Details of Return Flights");
		List<String> lsReturnFares = new ArrayList<String>();
		lsReturnFares = objBookingPage.getReturnFareFromFlights();
		System.out.println("Fare Details Details of Return flight");
		lsReturnFares.stream().forEach(System.out::println);
		lsReturnFares.clear();
		System.out.println("*****************************************************************");
		System.out.println("Getting Fare Details of Departure Flights which is having Fare less than 5000");
		System.out.println("Details of Deaprture flight which is having Fare less than 5000");
		objBookingPage.getDepartureFareFromFlightsLessThanFiveThousand();
		System.out.println("*****************************************************************");
		System.out.println("Getting Fare Details of Return Flights which is having Fare less than 5000");
		System.out.println("Details of Return flight which is having Fare less than 5000");
		objBookingPage.getReturnFareFromFlightsLessThanFiveThousand();
		System.out.println("*****************************************************************");
	}

}
