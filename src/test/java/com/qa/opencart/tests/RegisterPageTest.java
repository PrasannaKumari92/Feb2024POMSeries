package com.qa.opencart.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.qa.opencart.base.BaseTest;
import com.qa.opencart.constants.AppConstants;
import com.qa.opencart.errors.AppError;
import com.qa.opencart.utils.CSVUtil;
import com.qa.opencart.utils.ExcelUtil;
import com.qa.opencart.utils.StringUtils;

public class RegisterPageTest extends BaseTest {

	//Its like a pre-condition
	@BeforeClass
	public void regSetup() {
		regPage = loginPage.navigateToRegisterPage();
	}

	@DataProvider
	public Object[][] userRegTestData() {
		return new Object[][] {
			{"ajit", "automation", "7878787878", "ajit@123", "yes"},
			{"praful", "automation", "9999999999", "praful@123", "no"},
			{"madhu", "automation", "8888888888", "madhu@123", "yes"}
		};
	}

	@DataProvider
	public Object[][] userRegTestDataFromSheet() {
		return ExcelUtil.getTestData(AppConstants.REGISTER_SHEET_NAME);
	}

	@DataProvider
	public Object[][] userRegTestDataFromCSV() {
		return CSVUtil.csvData(AppConstants.REGISTER_SHEET_NAME);
	}

	//@Test(dataProvider = "userRegTestData")
	//@Test(dataProvider = "userRegTestDataFromSheet")
	@Test(dataProvider = "userRegTestDataFromCSV")
	public void userRegisterationTest(String firstName, String lastName, String telephone, String password, String subscribe) {

		Assert.assertTrue
		(regPage.userRegister(firstName, lastName, StringUtils.getRandomEmailId(), telephone, password, subscribe),
				AppError.USER_REG_NOT_DONE);

		//using hard code data:
//		Assert.assertTrue
//		(regPage.userRegister("Arti kumari", "testselenium", "artiautomation@gmail.com", "8989098765", "arti@789", "yes"),
//				AppError.USER_REG_NOT_DONE);

	}
}
