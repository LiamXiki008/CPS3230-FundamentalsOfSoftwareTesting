package edu.um.cps3230;

import com.google.gson.Gson;
import edu.um.cps3230.testdoubles.Alert;
import edu.um.cps3230.testdoubles.Api;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.jupiter.api.AfterEach;
import org.mockito.Mockito;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class MarketAlertUmSteps {

    protected static WebDriver browser;
    protected MyAlertsObject myAlertsObject;
    protected  LoginPageObject loginPageObject;
      String validLogin = "aba2df1c-5441-4581-9dc2-5413c9691825";
    protected ApiRequest apiRequest;
    String testAlertJson;
    Api api;

    Alert test_cart_alert = new Alert(1,"VW Polo","VW Polo 1.0 TSI 95PS 5dr","Test url","https://cdni.autocarindia.com/Utils/ImageResizer.ashx?n=https://cdni.autocarindia.com/ExtraImages/20200527092135_2020-VW-Polo-1.0-TSI-rear-static.jpg&w=726&h=482&q=75&c=1","aba2df1c-5441-4581-9dc2-5413c9691825", 200000);;
    Alert test_boat_alert = new Alert(2,"Boat","Boat Airdopes 441 TWS Earbuds","Test url","https://img.yachtall.com/image-sale-boat/sunseeker-portofino-53-list-182350acfa955d79b.jpg","aba2df1c-5441-4581-9dc2-5413c9691825", 200000);;
    Alert test_rent_alert = new Alert(3, "Rent", "Rent a house in Sliema", "Test url","https://www.sql.com.mt/Resources/PropertyImage.ashx?FileName=PropertyImg331802.jpeg", "aba2df1c-5441-4581-9dc2-5413c9691825", 200000);;
    Alert test_buyProperty_alert = new Alert(4, "Apartment in Mellieha", "Buy a house", "Test url","https://www.sql.com.mt/Resources/PropertyImage.ashx?FileName=PropertyImg331802.jpeg", "aba2df1c-5441-4581-9dc2-5413c9691825", 200000);;
    Alert test_toys_alert = new Alert(5, "Ps4", "Buy a toy", "Test url","https://cdn.pocket-lint.com/r/s/1200x630/assets/images/138767-games-review-ps4-slim-review-image1-qh1xwtlcmj.jpg", "aba2df1c-5441-4581-9dc2-5413c9691825", 200000);;
    Alert test_electronics_alert = new Alert(6, "Samsung 55 Inch", "Buy a phone", "Test url","https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcShCxeJ0iohRxpVo-C_fdFHl3nqRf5UMCo5aw&usqp=CAU", "aba2df1c-5441-4581-9dc2-5413c9691825", 200000);;


    @Before
    public void setup(){
        System.setProperty("webdriver.chrome.driver", "/chromedriver/chromedriver.exe");
        browser = new ChromeDriver();
        browser.get("https://www.marketalertum.com/");
        loginPageObject = new LoginPageObject(browser);
        myAlertsObject = new MyAlertsObject(browser);
        ScraperFunc.sleep(1);
    }


    @Given("I am a user of marketalertum$")
    public void iAmAUserOfMarketalertum() {
        assert(browser.getTitle().contains("MarketAlertUM"));
    }

    @When("I login using valid credentials$")
    public void iLoginUsingValidCredentials() {
     loginPageObject.logIn(validLogin);
    }

    @Then("I should see my alerts$")
    public void iShouldSeeMyAlerts() {
     ScraperFunc.sleep(2);

     assert(browser.findElement(By.xpath("//main[@class='pb-3']//h1")).getText().equals("Latest alerts for Liam Scicluna"));

    }


    @When("I login using invalid credentials$")
    public void iLoginUsingInvalidCredentials() {
        loginPageObject.logIn("invalid");
    }

    @Then("I should see the login screen again$")
    public void iShouldSeeTheLoginScreenAgain() {
       ScraperFunc.sleep(2);
        assert(!myAlertsObject.loggedIn());
    }

    @Given("I am an administrator of the website and I upload (\\d+) alerts$")
    public void iAmAnAdministratorOfTheWebsiteAndIUploadAlerts(int noOfAlerts) throws Exception {
        loginPageObject.logIn(validLogin);
        assert(myAlertsObject.loggedIn());
       ScraperFunc.sleep(2);
        apiRequest = new ApiRequest();

        testAlertJson = new Gson().toJson(test_electronics_alert);

        api = Mockito.mock(Api.class);
        Mockito.when(api.getApi()).thenReturn(Api.AVAILABLE);
        apiRequest.setApiStatus(api);

       int statusReturn = myAlertsObject.uploadAlert(noOfAlerts, testAlertJson, apiRequest);
       assert(statusReturn == 201);
    }


    @When("I view a list of alerts$")
    public void iViewAListOfAlerts() {
       ScraperFunc.sleep(2);
        assert(browser.findElement(By.xpath("//main[@class='pb-3']//h1")).getText().equals("Latest alerts for Liam Scicluna"));
    }

    @Then("each alert should contain an icon$")
    public void eachAlertShouldContainAnIcon() {
        int alertCount = myAlertsObject.alertCount();
        String[] alertIcons = new String[alertCount];

        for(int i=0; i<alertCount; i++){
            alertIcons[i] = myAlertsObject.getAlertIcon(i);
            System.out.println(alertIcons[i]);
            assert(alertIcons[i] != null);
        }
    }

    @And("each alert should contain a heading$")
    public void eachAlertShouldContainAHeading() {
        int alertCount = myAlertsObject.alertCount();
        String[] alertHeadings = new String[alertCount];

        for (int i = 0; i < alertCount; i++) {
            alertHeadings[i] = myAlertsObject.getAlertHeading();
            System.out.println(alertHeadings[i]);
            assert (alertHeadings[i] != null);
        }
    }

    @And("each alert should contain a description$")
    public void eachAlertShouldContainADescription() {
        int alertCount = myAlertsObject.alertCount();
        String[] alertDescription = new String[alertCount];

        for (int i = 0; i < alertCount; i++) {
            alertDescription[i] = myAlertsObject.getAlertDescription();
            System.out.println(alertDescription[i]);
            assert (alertDescription[i] != null);
        }
    }

    @And("each alert should contain an image$")
    public void eachAlertShouldContainAnImage() {
        int alertCount = myAlertsObject.alertCount();
        String[] alertImage = new String[alertCount];

        for (int i = 0; i < alertCount; i++) {
            alertImage[i] = myAlertsObject.getAlertImage();
            System.out.println(alertImage[i]);
            assert (alertImage[i] != null);
        }
    }

    @And("each alert should contain a price$")
    public void eachAlertShouldContainAPrice() {
        int alertCount = myAlertsObject.alertCount();
        String[] alertPrice = new String[alertCount];

        for (int i = 0; i < alertCount; i++) {
            alertPrice[i] = myAlertsObject.getAlertPrice();
            System.out.println(alertPrice[i]);
            assert (alertPrice[i] != null);
        }
    }

    @And("each alert should contain a link to the original product website$")
    public void eachAlertShouldContainALinkToTheOriginalProductWebsite() {
        int alertCount = myAlertsObject.alertCount();
        String[] alertLink = new String[alertCount];

        for (int i = 0; i < alertCount; i++) {
            alertLink[i] = myAlertsObject.getAlertLink();
            System.out.println(alertLink[i]);
            assert (alertLink[i] != null);
        }

    }

    @Given("I am an administrator of the website and I upload more than 5 alerts$")
    public void iAmAnAdministratorOfTheWebsiteAndIUploadMoreThanAlerts() throws Exception {
        loginPageObject.logIn(validLogin);
       ScraperFunc.sleep(2);
        apiRequest = new ApiRequest();

        testAlertJson = new Gson().toJson(test_electronics_alert);

        api = Mockito.mock(Api.class);
        Mockito.when(api.getApi()).thenReturn(Api.AVAILABLE);
        apiRequest.setApiStatus(api);


        int statusReturn = myAlertsObject.uploadAlert(6, testAlertJson, apiRequest);
        assert(statusReturn == 201);

    }

    @When("I view a list of alerts I should see 5 alerts$")
    public void iViewAListOfAlertsIShouldSeeAlerts() {
        assert(myAlertsObject.alertCount()<=5 && myAlertsObject.alertCount()>0);
    }

    @Given("I am an administrator of the website and I upload an alert of type {int}")
    public void iAmAnAdministratorOfTheWebsiteAndIUploadAnAlertOfTypeAlertType(int alertType) throws Exception {
        if(!myAlertsObject.loggedIn()){
            loginPageObject.logIn(validLogin);
        }
       ScraperFunc.sleep(2);
        assert(myAlertsObject.loggedIn());

        apiRequest = new ApiRequest();
        switch(alertType){
            case 1:
                testAlertJson = new Gson().toJson(test_cart_alert);
                break;
            case 2:
                testAlertJson = new Gson().toJson(test_boat_alert);
                break;
            case 3:
                testAlertJson = new Gson().toJson(test_rent_alert);
                break;
            case 4:
                testAlertJson = new Gson().toJson(test_buyProperty_alert);
                break;
            case 5:
                testAlertJson = new Gson().toJson(test_toys_alert);
                break;
            default:
                testAlertJson = new Gson().toJson(test_electronics_alert);
                break;
        }

        api = Mockito.mock(Api.class);
        Mockito.when(api.getApi()).thenReturn(Api.AVAILABLE);
        apiRequest.setApiStatus(api);


        int statusReturn = myAlertsObject.uploadAlert(1, testAlertJson,apiRequest);
        assert(statusReturn == 201);
    }

    @Then("I should see (\\d+) alerts$")
    public void iShouldSeeAlerts(int arg0) {
        assert(myAlertsObject.alertCount() >= arg0);
    }

    @Then("I should get an icon name with icon-car.png")
    public void i_should_get_an_icon_name_with_icon_car_png() {
        //Reload the page
        myAlertsObject.reloadPage();
        String iconType = myAlertsObject.getAlertIcon(0);
        assert(iconType.contains("icon-car.png"));
    }

    @Then("I should get an icon name with icon-boat.png")
    public void i_should_get_an_icon_name_with_icon_boat_png() {
        myAlertsObject.reloadPage();
        String iconType = myAlertsObject.getAlertIcon(0);
        assert(iconType.contains("icon-boat.png"));
    }

    @Then("I should get an icon name with icon-property-rent.png")
    public void i_should_get_an_icon_name_with_icon_property_rent_jpg() {
        myAlertsObject.reloadPage();
        String iconType = myAlertsObject.getAlertIcon(0);
        assert(iconType.contains("icon-property-rent.jpg"));
    }

    @Then("I should get an icon name with icon-property-sale.png")
    public void i_should_get_an_icon_name_with_icon_property_sale_jpg() {
        myAlertsObject.reloadPage();
        String iconType = myAlertsObject.getAlertIcon(0);
        assert(iconType.contains("icon-property-sale.jpg"));
    }

    @Then("I should get an icon name with icon-toys.png")
    public void i_should_get_an_icon_name_with_icon_toys_png() {
        myAlertsObject.reloadPage();
        String iconType = myAlertsObject.getAlertIcon(0);
        assert(iconType.contains("icon-toys.png"));
    }

    @Then("I should get an icon name with icon-electronics.png")
    public void i_should_get_an_icon_name_with_icon_electronics_png() {
        myAlertsObject.reloadPage();
        String iconType = myAlertsObject.getAlertIcon(0);
        assert(iconType.contains("icon-electronics.png"));
    }

    @After
    public void tearDown() {
        browser.quit();
    }

    @AfterEach
    public void tearDownApi(){
        apiRequest = null;
        testAlertJson = null;
    }

}
