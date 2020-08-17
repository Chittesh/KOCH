package koch.com.ixigo;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;

import org.testng.Assert;
import org.testng.annotations.Test;

public class verifyIxigo extends TestEnvironment {

	static DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd MMM, EEE");
	static final String expectedDepartureDate = dtf.format(LocalDateTime.now());

	@Test()
	public void verifyIxigoPageIsLoaded() throws InterruptedException {
		IxigoHomePage objIxigoHomePage = new IxigoHomePage(driver);
		objIxigoHomePage.launchPage();
		Assert.assertTrue(objIxigoHomePage.verifyIxiLogoIsPresent(), "Verify Ixi Logo is present");
		Assert.assertTrue(objIxigoHomePage.verifyFromInputIsPresent(), "Verify From Date Input filed is present");
		Assert.assertEquals(objIxigoHomePage.getDefaultDepartureDate(), expectedDepartureDate,
				"Verify Departure Date Acutual : " + objIxigoHomePage.getDefaultDepartureDate() + " Expected is : "
						+ expectedDepartureDate);
		Assert.assertEquals(objIxigoHomePage.getDefaultDepartureDate(), expectedDepartureDate,
				"Verify Departure Date Acutual : " + objIxigoHomePage.getDefaultDepartureDate() + " Expected is : "
						+ expectedDepartureDate);
		Assert.assertEquals(objIxigoHomePage.getDefaultTravellers(), StaticConstantClass.expectedDefaultTravellers,
				"Verify Default Travllers Acutual : " + objIxigoHomePage.getDefaultTravellers() + " Expected is : "
						+ StaticConstantClass.expectedDefaultTravellers);
		
		SimpleDateFormat sdf = new SimpleDateFormat("ddMMYYYY");
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DAY_OF_MONTH, 1); 
		String tommorowsDate = sdf.format(cal.getTime()); 
		cal.add(Calendar.DAY_OF_MONTH, 6); 
		String dateAfterOneWeek = sdf.format(cal.getTime()); 
		
		objIxigoHomePage.enterTravelDetails(StaticConstantClass.fromLocation,StaticConstantClass.toLocation,tommorowsDate,dateAfterOneWeek);
	}
	
	

}
