package com.qa.opencart.base;

import java.util.Properties;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.asserts.SoftAssert;
import com.qa.opencart.factory.DriverFactory;
import com.qa.opencart.pages.AccountsPage;
import com.qa.opencart.pages.LoginPage;
import com.qa.opencart.pages.ProductInfoPage;
import com.qa.opencart.pages.RegisterPage;
import com.qa.opencart.pages.searchResultsPage;
import io.qameta.allure.Description;
import io.qameta.allure.Step;

public class BaseTest {

	//BaseTest extends AppConstants --> No, BaseTest is not a AppConstants class to extend.

	DriverFactory df;
	WebDriver driver;
	protected Properties prop;
	protected LoginPage loginPage;
	protected AccountsPage accountPage;
	protected searchResultsPage searchResultsPage;
	protected ProductInfoPage ProductInfoPage;
	protected RegisterPage regPage;

	protected SoftAssert softAssert;

	@Step("setup for the test, inittializing browser: {0}")
	@Parameters({"browser"})
	@BeforeTest
	//public void setUp(@Optional("chrome") String browserName)
	public void setUp(String browserName) {
		df = new DriverFactory();
		prop = df.initProp();

		  //updating the browser on the fly
		  //testng.xml, browser param is passed to basetest and update the config file browser file
		  if(browserName!=null) {
			  prop.setProperty("browser", browserName);
		  }

		driver = df.initDriver(prop);
		loginPage = new LoginPage(driver);
		softAssert = new SoftAssert();
		//loginPage object is created here,
		//because main thing is login after login only, we navigate to the account page or products page.
		//every flow is via login.
		//starting point in the application is login, so created the object of loginpage.
	}
	
	@Step("close the browser...")
	@AfterTest
	public void tearDown() {
		driver.quit();
	}
}