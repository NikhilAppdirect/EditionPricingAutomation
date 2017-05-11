package com.appdirect.microsoft.pages;

import org.openqa.selenium.WebDriver;
import java.util.Properties;
import org.openqa.selenium.WebElement;
import com.appdirect.microsoft.utils.DriverUtils;
import com.appdirect.microsoft.utils.Reporterlog;
public class LoginPage {
    private WebDriver driver;
    private Reporterlog log;
    private Properties props;
    private WebElement loginButton, enterEmail, enterPass;
    public LoginPage(WebDriver driver) {
        this.driver = driver;
        this.log = new Reporterlog();
        props = new DriverUtils(driver).getProperty("resources/res-pages.properties");
    }


    /*
     * Function to transit from Login Page to Home page
     */
    public HomePage gotoHome() {
        enterEmail = new DriverUtils(this.driver).getElement(props.getProperty("enterEmail"));
        enterEmail.sendKeys(props.getProperty("loginEmail"));
        enterPass = new DriverUtils(this.driver).getElement(props.getProperty("enterPass"));
        enterPass.sendKeys(props.getProperty("loginPass"));
        loginButton = new DriverUtils(this.driver).getElement(props.getProperty("login"));
        this.loginButton.click();
        log.info("Clicked Log In button on Login Page");
        return new HomePage(this.driver);
    }
}

