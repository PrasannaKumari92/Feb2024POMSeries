package com.qa.opencart.tests;

import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import com.qa.opencart.base.BaseTest;
import com.qa.opencart.constants.AppConstants;
import com.qa.opencart.errors.AppError;
import com.qa.opencart.listeners.AnnotationTransformer;
import com.qa.opencart.listeners.ExtentReportListener;
import com.qa.opencart.listeners.TestAllureListener;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Issue;
import io.qameta.allure.Owner;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;

@Epic("Epic 100: Design Open Cart Application with Shopping Workflow")
@Story("US 101: Design Login page for Open Cart Application")
@Feature("F50: Feature login page")
//@Listeners({ExtentReportListener.class,TestAllureListener.class,AnnotationTransformer.class}),Ideally we wont use it here, 
//  only in testng suite.xml
//--only report will work, retry is not working here
public class LoginPageTest extends BaseTest {

	//LoginPageTest is a BaseTest class and is a Releastionship
	//So it should extend only one parent class.

	//This Description is coming from allure.
	@Description("checking login page title ---")
	@Severity(SeverityLevel.MINOR)
	@Owner("Prasanna Kumari")
	@Issue("Login-123")
	@Feature("login page title features")
	@Test(priority=1)
	public void loginPageTitleTest() {
		String actTitle = loginPage.getLoginPageTitle();
		Assert.assertEquals(actTitle, AppConstants.LOGIN_PAGE_TITLE, AppError.TITLE_NOT_FOUND);
	}

	@Description("checking login page url ---")
	@Severity(SeverityLevel.NORMAL)
	@Owner("Naveen Automation Labs")
	@Feature("login page URL features")
	@Test(priority=2)
	public void loginPageURLTest() {
		String actURL = loginPage.getLoginPageURL();
		Assert.assertTrue(actURL.contains(AppConstants.LOGIN_PAGE_FRACTION_URL),AppError.URL_NOT_FOUND);
	}

	@Description("checking forgot pwd link exist on the login page ---")
	@Severity(SeverityLevel.CRITICAL)
	@Owner("Naveen Automation Labs")
	@Feature("login page forgot pwd features")
	@Test(priority=3)
	public void forgotPwdLinkExistTest() {
		Assert.assertTrue(loginPage.checkForgotPwdLinkExist(),AppError.ELEMENT_NOT_FOUND);
	}

	@Description("checking user is able to login successfully ---")
	@Severity(SeverityLevel.BLOCKER)
	@Owner("Naveen Automation Labs")
	@Feature("login page features")
	@Test(priority=4)
	public void loginTest() {
		//Here, we are using the below commented code when we dont want to expose the password in the config file
		//accountPage = loginPage.doLogin(prop.getProperty("username"),System.getProperty("password"));

        accountPage = loginPage.doLogin(prop.getProperty("username"),prop.getProperty("password"));
        Assert.assertEquals(accountPage.getAccountPageTitle(),AppConstants.ACCOUNTS_PAGE_TITLE,AppError.TITLE_NOT_FOUND);

	}
}