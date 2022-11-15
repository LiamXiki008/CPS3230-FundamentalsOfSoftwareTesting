package edu.um.cps3230;

//Imports
import com.google.gson.Gson;
import edu.um.cps3230.testdoubles.Alert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import java.util.List;

//Class used to perform the scrape
public class Scraper {

    final int NO_OF_PRODUCTS = 5; //Number of products to scrape
    final int ALERT_TYPE = 6;
    final String POSTED_BY = "aba2df1c-5441-4581-9dc2-5413c9691825"; //This is the user id of the user that posted the alert
    final String API_URL = "https://api.marketalertum.com/Alert"; //This is the url of the API
    final String SEARCH_TERM = "gaming laptop"; //This is the search term that will be used to search for products
    WebDriver driver; //This is the driver that will be used to perform the scrape
    int statusCode = 0; //This is the status code of the response from the server
    Alert alert;
    String jsonAlert;

    public int Scraper(WebDriver driver, ApiRequest apiRequest) throws Exception {
        ScraperFunc scraper = new ScraperFunc();

        //Set the driver
        this.driver = driver;

        //Find the search bar in the website and look for asus products
        scraper.search(driver, SEARCH_TERM);

        //Find list of products that resulted from the search
        List<WebElement> productHeading = scraper.getProductHeading(driver);
        //Find list of prices that resulted from the search
        List<WebElement> productPriceList = scraper.getPriceList(driver);
        //Find list of imageUrls that resulted from the search
        List<WebElement> productImageUrl = scraper.getImageUrl(driver);
        //Get the description of all the products in the list
        List<WebElement> productDescription = scraper.getDescription(driver);
        //Find list of product links that resulted from the search and get the href
        List<WebElement> productLink = scraper.getProductLink(driver);



        //Loop through the list of products and get the product name, price, image url, description and link
        for (int i = 0; i < NO_OF_PRODUCTS; i++) {

            //Get the product name
            String productName = productHeading.get(i).getText();
            //Get the product heading
            String heading = productName.substring(0, productName.indexOf("\"")).replace("\"", "");
            //Get the product price
            String price = productPriceList.get(i).getText();;
            int parsedPrice = scraper.parsePriceToCents(price);
            //Get the product image url
            String imageUrl = productImageUrl.get(i).getAttribute("src");
            //Get the product description
            String description = productDescription.get(i).getText().replace("\"", "");
            //Get the product link
            String url = productLink.get(i).getAttribute("href");

            alert = new Alert(ALERT_TYPE,heading, description,url,imageUrl,POSTED_BY,parsedPrice);
            jsonAlert = new Gson().toJson(alert);
            //Post the alert to the server
            statusCode = apiRequest.postAlert(jsonAlert);
            System.out.println(statusCode);
        }

        //Return the status code
        return statusCode;
    }
}
