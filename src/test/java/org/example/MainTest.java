package org.example;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Reporter;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import java.time.Duration;

public class MainTest {
    private WebDriver driver;

    @BeforeTest
    public void setUp() {
        System.setProperty("webdriver.chrome.driver", "C:/webdrivers/chromedriver.exe");
        driver = new ChromeDriver();
    }

    @Test(priority = 1)
    public void testHomePageTitle() {
        driver.get("https://www.amazon.com/");
        Reporter.log("Navigating to Amazon.com");
        String expectedTitle = "Amazon.com. Spend less. Smile more.";
        String actualTitle = driver.getTitle();
        Assert.assertEquals(actualTitle, expectedTitle);
    }

    @Test(priority = 2)
    public void testSearchFunctionality() {
        String searchQuery = "iPhone 12";
        driver.findElement(By.id("twotabsearchtextbox")).sendKeys(searchQuery);
        driver.findElement(By.id("nav-search-submit-button")).click();
        Reporter.log("Searching for iPhone 12");
        String expectedTitle = "Amazon.com : iPhone 12";
        String actualTitle = driver.getTitle();
        Assert.assertEquals(actualTitle, expectedTitle);
    }

    @Test(priority = 3)
    public void testProductDetailsPage() {
        driver.findElement(By.linkText("Apple iPhone 12, 64GB, Blue - Unlocked (Renewed Premium)")).click();
        Reporter.log("Navigating to Apple iPhone 12, 64GB, Blue - Unlocked (Renewed Premium)");
        String expectedTitle = "Apple iPhone 12, 64GB, Blue - Unlocked (Renewed Premium)";
        String actualTitle = driver.getTitle();
        Assert.assertEquals(actualTitle, expectedTitle);
    }

    @Test(priority = 4)
    public void testAddToCart() {
        driver.findElement(By.id("add-to-cart-button")).click();
        Reporter.log("Adding iPhone 12 to cart");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@id='add-to-cart-button']")));
        String expectedText = "Added to Cart";
        WebDriverWait wait2 = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait2.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"attachDisplayAddBaseAlert\"]/div/h4")));
        String actualText = driver.findElement(By.xpath("//*[@id=\"attachDisplayAddBaseAlert\"]/div/h4")).getText();
        Assert.assertEquals(actualText, expectedText);
    }

    @Test(priority = 5)
    public void testCheckout() {
        driver.findElement(By.xpath("//*[@id=\"attach-sidesheet-view-cart-button\"]/span/input")).click();
        Reporter.log("Navigating to Checkout");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.titleContains("Amazon.com Shopping Cart"));
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"sc-buy-box-ptc-button\"]/span/input"))).click();
        wait.until(ExpectedConditions.titleContains("Amazon Sign-In"));
        String actualTitle = driver.getTitle();
        String expectedTitle = "Amazon Sign-In";
        Assert.assertEquals(actualTitle, expectedTitle);
    }

    @Test(priority = 6)
    public void testSignIn() {
        Reporter.log("Signing in using login details");
        WebElement emailInput = driver.findElement(By.id("ap_email"));
        emailInput.sendKeys("+32498357499");
        WebElement continueButton = driver.findElement(By.id("continue"));
        continueButton.click();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("ap_password")));
        WebElement passwordInput = driver.findElement(By.id("ap_password"));
        passwordInput.sendKeys("123456789");
        WebElement signInButton = driver.findElement(By.id("signInSubmit"));
        signInButton.click();
        wait.until(ExpectedConditions.titleContains("Select a shipping address"));
        String actualTitle = driver.getTitle();
        String expectedTitle = "Select a shipping address";
        Assert.assertEquals(actualTitle, expectedTitle);
    }

    @AfterTest
    public void tearDown() {
        driver.quit();
    }
}
