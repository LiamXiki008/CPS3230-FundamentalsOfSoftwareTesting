package edu.um.cps3230;

//Imports

import edu.um.cps3230.testdoubles.Api;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

//Class
public class ScreenScraperTests {

    //Variables
    WebDriver driver;
    final String SEARCH_TERM = "gaming laptop"; //This is the search term that will be used to search for products

    Scraper scraper = new Scraper(); //This is the scraper that will be used to perform the scrape
    ScraperFunc scraperFunc = new ScraperFunc(); //These are the scraper functions that will be used to perform the scrape
    ApiRequest apiRequest; //This is the api request that will be used to perform the scrape
    Api api;

    //This method will be run before each test and will set up the driver
    @BeforeEach
    public void testBrowserOpen() throws Exception {
            //Exercise
            System.setProperty("webdriver.chrome.driver", "/chromedriver/chromedriver.exe");
            driver = new ChromeDriver();

            //Open the Scan Malta website
            driver.get("https://www.scanmalta.com/shop");
            apiRequest = new ApiRequest();
            //Verify
            assert(driver.getTitle().equals("Computers Store Malta | SCAN"));
        }

    //Test to check if the search function works
    @Test
    public void testSearch() throws Exception{
        //Exercise
        scraperFunc.search(driver, SEARCH_TERM);

        //Verify
        //Assert that the search term is in the title
        assert(driver.getTitle().contains(SEARCH_TERM));
    }

    //Test to check if getProductHeading function works
    @Test
    public void testGetProductHeading() throws Exception{
        //Exercise
        scraperFunc.search(driver, SEARCH_TERM);
        scraperFunc.getProductHeading(driver);

        //Verify
        //Assert that the product heading is not null
        assert(scraperFunc.getProductHeading(driver) != null);
        int x=1;
    }

    //Test to check if getProductPrice function works
    @Test
    public void testGetPriceList() throws Exception{
        //Exercise
        scraperFunc.search(driver, SEARCH_TERM);
        scraperFunc.getPriceList(driver);

        //Verify
        //Assert that the product price is not null
        assert(scraperFunc.getPriceList(driver) != null);
    }

    //Test to check if getImageUrl function works
    @Test
    public void testGetImageUrl() throws Exception{
        //Exercise
        scraperFunc.search(driver, SEARCH_TERM);
        scraperFunc.getImageUrl(driver);

        //Verify
        //Assert that the image url is not null
        assert(scraperFunc.getImageUrl(driver) != null);
    }

    //Test to check if getDescription function works
    @Test
    public void testGetDescription() throws Exception{
        //Exercise
        scraperFunc.search(driver, SEARCH_TERM);
        scraperFunc.getDescription(driver);

        //Verify
        //Assert that the description is not null
        assert(scraperFunc.getDescription(driver) != null);
    }

    //Test the getProductLink function
    @Test
    public void testGetProductLink() throws Exception{
        //Exercise
        scraperFunc.search(driver, SEARCH_TERM);
        scraperFunc.getProductLink(driver);

        //Verify
        //Assert that the product link is not null
        assert(scraperFunc.getProductLink(driver) != null);
    }

    //Test the parsePriceToCents function
    @Test
    public void testParsePriceToCents() throws Exception{
        String test_price = "€1,000.00";
        int parsedPrice = scraperFunc.parsePriceToCents(test_price);

        assert(parsedPrice == 100000);
    }

    @Test
    public void testScrape() throws Exception{

        api = Mockito.mock(Api.class);
        Mockito.when(api.getApi()).thenReturn(Api.AVAILABLE);
        apiRequest.setApiStatus(api);

        //Exercise
        int statusCode = scraper.Scraper(driver, apiRequest);

        //Verify
        assert(statusCode == 201);
    }

    //Teardown method that will be run after each test
    @AfterEach
    public void teardown(){
        driver.quit();
    }
}

