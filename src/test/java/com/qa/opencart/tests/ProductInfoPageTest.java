package com.qa.opencart.tests;

import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.qa.opencart.base.BaseTest;
import com.qa.opencart.constants.AppConstants;
import com.qa.opencart.errors.AppError;
import com.qa.opencart.utils.ExcelUtil;

public class ProductInfoPageTest extends BaseTest {

	//Its own precondition
	@BeforeClass
	public void productInfoPageSetup() {
		accountPage = loginPage.doLogin(prop.getProperty("username"), prop.getProperty("password"));
	}

	//Only 2 columns, because the Product Name and the Header Name are same in the application.
	//The user can search for any product in the search.
	@DataProvider
	public Object[][] getProductData(){
		return new Object[][] {
			{"macbook", "MacBook Pro"},
			{"imac", "iMac"},
			{"samsung", "Samsung SyncMaster 941BW"},
			{"samsung", "Samsung Galaxy Tab 10.1"},
			{"canon", "Canon EOS 5D"} //This is the expected data
		};
	}

	@Test(dataProvider="getProductData")
	public void productHeaderTest(String searchKey, String productName) {
		searchResultsPage = accountPage.doSearch(searchKey);
		//ProductInfoPage = searchResultsPage.selectProduct("MacBook Pro"); Hardcoded value
		ProductInfoPage = searchResultsPage.selectProduct(productName);
		Assert.assertEquals(ProductInfoPage.getProductHeader(),productName, AppError.HEADER_NOT_FOUND);
	}


	@DataProvider
	public Object[][] getProductImageData(){
		return new Object[][] {
			{"macbook", "MacBook Pro", 4},
			{"imac", "iMac", 3},
			{"samsung", "Samsung SyncMaster 941BW", 1},
			{"samsung", "Samsung Galaxy Tab 10.1",7},
			{"canon", "Canon EOS 5D", 3}
		};
	}

	//Data Coming From Excel Sheet
	@DataProvider
	public Object[][] getProductImageSheetData(){
		return ExcelUtil.getTestData(AppConstants.PRODUCT_IMAGES_SHEET_NAME);
	}

	
	//@Test(dataProvider="getProductImageSheetData")
	@Test(dataProvider="getProductImageData")
	public void productImagesCountTest(String searchKey, String productName, int imagesCount) {
		searchResultsPage = accountPage.doSearch(searchKey);
		ProductInfoPage = searchResultsPage.selectProduct(productName);
		Assert.assertEquals(ProductInfoPage.getProductsImagesCount(), imagesCount , AppError.IMAGES_COUNT_MISMATCHED);
	}

	//public void productImagesCountTest(String searchKey, String productName, int imageCount), earlier we have passed
	//when testing the data using the dataprovider.
	//Now, we have passing the data from excel, it will consider as String,
	//So, declaring the param as String and converting to integer using parseInt. Remember IMP.

	//test with multiple assertions(soft assert)

	@Test
	public void productInfoTest() {
		searchResultsPage = accountPage.doSearch("macbook");
		ProductInfoPage = searchResultsPage.selectProduct("MacBook Pro");
		Map<String, String> productInfoMap = ProductInfoPage.getProductInfoMap();
		System.out.println("========product information===========");
		System.out.println(productInfoMap);

		//not a good practice to use Hard Assertion when we have multiple assertions like below ex

//		Assert.assertEquals(productInfoMap.get("productname"), "MacBook Pro");
//		Assert.assertEquals(productInfoMap.get("Brand"), "Apple");
//		Assert.assertEquals(productInfoMap.get("Product Code"), "Product 18");
//		Assert.assertEquals(productInfoMap.get("Reward Points"), "800");
//		Assert.assertEquals(productInfoMap.get("Availability"), "In Stock");
//		Assert.assertEquals(productInfoMap.get("productprice"), "$2,000.00");
//		Assert.assertEquals(productInfoMap.get("exTaxPrice"), "$2,000.00");

		softAssert.assertEquals(productInfoMap.get("productname"), "MacBook Pro");
		softAssert.assertEquals(productInfoMap.get("Brand"), "Apple");
		softAssert.assertEquals(productInfoMap.get("Product Code"), "Product 18");
		softAssert.assertEquals(productInfoMap.get("Reward Points"), "800");
		softAssert.assertEquals(productInfoMap.get("Availability"), "Out Of Stock");
		softAssert.assertEquals(productInfoMap.get("productprice"), "$2,000.00");
		softAssert.assertEquals(productInfoMap.get("exTaxPrice"), "$2,000.00");

		softAssert.assertAll(); //Failure Info(1) 1 is the assertion failed, if there is two assertions failures it will show as 2
	}
}
     //Assert vs verify
    //hard assert(Assert) vs soft Assert(verify - softAssert)
	//Assert --> methods (static)
	//SoftAssert --> methods (non static)

//1.what is the best practise of using Assertions?
//Ans: A single test must have only one assertion.

//when it is having multiple Assertions, its a good practice to use Soft Assertion, instead of hard assertion
// because, in hard assertion, if any of the assert fails, the test will be terminated instead of going to next assert.
// when we are using the soft assertion, it will go to next assert.
// Ans its mandatory to write in the end softAssert.assertAll() stmt, which will give the Failure Info()

// single assertion --> hard assertion
// multiple assertio  --> soft assertion

//Q: When to use which Assertions?

//Ans:- act vs exp --> Assert.assertEquals(act,exp);
// Assert.assertTrue(10>5);
// Assert.assertTrue(title.contains("Google")); ====> +ve Scenarios

// Assert.assertFalse(false); ====> -ve Scenarios
// Assert.assertFalse(5>10);

