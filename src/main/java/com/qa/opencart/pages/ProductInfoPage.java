package com.qa.opencart.pages;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.qa.opencart.utils.ElementUtil;
import com.qa.opencart.utils.TimeUtil;

public class ProductInfoPage {

	private WebDriver driver;
	private ElementUtil eleUtil;

	private By productHeader = By.cssSelector("div#content h1");
	private By productImagesCount = By.cssSelector("div#content a.thumbnail");
	private By productMetaData = By.xpath("(//div[@id='content']//ul[@class='list-unstyled'])[1]/li");
	private By productPriceData = By.xpath("(//div[@id='content']//ul[@class='list-unstyled'])[2]/li");

	private Map<String,String> productMap;

	public ProductInfoPage(WebDriver driver) {
		this.driver = driver;
		eleUtil = new ElementUtil(driver);
	}

	public String getProductHeader() {
		String header = eleUtil.doGetText(productHeader);
		System.out.println("product header ===" + header);
		return header;
	}

	public int getProductsImagesCount() {
		int imagesCount = eleUtil.waitForVisibilityOfElementsLocated(productImagesCount, TimeUtil.DEFAULT_MEDIUM_TIME).size();
		System.out.println("total images ===" + imagesCount);
		return imagesCount;
	}

	//This getProductInfoMap has both productData & priceData & headerData & imagescount
	public Map<String, String> getProductInfoMap() {
		productMap = new HashMap<>(); //when we dont to maintain the insertion order

		//productMap = new LinkedHashMap<String,String>(); //when we wanted to maintain the insertion order
		//productMap = new TreeMap<String,String>(); //when we wanted to sort the data in the alphabetical order //sorted Map

		productMap.put("productname", getProductHeader());
		productMap.put("productimagescount", String.valueOf(getProductsImagesCount()));

		getProductData();
		getProductPriceData();
		return productMap;
	}

//	Brand: Apple
//	Product Code: Product 18
//	Reward Points: 800
//	Availability: In Stock ==> These are the MetaData

	private void getProductData() {
		List<WebElement> metaList = eleUtil.getElements(productMetaData);
		for(WebElement e : metaList) {
			String metaData = e.getText();
			String meta[] = metaData.split(":");
			String metaKey = meta[0].trim();
			String metaValue = meta[1].trim();
			productMap.put(metaKey, metaValue);
		}
	}

//	$2,000.00
//	Ex Tax: $2,000.00

	private void getProductPriceData() {
		List<WebElement> priceList = eleUtil.getElements(productPriceData);
		String productPrice = priceList.get(0).getText();
		String exTaxPrice = priceList.get(1).getText().split(":")[1].trim(); //why, split(":")[1] because we need only $2,000.00
		productMap.put("productprice", productPrice); //here, we dont have any key, but still we can give customized key.
		productMap.put("exTaxPrice", exTaxPrice);
	  }
}