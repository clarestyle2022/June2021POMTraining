package com.qa.democart.test;

import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.qa.democart.listeners.TestAllureListener;
import com.qa.democart.pages.AccountPage;
import com.qa.democart.utils.Constants;
import com.qa.democart.utils.Errors;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;

@Epic("EPIC 100: Design Login page for demo cart application....")
@Story("US 101: Login page with different features")
@Listeners(TestAllureListener.class)

public class LoginPageTest extends BaseTest {
	

		@Description("login page title test...")   //@discription is from allure libary
		@Severity(SeverityLevel.NORMAL)
		@Test(priority = 1)
		public void loginPageTitleTest() {
			String title = loginPage.getLoginPageTitle();
			System.out.println("loin page tile is: " + title);
			Assert.assertEquals(title, Constants.LOGIN_PAGE_TITLE, Errors.TITLE_ERROR_MESSG);
		}
		
		@Description("login page header test...")
		@Severity(SeverityLevel.NORMAL)
		@Test(priority = 2)
		public void loginPageHeaderTest() {
			String header = loginPage.getPageHeaderText();
			System.out.println("login page header is: " + header);
			Assert.assertEquals(header, Constants.PAGE_HEADER, Errors.HEADER_ERROR_MESSG);
		}
		
		@Description("forgot paw link test...")
		@Severity(SeverityLevel.CRITICAL)
		@Test(priority = 3)
		public void forgotPwdTest() {
			Assert.assertTrue(loginPage.isForgotPwdLinkExist());
		}
		
		@Description("login page test...")
		@Severity(SeverityLevel.NORMAL)
		@Test(priority = 4)
		public void loginTest() {
			AccountPage accPage = loginPage.doLogin(prop.getProperty("username").trim(), prop.getProperty("password"));  //trim if space is given
			//AccountPage accPage = loginPage.doLogin(System.getProperty("username"), System.getProperty("password"));  // username and password will be su
			Assert.assertTrue(accPage.isLogOutLinkExist());
		}
		

		}
	

