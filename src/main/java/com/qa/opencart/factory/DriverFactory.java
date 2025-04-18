package com.qa.opencart.factory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.io.FileHandler;
import org.openqa.selenium.safari.SafariDriver;
import com.qa.opencart.constants.AppConstants;
import com.qa.opencart.errors.AppError;
import com.qa.opencart.exceptions.BrowserException;
import com.qa.opencart.exceptions.FrameworkException;
	
public class DriverFactory {

	WebDriver driver;
	Properties prop;
	OptionsManager optionsManager;
	
	public static String highlight;

	//ThreadLocal is a Class and we can pass the Class or Interface.ThreadLocal is coming from Java.
	//After it got implemented by JDK 8.

	public static ThreadLocal<WebDriver> tlDriver = new ThreadLocal<>();


	/**
	 * This is used to init the driver on the basis on given browser name.
	 * @param browserName
	 */
	public WebDriver initDriver(Properties prop) { //call by reference for Properties prop example.

		String browserName = prop.getProperty("browser");
		System.out.println("browser name is : " + browserName);
		
		highlight = prop.getProperty("highlight");
		
		optionsManager = new OptionsManager(prop);

		switch(browserName.toLowerCase().trim()) {
		case "chrome" :
			  //driver = new ChromeDriver();
			  tlDriver.set(new ChromeDriver(optionsManager.getChromeOptions()));
			  break;
		case "firefox" :
			  //driver = new FirefoxDriver();
			  tlDriver.set(new FirefoxDriver(optionsManager.getFirefoxOptions()));
			  break;
	    case "edge" :
			  //driver = new EdgeDriver();
	    	  tlDriver.set(new EdgeDriver(optionsManager.getEdgeOptions()));
			  break;
		case "safari" :
			  //driver = new SafariDriver();
			  tlDriver.set(new SafariDriver());
			  break;

		default:
			System.out.println("plz pass the right browser name..." + browserName);
			throw new BrowserException(AppError.BROWSER_NOT_FOUND);
		}

//		driver.manage().deleteAllCookies(); Here, we are still calling the normal driver, Inorder to call the threal local driver.
//		driver.manage().window().maximize();
//		driver.get(prop.getProperty("url")); we have to update it, getDriver.manage().deleteAllCookies();
//
//		return driver;

		getDriver().manage().deleteAllCookies();
		getDriver().manage().window().maximize();
		getDriver().get(prop.getProperty("url"));

        return getDriver();
	}

	/**
	 * get the thread local copy of the driver
	 * @return
	 */


	public static WebDriver getDriver() {
		return tlDriver.get();
	}


	/**
	 * This is used to init the properties from the properties file
	 * @return this returns properties(prop)
	 */

	public Properties initProp()  {

		prop = new Properties();
		FileInputStream ip = null;

		//mvn clean install -Denv="qa"
		String envName = System.getProperty("env");
		System.out.println("running test suite on env--->" + envName);

		//what if when we are not passing any env name: just only mvn clean install
		if(envName == null) {
			System.out.println("env name is not given, hence running it on QA environment....");
			try{
				ip = new FileInputStream(AppConstants.CONFIG_QA_FILE_PATH);
			}
			catch (FileNotFoundException e)	{
				e.printStackTrace();
			}
		}
		else {
		try {
		switch(envName.trim().toLowerCase()) {
		case "qa" :
			ip = new FileInputStream(AppConstants.CONFIG_QA_FILE_PATH);
			break;

		case "stage" :
			ip = new FileInputStream(AppConstants.CONFIG_STAGE_FILE_PATH);
			break;

	    case "dev" :
		    ip = new FileInputStream(AppConstants.CONFIG_DEV_FILE_PATH);
		    break;

        case "uat" :
	        ip = new FileInputStream(AppConstants.CONFIG_UAT_FILE_PATH);
	        break;

        case "prod" :
	        ip = new FileInputStream(AppConstants.CONFIG_FILE_PATH);
	        break;

	     default:
	    	 System.out.println("please pass the right env name..." + envName);
	    	 throw new FrameworkException("WRONG ENV PASSED");
        }
		}
		catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		}

		try {
			prop.load(ip);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return prop;
  }
	
	/**
	 * take screenshot on failure of the testcase
	 */
	
	public static String getScreenshot(String methodName) {
	
		//when executing below code, it will create the screenshot in temporary location like c drive or any drive.
		//which, i dont want to happen so that i am creating the screeshots Folder in this Project.
		
		File srcFile = ((TakesScreenshot) getDriver()).getScreenshotAs(OutputType.FILE);
		
		String path = System.getProperty("user.dir")+"/screenshots/"+methodName+"_"+System.currentTimeMillis()+".png";
		
		File destination = new File(path);
		
		try {
			FileHandler.copy(srcFile, destination);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return path;
	}
}



//without using maven, when you have single env to run the test cases.
//try {
//	FileInputStream ip = new FileInputStream(AppConstants.CONFIG_FILE_PATH);
//	prop.load(ip);
//} catch (FileNotFoundException e) {
//	e.printStackTrace();
//} catch (IOException e) {
//	e.printStackTrace();
//}
//return prop;