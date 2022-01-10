package com.qa.democart.test;

import java.util.List;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.qa.democart.listeners.TestAllureListener;
import com.qa.democart.utils.Constants;
import com.qa.democart.utils.ExcelUtil;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;


@Epic("EPIC 101: Design Account page for demo cart application....")
@Story("US 102: Account page with different features")
@Listeners(TestAllureListener.class)   // hack for screenshot if its not showing in report.


public class AccountPageTest extends BaseTest {

	@BeforeClass
	public void accPageSetUp() {
		accPage = loginPage.doLogin(prop.getProperty("username"), prop.getProperty("password"));
	}

	@Description("accPageTitleTest")
	@Severity(SeverityLevel.NORMAL)
	@Test(priority = 1)
	public void accPageTitleTest() {
		String title = accPage.getAccPageTitle();
		System.out.println("acc page title is: " + title);
		Assert.assertEquals(title, Constants.Account_PAGE_Title);
	}

	@Description("accPageHeaderTest")
	@Severity(SeverityLevel.NORMAL)
	@Test(priority = 2)
	public void accPageHeaderTest() {
		String header = accPage.getAccPageHeader();
		System.out.println("acc page header is: " + header);
		Assert.assertEquals(header, Constants.PAGE_HEADER);
	}

	
	@Description("accSectionsListTest")
	@Severity(SeverityLevel.NORMAL)
	@Test(priority = 3)
	public void accSectionsTest() {
		List<String> actAccSecList = accPage.getAccountSectionList();
		System.out.println("actual sections: " + actAccSecList);
		Assert.assertEquals(actAccSecList, Constants.EXPECTED_ACC_SEC_List);
	}
	
	@Description("logout linkExistTest")
	@Severity(SeverityLevel.NORMAL)
	@Test(priority = 4)
	public void logoutLinkExistTest() {
		Assert.assertTrue(accPage.isLogOutLinkExist());
	}
	
	
	@DataProvider
	public Object[][]  getSearchData() {
		return new  Object[][] {
			{"Macbook Pro"}, 
			{"Macbook Air"}, 
			{"Apple"}
			}; // 3 role one colum
	}
	
	
	@Description("searchTest wiht {0}")
	@Severity(SeverityLevel.CRITICAL)
	@Test(priority = 5, dataProvider = "getSearchData")
	public void searchTest(String ProductName){
		resultPage = accPage.doSearch(ProductName);   
		String resultHeader = resultPage.getSearchPageHeader();
		System.out.println("result header is : " + resultHeader);
		Assert.assertTrue(resultHeader.contains(ProductName));		
	}
	
//	@DataProvider
//	public Object[][]  getProductSelectData() {
//		return new  Object[][] {
//			{"Macbook", "MacBook Air"}, //row and colum. row is to search for macboo and colum is to select macbook air
//			{"Macbook", "MacBook Pro"}, 
//			{"Apple", "Apple Cinema 30\""} //search on wed is written as-Apple Cinema 30" .add backslash\ before the quote" 
//			}; // 3 role one colum
//	}
	
	@DataProvider
	public Object[][]  getProductSelectData() {
		return ExcelUtil.getTestData(Constants.PRODUCT_SHEET_NAME);
	}
	
	@Description("selectProductTest with product name: {0} and mainProductName: {1}")
	@Severity(SeverityLevel.CRITICAL)
	@Test(priority = 6, dataProvider = "getProductSelectData")
	public void selectProductTest(String productName, String mainProductName) {
		resultPage = accPage.doSearch(productName); 
		productInfoPage = resultPage.selectProduct(mainProductName);
		String header = productInfoPage.getProductHeaderText();
		System.out.println("product header: " + header);
		Assert.assertEquals(header, mainProductName);  
		
		
		
	}
	
}