package com.qa.democart.test;

import java.util.Random;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.qa.democart.utils.Constants;
import com.qa.democart.utils.ExcelUtil;

public class RegistrationPageTest extends BaseTest {

	@BeforeClass
	public void regSetup() {
		regPage = loginPage.navigateToRegisterPage();
	}
	
	public String getRandomEmail() {  // to generate automatic email
		Random random = new Random();
		String email = "testautomation"+random.nextInt(5000)+"@gmail.com";
		System.out.println(email);
		return email;
	}

	@DataProvider // 3roll lcolu
	public Object[][] getRegTestData() {
		return ExcelUtil.getTestData(Constants.REGISTER_SHEET_NAME);
	}

	@Test(dataProvider = "getRegTestData")
	public void registrationTest(String firstName, String lastName, String telephone, String password,
			 String subscribe) {
		Assert.assertTrue(
				regPage.accountRegistration(firstName, lastName, 
						getRandomEmail(), telephone, password, subscribe)
				);
	}

}