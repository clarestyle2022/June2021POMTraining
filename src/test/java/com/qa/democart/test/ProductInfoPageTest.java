package com.qa.democart.test;

import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class ProductInfoPageTest extends BaseTest {
	
	@BeforeClass
	public void productInfoPageSetUp() {
		accPage = loginPage.doLogin(prop.getProperty("username"), prop.getProperty("password"));
	}
	
@Test
public void productImageTest() {
	resultPage = accPage.doSearch("iMac");
	productInfoPage = resultPage.selectProduct("iMac");
	Assert.assertEquals(productInfoPage.getProductImagesCount(), 3);
	
}
	
@Test
public void productInfoTest() {
	resultPage = accPage.doSearch("Macbook");
	productInfoPage = resultPage.selectProduct("MacBook Air");
	Map<String, String> actProductInfoMap = productInfoPage.getProductInfo();
	
	softAssert.assertEquals(actProductInfoMap.get("name"), "MacBook Pro");
	softAssert.assertEquals(actProductInfoMap.get("Brand"),"Apple");
	softAssert.assertEquals(actProductInfoMap.get("Product Code"),"Product 18");
	softAssert.assertEquals(actProductInfoMap.get("price"), "$2,000.00");
	softAssert.assertAll();   // this wil complile all and tell you test that pass or fail
	//for multiple assertion in a test case use soft assertion. if one test fails the script will stop, wit soft assert all test will run
}

// ideally one test should have one assertion . in canse for multiple sue soft assertion so all can be executed without stopping if all got failed
		
	}

