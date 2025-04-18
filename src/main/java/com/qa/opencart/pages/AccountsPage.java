package com.qa.opencart.pages;

import java.util.ArrayList;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import com.qa.opencart.constants.AppConstants;
import com.qa.opencart.utils.ElementUtil;
import com.qa.opencart.utils.TimeUtil;

public class AccountsPage {

	private WebDriver driver;
	private ElementUtil eleUtil;

	public AccountsPage(WebDriver driver) {
		this.driver = driver;
		eleUtil = new ElementUtil(driver);
	}

	private By logoutLink = By.linkText("Logout");
	private By headers = By.cssSelector("div#content h2");
	private By search = By.name("search");
	private By searchIcon =  By.cssSelector("div#search button");

	public String getAccountPageTitle() {
		String title = eleUtil.waitForTitleToBe(AppConstants.ACCOUNTS_PAGE_TITLE, TimeUtil.DEFAULT_TIME);
		System.out.println("Accounts page title : " + title);
		return title;
	}

	public String getAccountPageURL() {
		String url =eleUtil.waitForURLContains(AppConstants.ACCOUNT_PAGE_FRACTION_URL, TimeUtil.DEFAULT_TIME);
		System.out.println("Accounts page url : " + url);
		return url;
	}

	public boolean isLogoutLinkExist() {
		return eleUtil.doIsDisplayed(logoutLink);
	}

	public List<String> getAccountPageHeaders() {
		List<WebElement> headersList = eleUtil.waitForVisibilityOfElementsLocated(headers,TimeUtil.DEFAULT_MEDIUM_TIME);

		//List<WebElement> headersList= driver.findElements(headers);
		List<String> headersValList = new ArrayList<>();
		for(WebElement e : headersList) {
			String text = e.getText();
			headersValList.add(text);
		}
		return headersValList;
	}

	public boolean isSearchExist() {
		//return driver.findElement(search).isDisplayed();
		return eleUtil.doIsDisplayed(search);
	}

	public searchResultsPage doSearch(String searchKey) {
		System.out.println("searching for product : " + searchKey);

		if(isSearchExist()) {
			eleUtil.doSendKeys(search, searchKey);
			eleUtil.doClick(searchIcon);

			//driver.findElement(search).sendKeys(searchKey);
			//driver.findElement(searchIcon).click();
			return new searchResultsPage(driver);
		}
		else {
			System.out.println("search key is not present on the page");
			return null;
		}
	}
}