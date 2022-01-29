package com.qa.democart.factory;

import java.util.Properties;

import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;

public class OptionsManager {

	private Properties prop;
	private ChromeOptions co;
	private FirefoxOptions fo;

	public OptionsManager(Properties prop) {
	this.prop = prop;
}

	public ChromeOptions getChormeOptions() {
		co = new ChromeOptions();
		if (Boolean.parseBoolean(prop.getProperty("headless").trim()))
			co.addArguments("headless");
		if (Boolean.parseBoolean(prop.getProperty("incognito").trim()))
			co.addArguments("incognito");
		return co;
	}

	public FirefoxOptions getFirefoxOptions() {
		fo = new FirefoxOptions();
		if (Boolean.parseBoolean(prop.getProperty("headless").trim()))
			co.addArguments("headless");
		if (Boolean.parseBoolean(prop.getProperty("incognito").trim()))
			co.addArguments("incognito");
		return fo;
	}
}
