package com.qa.democart.factory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariDriver;

import io.github.bonigarcia.wdm.WebDriverManager;

public class DriverFactory {

	public WebDriver driver;
	public static String highlight;
	private OptionsManager optionsManager;
	private Properties prop;
	
	
	public static ThreadLocal<WebDriver> tlDriver = new ThreadLocal<WebDriver>();
	
	public static final Logger log = Logger.getLogger(String.valueOf(DriverFactory.class));
	
	/**
	 * This method is used to initilize the driver
	 * 
	 * @param browserName
	 * @return
	 */
	public WebDriver initDriver(Properties prop) {
		
		String browserName = prop.getProperty("browser");
		String browserVersion = prop.getProperty("browserversion");
		log.info("browser name : " + browserName + " and browserversion: " + browserVersion);
		
		highlight = prop.getProperty("highlight");
		
		System.out.println("browser name is : " + browserName);
		optionsManager = new OptionsManager(prop);
		if (browserName.equalsIgnoreCase("chrome")) {

			if (Boolean.parseBoolean(prop.getProperty("remote"))) {
				log.info("running test on remote");
				init_remoteDriver("chrome", browserVersion);
			} else {
				log.info("running test on local");
				WebDriverManager.chromedriver().setup();
				tlDriver.set(new ChromeDriver(optionsManager.getChormeOptions()));
			}

		}

		else if (browserName.equalsIgnoreCase("firefox")) {
			WebDriverManager.firefoxdriver().setup();

			if (Boolean.parseBoolean(prop.getProperty("remote"))) {
				init_remoteDriver("firefox", browserVersion);
			} else {
				WebDriverManager.firefoxdriver().setup();
				tlDriver.set(new FirefoxDriver(optionsManager.getFirefoxOptions()));
			}

		} else if (browserName.equalsIgnoreCase("safari")) {
			tlDriver.set(new SafariDriver());
		}

		else {
			System.out.println("please pass the right browserName : " + browserName);
		}

		getDriver().manage().deleteAllCookies();
		getDriver().manage().window().maximize();

		getDriver().get(prop.getProperty("url"));

		return getDriver();

	
	}
	
	private void init_remoteDriver(String browserName, String browserVersion) {

		System.out.println("running test on remote with browser: " + browserName + ":" + browserVersion);

		if (browserName.equals("chrome")) {
			DesiredCapabilities caps = DesiredCapabilities.chrome();
			caps.setCapability("browserName", "chrome");
			caps.setCapability("browserVersion", browserVersion);
			caps.setCapability("enableVNC", true);
			caps.setCapability(ChromeOptions.CAPABILITY, optionsManager.getChormeOptions());
			try {
				tlDriver.set(new RemoteWebDriver(new URL(prop.getProperty("huburl")), caps));
			} catch (MalformedURLException e) {
				e.printStackTrace();
			}
		} else if (browserName.equals("firefox")) {
			DesiredCapabilities caps = DesiredCapabilities.firefox();
			caps.setCapability("browserName", "firefox");
			caps.setCapability("browserVersion", browserVersion);
			caps.setCapability("enableVNC", true);
			caps.setCapability(FirefoxOptions.FIREFOX_OPTIONS, optionsManager.getFirefoxOptions());
			try {
				tlDriver.set(new RemoteWebDriver(new URL(prop.getProperty("huburl")), caps));
			} catch (MalformedURLException e) {
				e.printStackTrace();
			}
		}

	}

	public WebDriver getDriver() {
		return tlDriver.get();
	}

	/**
	 * This method is used to initilalize the properties on the basis of given env
	 * name
	 * 
	 * @return
	 */
	public Properties initProperties() {
		prop = null;
		FileInputStream ip = null;

		String env = System.getProperty("env");// mvn clean install -Denv="qa"

		try {
			if (env == null) {
				System.out.println("Running on Environment: PROD env....");
				log.info("Running on Environment: PROD env....");
				ip = new FileInputStream("./src/test/resources/config/config.properties");
			}
			else {
				System.out.println("Running on Environment: " + env);
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
					System.out.println("No ENV found.....");
					throw new Exception("NOENVFOUNDEXCEPTION");

				}
			}

		} catch (FileNotFoundException e) {
			log.error("file not found exception is coming.....");
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
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

	/**
	 * take screenshot
	 */

	public String getScreenshot() {
		File scrFile = ((TakesScreenshot)getDriver()).getScreenshotAs(OutputType.FILE);
		String path = System.getProperty("user dir")+"/screenshot/"+System.currentTimeMillis()+".png"; // userdirectory 
		File destination = new File(path); // moving the screenshot source file to the destinatio file 
		
		try {
			FileUtils.copyFile(scrFile, destination);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return path;
		
	}
	
	
}
