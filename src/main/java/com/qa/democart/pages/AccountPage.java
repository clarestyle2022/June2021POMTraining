package com.qa.democart.pages;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.qa.democart.utils.Constants;
import com.qa.democart.utils.ElementUtil;

import io.qameta.allure.Step;

public class AccountPage {

	private WebDriver driver; 
	private ElementUtil elementUtil; 
	
	private By accSections = By.cssSelector("div#content h2");
	private By header = By.cssSelector("div#logo h1 a");
	private By logoutLink = By.linkText("Logout");
	private By searchField = By.name("search");
	private By searchButton = By.cssSelector("div#search button");
	
	public  AccountPage(WebDriver driver) {
		this.driver = driver;
		elementUtil = new ElementUtil(driver);
	}
	
	@Step("getAccPageTile")
	public String getAccPageTitle() {
		return elementUtil.waitForTitleIs(Constants.Account_PAGE_Title, 10);
}
	
	@Step("getAccPageUrl")
	public String getAccPageUrl() {
		return elementUtil.waitForUrlContains(Constants.ACCOUNT_PAGE_URL_FRACTION, 10);
		
	}
	
	@Step("getAccPageHeader")
	public String getAccPageHeader() {
	return 	elementUtil.doGetText(header);
	}
	
	@Step("getAccSectionList")
	  public List<String> getAccountSectionList() {   // or use wait for elementvisibility
	  List<String>accSecValueList = new ArrayList<String>();
	   List<WebElement>accSecList =elementUtil.getElements(accSections); for(WebElement e : accSecList) {
	  accSecValueList.add(e.getText());
	  }
	   //Collections.sort(accSecValueList);
	   return accSecValueList;
	  }
		
	@Step("isLogoutLinkExist")
	public boolean isLogOutLinkExist() {
		return elementUtil.doIsDisplayed(logoutLink);
	}
	   
	@Step("do search with {0}")
	public ResultPage doSearch(String ProductName) {
		System.out.println("searching the product: " + ProductName);
		elementUtil.doSendKeys(searchField, ProductName);
		elementUtil.doClick(searchButton);  
		return new ResultPage(driver); 
	}
	
		
}
