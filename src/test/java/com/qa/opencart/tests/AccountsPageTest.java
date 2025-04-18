package com.qa.opencart.tests;

import java.util.List;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.qa.opencart.base.BaseTest;
import com.qa.opencart.constants.AppConstants;
import com.qa.opencart.errors.AppError;

public class AccountsPageTest extends BaseTest {

	@BeforeClass
	public void accountSetUp() {
		accountPage = loginPage.doLogin(prop.getProperty("username"), prop.getProperty("password"));
	}

	@Test
	public void accountPageTitleTest() {
		Assert.assertEquals(accountPage.getAccountPageTitle(), AppConstants.ACCOUNTS_PAGE_TITLE, AppError.TITLE_NOT_FOUND);
	}

	@Test
	public void accountPageURLTest() {
		Assert.assertTrue(accountPage.getAccountPageURL().contains(AppConstants.ACCOUNT_PAGE_FRACTION_URL), AppError.URL_NOT_FOUND);
	}

	@Test
	public void accountPageHeadersTest() {
		List<String> accountPageHeadersList = accountPage.getAccountPageHeaders();
		Assert.assertEquals(accountPageHeadersList, AppConstants.ACC_PAGE_HEADERS_LIST, AppError.LIST_IS_NOT_MATCHED);
	}

	@DataProvider
	public Object[][] getSearchData() {
		return new Object[][] {
			{"macbook", 3},
			{"imac", 1},
			{"samsung", 2},
			{"Airtel", 0}
		};
	}

	@Test(dataProvider = "getSearchData")
	public void searchTest(String searchKey, int resultsCount) {
		searchResultsPage = accountPage.doSearch(searchKey);
		Assert.assertEquals(searchResultsPage.getSearchResultsCount(),resultsCount, AppError.RESULTS_COUNT_MISMATCHED);

	}
}