package koch.com.ixigo;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.BrowserType;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.ITestContext;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;

import com.beust.jcommander.Parameter;

public class TestEnvironment {
	static WebDriver driver;

	@BeforeTest(alwaysRun = true)
	@Parameters({ "runLocation", "browserName" })
	public synchronized void driverSetup(String runLocation, String browserName, ITestContext ctx)
			throws MalformedURLException {

		if (runLocation.contains("local")) {
			String path = System.getProperty("user.dir") + "\\src\\main\\resources\\chromedriver.exe";
			System.setProperty("webdriver.chrome.driver", path);
			ChromeOptions options = new ChromeOptions();
			options.addArguments("--incognito");
			options.addArguments("--disable-popup-blocking");
			options.addArguments("test-type");
			DesiredCapabilities capabilities = DesiredCapabilities.chrome();
			capabilities.setCapability(ChromeOptions.CAPABILITY, options);
			driver = new ChromeDriver(capabilities);
			driver.manage().timeouts().implicitlyWait(35, TimeUnit.SECONDS);
			driver.manage().window().maximize();
		} else if (runLocation.contains("grid")) {
			// To Get Name From testng xml file
			String testName = ctx.getCurrentXmlTest().getName();

			DesiredCapabilities capabilities = null;
			if (browserName.contains("chrome")) {
				capabilities = DesiredCapabilities.chrome();
			} else if (browserName.contains("firefox")) {
				capabilities = DesiredCapabilities.firefox();
			}

			String host = "localhost";
			if (System.getProperty("HUB_HOST") != null) {
				host = System.getProperty("HUB_HOST");
			}

			String url = "http://" + host + ":4444/wd/hub";
			URL objURL = new URL(url);
			driver = new RemoteWebDriver(objURL, capabilities);
		} else if (runLocation.contains("cloud")) {
			// To Get Name From testng xml file
			String testName = ctx.getCurrentXmlTest().getName();

			String userName = "chittesh";
			String password = "Zalenium2021";
			String ipAddress = "35.225.229.145";
			String remoteUrl = "http://" + userName + ":" + password + "@" + ipAddress + "/wd/hub";
			System.out.println("Zalenium hub url is : " + remoteUrl);
			URL objURL = new URL(remoteUrl);

			ChromeOptions options = new ChromeOptions();
			Map<String, Object> prefs = new HashMap<String, Object>();
			Map<String, Object> profile = new HashMap<String, Object>();
			Map<String, Object> contentSettings = new HashMap<String, Object>();

			// 0 - Default, 1 - Allow, 2 - Block
			contentSettings.put("geolocation", 1);
			profile.put("managed_default_content_settings", contentSettings);
			prefs.put("profile", profile);
			options.setExperimentalOption("prefs", prefs);

			DesiredCapabilities capabilities = new DesiredCapabilities();
			capabilities.setCapability("name", testName);
			capabilities.setCapability(CapabilityType.PLATFORM_NAME, Platform.LINUX);
			capabilities.setCapability("build", 111.11);
			capabilities.setCapability("idleTimeout", 180);
			capabilities.setCapability("recordVideo", true);
			capabilities.setCapability("tz", "Asia/Kolkata");

			if (browserName.contains("chrome")) {
				capabilities.setCapability(CapabilityType.BROWSER_NAME, BrowserType.CHROME);
				capabilities.setCapability(ChromeOptions.CAPABILITY, options);
			} else if (browserName.contains("firefox")) {
				capabilities.setCapability(CapabilityType.BROWSER_NAME, BrowserType.FIREFOX);
			}

			driver = new RemoteWebDriver(objURL, capabilities);

		}

	}

	@AfterSuite(alwaysRun = true)
	public void driverClose() {
		driver.quit();
	}

}
