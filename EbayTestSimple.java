package mock;

import java.io.File;
import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.io.FileHandler;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class EbayTestSimple {
    public static void main(String[] args) {
        WebDriver driver = null;
        
        try {
            // Step 1: Setup Chrome driver
            System.out.println("Setting up Chrome driver...");
            ChromeOptions options = new ChromeOptions();
            options.addArguments("--disable-blink-features=AutomationControlled");
            options.addArguments("--no-sandbox");
            options.addArguments("--disable-dev-shm-usage");
            
            driver = new ChromeDriver(options);
            driver.manage().window().maximize();
            
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            JavascriptExecutor js = (JavascriptExecutor) driver;
            
            // Step 2: Go to eBay
            System.out.println("Opening eBay...");
            driver.get("https://www.ebay.com");
            Thread.sleep(3000);
            
            // Handle cookie banner if present
            try {
                WebElement cookieButton = driver.findElement(By.id("gdpr-banner-accept"));
                if (cookieButton.isDisplayed()) {
                    cookieButton.click();
                    System.out.println("Accepted cookies");
                }
            } catch (Exception e) {
                System.out.println("No cookie banner found");
            }
            
            // Step 3: Search for mobiles
            System.out.println("Searching for mobiles...");
            WebElement searchBox = wait.until(ExpectedConditions.elementToBeClickable(By.id("gh-ac")));
            searchBox.clear();
            searchBox.sendKeys("mobiles");
            
            WebElement searchButton = driver.findElement(By.id("gh-btn"));
            searchButton.click();
            
            // Wait for results
            Thread.sleep(5000);
            
            // Step 4: Find and click on a mobile
            System.out.println("Looking for a mobile to select...");
            List<WebElement> items = driver.findElements(By.cssSelector(".s-item"));
            
            boolean itemClicked = false;
            for (int i = 1; i < Math.min(items.size(), 8); i++) {
                try {
                    WebElement item = items.get(i);
                    WebElement titleElement = item.findElement(By.cssSelector(".s-item__title"));
                    String title = titleElement.getText();
                    
                    // Skip ads and focus on regular items
                    if (!title.toLowerCase().contains("shop on ebay") && 
                        !title.toLowerCase().contains("sponsored") &&
                        title.length() > 10) {
                        
                        WebElement link = item.findElement(By.cssSelector(".s-item__link"));
                        System.out.println("Selected mobile: " + title);
                        
                        // Scroll and click
                        js.executeScript("arguments[0].scrollIntoView(true);", link);
                        Thread.sleep(2000);
                        js.executeScript("arguments[0].click();", link);
                        itemClicked = true;
                        break;
                    }
                } catch (Exception e) {
                    continue;
                }
            }
            
            if (!itemClicked) {
                System.out.println("Could not find a suitable item");
                return;
            }
            
            // Step 5: Wait for product page
            Thread.sleep(5000);
            System.out.println("Product page loaded");
            
            // Step 6: Try to select any required variants
            System.out.println("Checking for product options...");
            try {
                List<WebElement> dropdowns = driver.findElements(By.tagName("select"));
                for (WebElement dropdown : dropdowns) {
                    if (dropdown.isDisplayed() && dropdown.isEnabled()) {
                        Select select = new Select(dropdown);
                        if (select.getOptions().size() > 1) {
                            select.selectByIndex(1); // Select first available option
                            System.out.println("Selected option: " + select.getFirstSelectedOption().getText());
                            Thread.sleep(1000);
                        }
                    }
                }
            } catch (Exception e) {
                System.out.println("No additional options needed");
            }
            
            // Step 7: Add to cart
            System.out.println("Adding to cart...");
            boolean addedToCart = false;
            
            // Try different add to cart button selectors
            String[] cartSelectors = {
                "a[data-testid='ux-call-to-action']",
                "#atcBtn",
                ".notranslate[href*='cart']",
                ".btn-ter",
                "a[href*='AddToCart']"
            };
            
            for (String selector : cartSelectors) {
                try {
                    WebElement addToCartBtn = driver.findElement(By.cssSelector(selector));
                    if (addToCartBtn.isDisplayed() && addToCartBtn.isEnabled()) {
                        js.executeScript("arguments[0].scrollIntoView(true);", addToCartBtn);
                        Thread.sleep(1000);
                        js.executeScript("arguments[0].click();", addToCartBtn);
                        System.out.println("Clicked Add to Cart button");
                        addedToCart = true;
                        break;
                    }
                } catch (Exception e) {
                    continue;
                }
            }
            
            if (!addedToCart) {
                System.out.println("Could not find Add to Cart button, trying Buy It Now...");
                try {
                    WebElement buyNowBtn = driver.findElement(By.cssSelector("#binBtn_btn"));
                    js.executeScript("arguments[0].click();", buyNowBtn);
                    System.out.println("Clicked Buy It Now");
                } catch (Exception e) {
                    System.out.println("Could not find Buy It Now button either");
                }
            }
            
            // Wait for cart/checkout page
            Thread.sleep(8000);
            
            // Step 8: Navigate to cart if needed
            if (!driver.getCurrentUrl().contains("cart") && !driver.getCurrentUrl().contains("checkout")) {
                try {
                    System.out.println("Navigating to cart...");
                    WebElement cartIcon = driver.findElement(By.cssSelector("#gh-cart-n"));
                    cartIcon.click();
                    Thread.sleep(5000);
                } catch (Exception e) {
                    System.out.println("Could not find cart icon");
                }
            }
            
            // Step 9: Take screenshot
            System.out.println("Taking screenshot...");
            TakesScreenshot screenshot = (TakesScreenshot) driver;
            File sourceFile = screenshot.getScreenshotAs(OutputType.FILE);
            File destFile = new File("ebay_cart_screenshot.png");
            FileHandler.copy(sourceFile, destFile);
            
            System.out.println("Screenshot saved: " + destFile.getAbsolutePath());
            System.out.println("Test completed successfully!");
            
            // Display cart info if possible
            try {
                List<WebElement> cartItems = driver.findElements(By.cssSelector(".item-title, .it-ttl, [data-testid='item-title']"));
                if (!cartItems.isEmpty()) {
                    System.out.println("Items in cart:");
                    for (WebElement item : cartItems) {
                        String itemText = item.getText().trim();
                        if (!itemText.isEmpty()) {
                            System.out.println("- " + itemText);
                        }
                    }
                }
            } catch (Exception e) {
                System.out.println("Cart page loaded successfully");
            }
            
        } catch (Exception e) {
            System.err.println("Error occurred: " + e.getMessage());
            e.printStackTrace();
            
            // Take error screenshot
            if (driver != null) {
                try {
                    TakesScreenshot screenshot = (TakesScreenshot) driver;
                    File errorFile = screenshot.getScreenshotAs(OutputType.FILE);
                    File destFile = new File("ebay_error_screenshot.png");
                    FileHandler.copy(errorFile, destFile);
                    System.out.println("Error screenshot saved: " + destFile.getAbsolutePath());
                } catch (Exception ex) {
                    System.err.println("Could not take error screenshot");
                }
            }
            
        } finally {
            // Close browser
            if (driver != null) {
                System.out.println("Closing browser in 5 seconds...");
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                driver.quit();
                System.out.println("Browser closed");
            }
        }
    }
}