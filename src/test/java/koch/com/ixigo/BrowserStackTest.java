package koch.com.ixigo;

import static org.testng.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class BrowserStackTest extends TestEnvironment {
	@Test
	public void verifyWebsiteListOnHomePage() throws InterruptedException {
		BrowserStackPage obj = new BrowserStackPage(driver);
		obj.launchPage();
		List<String> objActualListFromUI = new ArrayList<String>();
		objActualListFromUI = obj.getValuesFromHeader();
		String expectedOutput[] = new String[] { "Live", "Automate", "Percy", "App Live", "App Automate" };
		List<String> expectedList = Arrays.asList(expectedOutput);
		assertEquals(objActualListFromUI, expectedList,
				"Verify Headers Actual : " + objActualListFromUI + " Expected : " + expectedList);
	}

}
