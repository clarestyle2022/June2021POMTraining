package com.qa.democart.factory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.safari.SafariDriver;

import io.github.bonigarcia.wdm.WebDriverManager;

public class DriverFactory {

	public WebDriver driver;
	public static String highlight;
	private OptionsManager optionsManager;
	private Properties prop;
	
	
	public static ThreadLocal<WebDriver> tlDriver = new ThreadLocal<WebDriver>();
	//webdriver should be threadlocalised so we write it like this. we use set and get method
	// theredlocal is webdriver
	/**
	 * This method is used to initilize the driver 
	 * @param browserName
	 * @return
	 */
	public WebDriver initDriver(Properties prop) {
		
		String browserName = prop.getProperty("browser");
		highlight = prop.getProperty("highlight");
		
		System.out.println("browser name is : " + browserName);
		optionsManager = new OptionsManager(prop);
		if (browserName.equalsIgnoreCase("chrome")) {
			WebDriverManager.chromedriver().setup();
			//driver = new ChromeDriver(optionsManager.getChormeOptions());
			tlDriver.set(new ChromeDriver(optionsManager.getChormeOptions()));  // you will get a copy of the dtiver with the threadlocal
		}

		else if (browserName.equalsIgnoreCase("firefox")) {
			WebDriverManager.firefoxdriver().setup();
			//driver = new FirefoxDriver(optionsManager.getFirefoxOptions());
			tlDriver.set(new FirefoxDriver(optionsManager.getFirefoxOptions()));
		}

		else if (browserName.equalsIgnoreCase("safari")) {
			//driver = new SafariDriver();
			tlDriver.set(new SafariDriver());	
		}

		else {
			System.out.println("please pass the right browserName :" + browserName);
		}

		getDriver().manage().deleteAllCookies();
		getDriver().manage().window().maximize();
		getDriver().get(prop.getProperty("url"));
		
		return getDriver();
	}
	
	public WebDriver getDriver() {
		return tlDriver.get();
	}
	/**
	 * This method is used to initilize the properties on the basis of given environment name
	 * @return
	 */
	public Properties initProperties() {
		Properties prop = null;
		FileInputStream ip = null;
		
		String env = System.getProperty("env"); // mvn clean install -Devn ="qa"
		try {
		if(env==null) {
			System.out.println("Running on Environment: PROD env...");
		  ip = new FileInputStream("./src/test/resources/config/config.propertiesfile");
	
		}
		
		else {
			System.out.println("running on Environment: " +env);
			
			switch (env) {
			case "qa":
				ip = new FileInputStream("./src/test/resources/config/qa.config.properties");
				break;
			case "dev":
				ip = new FileInputStream("./src/test/resources/config/dev.config.properties");
				break;
			case "stage":
				ip = new FileInputStream("./src/test/resources/config/stage.config.properties");
				break;
				
				default:
					System.out.println("NO ENV found...");
					throw new Exception("NOENVFOUNDEXCEPTION");  //customeised exception (shown is comman prompt)
			}
		}
		
		 } catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	
		try {   
		prop = new Properties();
		prop.load(ip);
		
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return prop;
		}
	
		/* take screenshot */
	public String getScreenshot() {
		File scrFile = ((TakesScreenshot)getDriver()).getScreenshotAs(OutputType.FILE);
		String path = System.getProperty("user.dir")+"/screenshot/"+System.currentTimeMillis()+".png"; // user.dir isuserdirectory 
		File destination = new File(path); // moving the screenshot source file to the destinatio file 
		
		try {
			FileUtils.copyFile(scrFile, destination);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return path;
		
	}
	
	
}
