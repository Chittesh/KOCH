package koch.com.ixigo;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import org.testng.Assert;
import org.testng.annotations.Test;

public class verifyIxigo extends TestEnvironment {

	static DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd MMM, EEE");
	static final String expectedDepartureDate = dtf.format(LocalDateTime.now());
	static String fromDate;
	static String toDate;

	@Test()
	public void verifyIxigoPageIsLoaded() throws InterruptedException {

		IxigoHomePage objIxigoHomePage = new IxigoHomePage(driver);
		objIxigoHomePage.launchPage();
		System.out.println("*****************************************************************");
		System.out.println("Verifying Ixi Logo is present");
		Assert.assertTrue(objIxigoHomePage.verifyIxiLogoIsPresent(), "Verify Ixi Logo is present");
		System.out.println("Verifying From Place Input is present");
		Assert.assertTrue(objIxigoHomePage.verifyFromInputIsPresent(), "Verify From Date Input filed is present");
		System.out.println("Verifying Default Departure Date : " + objIxigoHomePage.getDefaultDepartureDate());
		Assert.assertEquals(objIxigoHomePage.getDefaultDepartureDate(), expectedDepartureDate,
				"Verify Departure Date Acutual : " + objIxigoHomePage.getDefaultDepartureDate() + " Expected is : "
						+ expectedDepartureDate);
		System.out.println("Verifying Default Travellers text : " + objIxigoHomePage.getDefaultTravellers());
		Assert.assertEquals(objIxigoHomePage.getDefaultTravellers(), StaticConstantClass.expectedDefaultTravellers,
				"Verify Default Travllers Acutual : " + objIxigoHomePage.getDefaultTravellers() + " Expected is : "
						+ StaticConstantClass.expectedDefaultTravellers);
		System.out.println("*****************************************************************");

	}

	public void setDates(String pattern) {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
		
		Calendar c1 = Calendar.getInstance();
		c1.setTime(new Date());
		c1.add(Calendar.DATE, 5);
		fromDate = simpleDateFormat.format(c1.getTime());

		Calendar c = Calendar.getInstance();
		c.setTime(new Date());
		c.add(Calendar.DATE, 40);
		String output = simpleDateFormat.format(c.getTime());
		System.out.println(output);
		toDate = output;
	}

	@Test(dependsOnMethods = "verifyIxigoPageIsLoaded")
	public void verifyPassangerDetailsAreAdded() throws InterruptedException {
		setDates("ddMMyyyy");
		IxigoHomePage objIxigoHomePage = new IxigoHomePage(driver);
		System.out.println("*****************************************************************");
		objIxigoHomePage.enterTravelDetails(StaticConstantClass.fromLocation, StaticConstantClass.toLocation, fromDate,
				toDate);
		System.out.println("*****************************************************************");
		BookingPage objBookingPage = new BookingPage(driver);
		Assert.assertTrue(objBookingPage.verifyMoreFiltersLinkIsPresent(), "Verify More Filter link is present");
		HashMap<String, String> hm = new HashMap<String, String>();
		hm = objBookingPage.getPassangerDetails();
		System.out.println("Verification Passanger Details starts here");
		System.out.println("*****************************************************************");
		System.out.println("Passanger Details captured are");
		for (Entry<String, String> entry : hm.entrySet()) {
			System.out.println(entry.getKey() + " : " + entry.getValue());
		}
		System.out.println("*****************************************************************");

		setDates("dd MMM, EEE");
		System.out.println("Verifying atleast One Departure and atleast One Return Flight is present");
		Assert.assertTrue(objBookingPage.getNoOFDepartureFlights() >= 1,
				"Verify Atleast One Departure flight is present");
		Assert.assertTrue(objBookingPage.getNoOFReturnFlights() >= 1, "Verify Atleast One Return flight is present");
		Assert.assertEquals(hm.get("Departure Place"), StaticConstantClass.fromLocation, "Verify From Place Acutual : "
				+ hm.get("Departure Place") + " Expected is : " + StaticConstantClass.fromLocation);
		Assert.assertEquals(hm.get("Destination Place"), StaticConstantClass.toLocation, "Verify To Place Acutual : "
				+ hm.get("Destination Place") + " Expected is : " + StaticConstantClass.toLocation);
		Assert.assertEquals(hm.get("Depart Date"), fromDate,
				"Verify Depart Date Acutual : " + hm.get("Depart Date") + " Expected is : " + fromDate);
		Assert.assertEquals(hm.get("Return Date"), toDate,
				"Verify Return Date Acutual : " + hm.get("Return Date") + " Expected is : " + toDate);
		Assert.assertEquals(hm.get("No Of Passangers"), StaticConstantClass.expectedTravellers,
				"Verify No Of Passangers Acutual : " + hm.get("No Of Passangers") + " Expected is : "
						+ StaticConstantClass.expectedTravellers);
		System.out.println("Verification Passanger Details ends here");
	}

	@Test(dependsOnMethods = "verifyPassangerDetailsAreAdded")
	public void verifyNonStopFlights() throws InterruptedException {
		BookingPage objBookingPage = new BookingPage(driver);
		System.out.println("*****************************************************************");
		Assert.assertTrue(objBookingPage.verifyStopsSection(), "Verify Stops Section is present");
		Assert.assertTrue(objBookingPage.verifyDepartureFromSection(), "Verify Departure From Section is present");
		Assert.assertTrue(objBookingPage.verifyAirlinesSection(), "Verify Airline Section is present");
		System.out.println("*****************************************************************");
		System.out.println("Getting Deatils of Departure Flights before applying Non Stop Check Box");
		System.out.println("No of Departure Flights " + objBookingPage.getNoOFDepartureFlights());
		List<String> lsDepartureStops = new ArrayList<String>();
		lsDepartureStops = objBookingPage.getDepartureStopsFromFlights();
		System.out.println("Stop Details of Deaprture flight are listed below");
		System.out.println("*****************************************************************");
		lsDepartureStops.stream().distinct().forEach(System.out::println);
		lsDepartureStops.clear();
		System.out.println("*****************************************************************");
		System.out.println("Getting Deatils of Return Flights before applying Non Stop Check Box");
		System.out.println("No of Return Flights " + objBookingPage.getNoOFReturnFlights());
		List<String> lsReturnStops = new ArrayList<String>();
		lsReturnStops = objBookingPage.getReturnStopsFromFlights();
		System.out.println("Stop Details of Return flight are listed below");
		System.out.println("*****************************************************************");
		lsReturnStops.stream().distinct().forEach(System.out::println);
		lsReturnStops.clear();

		System.out.println("*****************************************************************");
		System.out.println("Checking Non Stop ICON");
		objBookingPage.checkNonStopIcon();
		System.out.println("*****************************************************************");

		System.out.println("Getting Deatils of Departure Flights after applying Non Stop Check Box");
		System.out.println("No of Departure Flights " + objBookingPage.getNoOFDepartureFlights());
		lsDepartureStops = objBookingPage.getDepartureStopsFromFlights();
		System.out.println("Stop Details of Deaprture flights are listed below");
		System.out.println("*****************************************************************");
		lsDepartureStops.stream().distinct().forEach(System.out::println);
		System.out.println("*****************************************************************");
		System.out.println("Getting Deatils of Return Flights after applying Non Stop Check Box");
		System.out.println("No of Return Flights " + objBookingPage.getNoOFReturnFlights());
		lsReturnStops = objBookingPage.getReturnStopsFromFlights();
		System.out.println("Stop Details of Return flights are listed below");
		System.out.println("*****************************************************************");
		lsReturnStops.stream().distinct().forEach(System.out::println);
		System.out.println("*****************************************************************");
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
		System.out.println("*****************************************************************");
		System.out.println("Unchecking Non Stop ICON");
		objBookingPage.checkNonStopIcon();
		System.out.println("*****************************************************************");
	}

	@Test(dependsOnMethods = "verifyNonStopFlights")
	public void verifyFlightsWithFareLessThanFiveThosuand() throws InterruptedException {
		BookingPage objBookingPage = new BookingPage(driver);
		List<String> lsDepartureFares = new ArrayList<String>();
		lsDepartureFares = objBookingPage.getDepartureFareFromFlights();
		System.out.println("Fare Details Details of Deaprture flights are listed below");
		System.out.println("*****************************************************************");
		lsDepartureFares.stream().forEach(System.out::println);
		lsDepartureFares.clear();
		System.out.println("*****************************************************************");

		List<String> lsReturnFares = new ArrayList<String>();
		lsReturnFares = objBookingPage.getReturnFareFromFlights();
		System.out.println("Fare Details Details of Return flights are listed below");
		System.out.println("*****************************************************************");
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
