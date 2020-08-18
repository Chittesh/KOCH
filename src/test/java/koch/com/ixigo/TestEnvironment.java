package koch.com.ixigo;

import java.util.concurrent.TimeUnit;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;

public class TestEnvironment {
	static WebDriver driver;

	@BeforeTest(alwaysRun = true)
	public synchronized void driverSetup() {
		String path = System.getProperty("user.dir") + "\\src\\main\\resources\\chromedriver.exe";
		System.setProperty("webdriver.chrome.driver", path);

		ChromeOptions options = new ChromeOptions();
		options.addArguments("--incognito");
		options.addArguments("--disable-popup-blocking");
		options.addArguments("test-type");
		options.addArguments("--disable-notifications");

		DesiredCapabilities capabilities = DesiredCapabilities.chrome();
		capabilities.setCapability(ChromeOptions.CAPABILITY, options);
		driver = new ChromeDriver(capabilities);
		driver.manage().timeouts().implicitlyWait(35, TimeUnit.SECONDS);
		driver.manage().window().maximize();
	}

	@AfterTest(alwaysRun = true)
	public void driverClose() {
		driver.quit();
	}

}
