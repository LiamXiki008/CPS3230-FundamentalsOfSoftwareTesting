package edu.um.cps3230;

//Import statements
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class MyAlertsObject {

    static WebDriver browser;
    By loginButton = By.xpath("//a[@href='/Alerts/Login']"); //Login button
    By logoutButton = By.xpath("//a[@href='/Home/Logout']"); //Logout button
    String API_URL = "https://api.marketalertum.com/Alert";  //API URL
    ApiRequest apiRequest = new ApiRequest(); //Create an instance of the ApiRequest class



    public MyAlertsObject(WebDriver browser){
        MyAlertsObject.browser = browser;
    }

    //Check if logged in
    public boolean loggedIn(){
        boolean loggedIn= browser.findElements(logoutButton).size() != 0;

        return loggedIn;
    }

    //Logout method
//    public void logOut(){
//        ScraperFunc.sleep();(1);
//        browser.findElement(logoutButton).click();
//    }

    //Method used to count the amount of alerts
    public int alertCount(){
        ScraperFunc.sleep(1);
        return browser.findElements(By.xpath("//table")).size();
    }

    public String getAlertIcon(int index){
        ScraperFunc.sleep(1);
        System.out.println(browser.findElement(By.xpath("//table[" + (index+1) + "]//tr[1]//td//h4//img")).getAttribute("src").substring(7));
        return browser.findElement(By.xpath("//img[contains(@src,'icon')]")).getAttribute("src").substring(7);
    }

    public String getAlertHeading(){
        ScraperFunc.sleep(1);
        return browser.findElement(By.xpath("//h4")).getText();
    }

    public String getAlertDescription(){
        ScraperFunc.sleep(1);
        return browser.findElement(By.xpath("//div[@class='container']/main//table//tbody//following::tr[3]/td")).getText();
    }

    public String getAlertImage(){
        ScraperFunc.sleep(1);
        return browser.findElement(By.xpath("//td[@rowspan='4']/img")).getAttribute("src");
    }

    public String getAlertLink(){
        ScraperFunc.sleep(1);
        return browser.findElement(By.linkText("Visit item")).getAttribute("href");
    }

    public String getAlertPrice(){
        ScraperFunc.sleep(1);
        return browser.findElement(By.xpath("//tr[3]")).getText();
    }

    public void reloadPage(){
        browser.navigate().refresh();
    }

    public int uploadAlert (int numberOfAlerts, String jsonAlert, ApiRequest apiRequest) throws Exception{
        //POST request
        int statuscode=0;
        for(int c=0;c<numberOfAlerts;c++) {
           statuscode = apiRequest.postAlert(jsonAlert);
        }
        return statuscode;
    }
}
