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

public class google extends TestEnvironment {

	static DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd MMM, EEE");
	static final String expectedDepartureDate = dtf.format(LocalDateTime.now());
	static String fromDate;
	static String toDate;

	@Test()
	public void verifyIxigoPageIsLoaded() throws InterruptedException {

		IxigoHomePage objIxigoHomePage = new IxigoHomePage(driver);
		objIxigoHomePage.launchPage();

	}

	
}
