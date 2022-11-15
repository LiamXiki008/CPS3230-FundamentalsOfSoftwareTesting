//Package
package edu.um.cps3230;

//Imports
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class ScraperFunc {

    //Method used to get the product heading
    public List<WebElement> getProductHeading(WebDriver driver) throws Exception {
        //Wait for the page to load
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(2));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("a[class='product-item-link']")));

        //Get the list of products
        List<WebElement> productHeading = driver.findElements(By.cssSelector("a[class='product-item-link']"));

        //Return the list of products
        return productHeading;
    }

    //Method used to get the product price
    public List<WebElement> getPriceList(WebDriver driver) throws Exception {
        //Wait for the page to load
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(2));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("span[data-price-type='finalPrice']")));

        //Get the list of products
        List<WebElement> priceList = driver.findElements(By.cssSelector("span[data-price-type='finalPrice']"));

        //Return the list of prices
        return priceList;
    }

    //Method used to get the product image url
    public List<WebElement> getImageUrl(WebDriver driver) throws Exception {
        //Wait for the page to load
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(2));
        wait.until(ExpectedConditions.visibilityOfElementLocated((By.cssSelector("img[class='product-image-photo main-img']"))));

        //Get the list of products
        List<WebElement> imageUrls = driver.findElements(By.cssSelector("img[class='product-image-photo main-img']"));

        //Return the list of image urls
        return imageUrls;
    }

    //Method used to get the product description
    public List<WebElement> getDescription(WebDriver driver) throws Exception {
        //Wait for the page to load
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(2));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("a[class='product-item-link']")));

        //Get the list of products
        List<WebElement> productDescription = driver.findElements(By.cssSelector("a[class='product-item-link']"));

        //Return the list of descriptions
        return productDescription;
    }

    //Method used to get the product link
    public List<WebElement> getProductLink(WebDriver driver) throws Exception {
        //Wait for the page to load
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(2));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("a[class='product-item-link']")));

        //Get the list of products
        List<WebElement> productLinks = driver.findElements(By.cssSelector("a[class='product-item-link']"));

        //Return the list of product links
        return productLinks;
    }

    //Method used to search for a product
    public void search(WebDriver driver, String searchTerm) throws Exception{

        //Locate the search bar and enter the search term
        WebElement searchField = driver.findElement(By.name("q"));
        searchField.sendKeys(searchTerm);

        //Wait until verified
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("search")));
        driver.switchTo().activeElement().sendKeys(Keys.ENTER);
    }

    public int parsePriceToCents(String price) {
        String parsedPrice = "";
        parsedPrice = price.replace("€", "").replace(",", "").replace(".", "");

        return Integer.parseInt(parsedPrice);
    }

    public static void sleep(int seconds) {
        try {
            Thread.sleep(seconds*1000);
        } catch (Exception e) {
            System.out.println("Error: "+e);
        }
    }


}
