package koch.com.ixigo;

import org.testng.annotations.Test;

public class sampleTestScript extends TestEnvironment {
	@Test
	public void test1() {
		GooglePage obj = new GooglePage(driver);
		driver.get("https://www.google.com/");
		obj.enterValueInTextBox("test");
		obj.clickOnSearchButton();
		obj.clickOnLink();
	}

	@Test
	public void test1_2() {
		GooglePage obj = new GooglePage(driver);
		driver.get("https://www.google.com/");
		obj.enterValueInTextBox("test");
		obj.clickOnSearchButton();
		obj.clickOnLink();
	}

}
