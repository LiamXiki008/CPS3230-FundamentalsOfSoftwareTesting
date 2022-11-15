package edu.um.cps3230;

import com.google.gson.Gson;
import edu.um.cps3230.testdoubles.Alert;
import edu.um.cps3230.testdoubles.Api;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.openqa.selenium.WebDriver;

public class ApiRequestTests {

    Alert test_alert;
    ApiRequest apiRequest;
    WebDriver driver;
    String testAlertJson;
    Api api;
    int statusCode = 0;


    @BeforeEach
    public void setup(){
        apiRequest = new ApiRequest();
        test_alert = new Alert(
                6,"ASUS TUF Gaming F17 17.3","ASUS TUF Gaming F17 17.3 FHD IPS 144Hz Core i7-11800H (11th Gen) 1TB SSD 16GB RAM RTX3060 (6GB) Win11 (Ex Display)","https://www.scanmalta.com/shop/asus-tuf-gaming-f17-17-3-fhd-ips-144hz-core-i7-11800h-11th-gen-1tb-ssd-16gb-ram-rtx3060-6gb-win10-ex-display.html",
                "https://www.scanmalta.com/shop/pub/media/catalog/product/cache/b084519189a7c7b3054def1f3dcab96f/3/3/3399009-l-a.jpg","aba2df1c-5441-4581-9dc2-5413c9691825",102900);

    testAlertJson = new Gson().toJson(test_alert);
    }

    //This method will test the post alert function
    @Test
    public void testPostAlert() throws Exception{
        //Setup
        api = Mockito.mock(Api.class);
        Mockito.when(api.getApi()).thenReturn(Api.AVAILABLE);
        apiRequest.setApiStatus(api);

        //Exercise
        statusCode = apiRequest.postAlert(testAlertJson);

        //Verify
        //Assert that scraper returns a status code of 201
        assert(statusCode == 201);

    }

    @Test
    public void testPostAlertWithApiUnavailable() throws Exception{
        //Setup
        api = Mockito.mock(Api.class);
        Mockito.when(api.getApi()).thenReturn(Api.UNAVAILABLE);
        apiRequest.setApiStatus(api);

        //Exercise
        int statusCode = apiRequest.postAlert(testAlertJson);

        //Verify
        //Assert that scraper returns a -1
        assert(statusCode == -1);

    }

    @Test
    public void testPostAlertWithNoApi() throws Exception{
        //Setup

        apiRequest.setApiStatus(null);

        //Exercise
        int statusCode = apiRequest.postAlert(testAlertJson);

        //Verify
        //Assert that scraper returns a -1
        assert(statusCode == -1);

    }

    @Test
    public void testDeleteAlertWithNoApi() throws Exception{
        //Setup

        apiRequest.setApiStatus(null);

        //Exercise
        int statusCode = apiRequest.removeAlert();

        //Verify
        //Assert that scraper returns a -1
        assert(statusCode == -1);

    }


    //This tests the delete function
    @Test
    public void testDeleteAlertWithApiService() throws Exception{
       //Setup
        api = Mockito.mock(Api.class);
        Mockito.when(api.getApi()).thenReturn(Api.AVAILABLE);
        apiRequest.setApiStatus(api);

        //Exercise
        statusCode = apiRequest.removeAlert();

        //Verify
        //Assert that the reply is 200
        assert(statusCode==200);

    }

    @Test
    public void testDeleteAlertWithoutApiService() throws Exception{
        //Setup
        api = Mockito.mock(Api.class);
        Mockito.when(api.getApi()).thenReturn(Api.UNAVAILABLE);
        apiRequest.setApiStatus(api);

        //Exercise
        statusCode = apiRequest.removeAlert();

        //Verify
        //Assert that the reply is -1
        assert(statusCode==-1);

    }

    @AfterEach
    public void tearDown(){
        apiRequest = null;
        test_alert = null;
        testAlertJson = null;
        statusCode = 0;
    }

}
