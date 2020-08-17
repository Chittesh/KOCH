package koch.com.ixigo;

import org.testng.Assert;
import org.testng.annotations.Test;

public class verifyIxigo extends TestEnvironment {

	@Test()
	public void verifyIxigoPageIsLoaded() {
		IxigoHomePage objIxigoHomePage = new IxigoHomePage(driver);
		objIxigoHomePage.launchPage();
		Assert.assertTrue(objIxigoHomePage.verifyFromInputIsPresent(),"Verify From Input is present");
	}

}
