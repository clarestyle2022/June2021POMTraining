package com.qa.democart.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.qa.democart.utils.Constants;
import com.qa.democart.utils.ElementUtil;

import io.qameta.allure.Step;

public class LoginPage {

	private WebDriver driver;    // this driver is ao ly for this page calss 
	private ElementUtil elementUtil;
	
	//private By Locators:
	private By emailId  = By.id("input-email");
	private By password = By.id("input-password");
	private By loginButton = By.xpath("//input[@value=\"Login\"]");
	//private By forgotPwdLink = By.xpath("//div[@class ='form-group']//a[text() ='Forgotten Password']");
	private By forgotPwdLink = By.xpath("//div[@class='form-group']//a[text()='Forgotten Password']");
	private By header = By.cssSelector("div#logo h1 a");
	
	private By registerLink = By.linkText("Register");
	
	//constructor:
	public LoginPage (WebDriver driver) { 
		this.driver = driver;
		elementUtil = new ElementUtil(driver);
	}
	
	
	//page actions/page methods/ functionality of beahavious of the page/ no assertion
	@Step("getting login page title...")
	public String getLoginPageTitle() {
		return elementUtil.waitForTitleIs(Constants.LOGIN_PAGE_TITLE, 10);
	}
	
	@Step("getting loging page header...")
	public String getPageHeaderText() {
		return elementUtil.doGetText(header);			
	}
	
	@Step("checking forgot pwd link is dosplayed on login page...")
	public boolean isForgotPwdLinkExist() {
		return elementUtil.doIsDisplayed(forgotPwdLink);
	}
	
	@Step("login to application with username{0} and password {1}..")  //0 uesrsname and 1 password are the numbers of paramter 
	public AccountPage doLogin(String un, String pwd) {
		System.out.println("===========" +un + ":" + pwd +"==========");
		elementUtil.doSendKeys(emailId, un);
		elementUtil.doSendKeys(password, pwd);
		elementUtil.doClick(loginButton); 
		return new AccountPage(driver);   //page chaining	
	}
	
	@Step("navigating to reg page...")
	public RegistrationPage navigateToRegisterPage() {
		elementUtil.doClick(registerLink);
		return new RegistrationPage(driver);
	}
}
