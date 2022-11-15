package edu.um.cps3230;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;


public class LoginPageObject{

    static WebDriver browser;
    By loginButton = By.xpath("//a[@href='/Alerts/Login']");
    By userId = By.id("UserId");


    public LoginPageObject(WebDriver browser){
        LoginPageObject.browser = browser;
    }

    public void logIn(String uniqueId){
        ScraperFunc.sleep(1);
        browser.findElement(loginButton).click();
        ScraperFunc.sleep(1);
        browser.findElement(userId).sendKeys(uniqueId);
        ScraperFunc.sleep(2);
        browser.findElement(userId).submit();
    }

}
