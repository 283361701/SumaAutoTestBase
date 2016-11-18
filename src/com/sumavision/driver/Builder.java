package com.sumavision.driver;

/**
 * 设备参数模块
 * @author chenxunlie
 * @version 1.0
 * by.2016/11/18
 */
public class Builder {
	
	 String deviceName = InitDriver.deviceName;
	 String platformVersion = InitDriver.platformVersion;
	 String platformName = InitDriver.platformName;
	 String browserName = InitDriver.browserName;
	 String path = System.getProperty("user.dir") + "/src/main/java/apps/";
	 String appPath = InitDriver.appPath;
	 String appPackage = InitDriver.appPackage;
	 String antuLaunch = InitDriver.autuLaunch;
	 String noReset = InitDriver.noReset;
	 String noSign = InitDriver.noSign;
	 String unicodeKeyboard = InitDriver.unicodeKeyboard;
	 String resetKeyboard = InitDriver.resetKeyboard;
	 String appActivity = InitDriver.appActivity;

	 public Builder setDeviceName(String deviceName) {
	    this.deviceName = deviceName;
	    return this;
	 }

	 public Builder setPlatformVersion(String platformVersion) {
	    this.platformVersion = platformVersion;
	    return this;
	 }
	    
	 public Builder setPlatformName(String platformName) {
	    this.platformVersion = platformName;
	    return this;
	 }
	 
	 public Builder setBrowserName(String browserName) {
		 this.browserName = browserName;
		 return this;
	 }

	 public Builder setAppPath(String appPath) {
		 this.appPath = path + appPath;
		 return this;
	 }
	 
	 public Builder setApp(String appPath) {
	    this.appPath = appPath;
	    return this;
	 }

	 public Builder setAppPackage(String appPackage) {
	    this.appPackage = appPackage;
	    return this;
	 }
	 
	 public Builder setAutoLaunch(String antoLaunch) {
		 this.antuLaunch = antoLaunch;
		 return this;
	 }

	 public Builder setNoReset(String noReset) {
	    this.noReset = noReset;
	    return this;
	 }

	 public Builder setNoSign(String noSign) {
	    this.noSign = noSign;
	    return this;
	 }
	   
	 public Builder setUnicodeKeyboard(String unicodeKeyboard) {
	    this.unicodeKeyboard = unicodeKeyboard;
	    return this;
	 }

	 public Builder setResetKeyboard(String resetKeyboard) {
	    this.resetKeyboard = resetKeyboard;
	    return this;
	 }

	 public Builder setAppActivity(String appActivity) {
	    this.appActivity = appActivity;
	    return this;
	 }

	 public InitDriver build() {
	    return new InitDriver(this);
	 }
	    
}
