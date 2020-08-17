package koch.com.ixigo;

import java.util.concurrent.TimeUnit;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

public class TestEnvironment {
	static WebDriver driver;

	@BeforeMethod(alwaysRun = true)
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
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.manage().window().maximize();
	}

	@AfterMethod(alwaysRun = true)
	public void driverClose() {
		driver.quit();
	}

}
