package com.qa.democart.test;

import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.asserts.SoftAssert;

import com.qa.democart.factory.DriverFactory;
import com.qa.democart.pages.AccountPage;
import com.qa.democart.pages.LoginPage;
import com.qa.democart.pages.ProductInfoPage;
import com.qa.democart.pages.RegistrationPage;
import com.qa.democart.pages.ResultPage;

public class BaseTest {

	WebDriver driver;
	Properties prop;
	DriverFactory df;    // costructor of driverFactory
	
	SoftAssert softAssert;
	
	LoginPage loginPage;  // comstructor of LoginPage(class opject)
	AccountPage accPage;
	ResultPage resultPage;
	ProductInfoPage productInfoPage;
	RegistrationPage regPage;
	
	
	@Parameters({"browser", "browserversion"})   //coming from testng xml file forselenoid
	@BeforeTest
	public void SetUp(String browser, String browserVersion) {
		softAssert = new SoftAssert();
		df = new DriverFactory();
		prop = df.initProperties();
		
		if(browser!=null) {
			prop.setProperty("browser", browser);
			prop.setProperty("browserversion", browserVersion);
		}
		
		driver = df.initDriver(prop);  //need driver that is defined in all methods 
	    loginPage = new LoginPage(driver);  // need driver for Loininpage
	}
	
	@AfterTest
	public void tearDown() { 
		driver.quit(); 
		}

}
