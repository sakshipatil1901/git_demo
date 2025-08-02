package mock;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.List;
import org.apache.commons.io.FileUtils;

public class TestCase {
    public static void main(String[] args) throws InterruptedException, IOException {
        // Step 1: Setup Chrome driver with options
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--disable-blink-features=AutomationControlled");
        options.addExperimentalOption("useAutomationExtension", false);
        options.addExperimentalOption("excludeSwitches", new String[]{"enable-automation"});
        
        WebDriver driver = new ChromeDriver(options);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        JavascriptExecutor js = (JavascriptExecutor) driver;
        
        try {
            driver.manage().window().maximize();
            
            // Step 2: Navigate to eBay
            System.out.println("Navigating to eBay...");
            driver.get("https://www.ebay.com");
            
            // Wait for page to load and accept cookies if popup appears
            Thread.sleep(2000);
            try {
                WebElement acceptCookies = wait.until(ExpectedConditions.elementToBeClickable(By.id("gdpr-banner-accept")));
                acceptCookies.click();
                System.out.println("Accepted cookies");
            } catch (Exception e) {
                System.out.println("No cookie banner found");
            }
            
            // Step 3: Search for "mobiles"
            System.out.println("Searching for mobiles...");
            WebElement searchBox = wait.until(ExpectedConditions.elementToBeClickable(By.id("gh-ac")));
            searchBox.clear();
            searchBox.sendKeys("mobiles");
            
            WebElement searchButton = driver.findElement(By.id("gh-btn"));
            searchButton.click();
            
            // Wait for search results to load
            wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".s-item")));
            Thread.sleep(3000);
            
            // Step 4: Click on a mobile (skip sponsored ads)
            System.out.println("Selecting a mobile...");
            List<WebElement> searchResults = driver.findElements(By.cssSelector(".s-item:not(.s-item--watch-at-auction)"));
            
            WebElement selectedMobile = null;
            for (int i = 1; i < Math.min(searchResults.size(), 10); i++) { // Skip first item as it might be ad
                WebElement item = searchResults.get(i);
                try {
                    WebElement link = item.findElement(By.cssSelector(".s-item__link"));
                    WebElement title = item.findElement(By.cssSelector(".s-item__title"));
                    
                    // Skip if it's an ad or auction
                    if (!title.getText().toLowerCase().contains("ad") && 
                        !title.getText().toLowerCase().contains("auction")) {
                        selectedMobile = link;
                        System.out.println("Selected mobile: " + title.getText());
                        break;
                    }
                } catch (Exception e) {
                    continue;
                }
            }
            
            if (selectedMobile != null) {
                // Scroll to element and click
                js.executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", selectedMobile);
                Thread.sleep(2000);
                
                // Use JavaScript click to avoid interception
                js.executeScript("arguments[0].click();", selectedMobile);
                System.out.println("Clicked on mobile");
            } else {
                throw new RuntimeException("No suitable mobile found");
            }
            
            // Step 5: Wait for product page to load
            wait.until(ExpectedConditions.presenceOfElementLocated(By.id("x-msku-price-label")));
            Thread.sleep(3000);
            
            // Step 6: Handle product variants if any (color, size, etc.)
            System.out.println("Checking for product variants...");
            try {
                // Look for variant selectors (color, size, storage, etc.)
                List<WebElement> variantSelectors = driver.findElements(By.cssSelector("select[id*='msku'], .x-msku-selectors select"));
                
                for (WebElement selector : variantSelectors) {
                    if (selector.isDisplayed() && selector.isEnabled()) {
                        Select dropdown = new Select(selector);
                        List<WebElement> options = dropdown.getOptions();
                        if (options.size() > 1) {
                            // Select the second option (first is usually "Select...")
                            dropdown.selectByIndex(1);
                            System.out.println("Selected variant option: " + dropdown.getFirstSelectedOption().getText());
                            Thread.sleep(1000);
                        }
                    }
                }
            } catch (Exception e) {
                System.out.println("No variants found or already selected");
            }
            
            // Step 7: Add to cart
            System.out.println("Adding to cart...");
            try {
                WebElement addToCartButton = wait.until(ExpectedConditions.elementToBeClickable(
                    By.cssSelector("a[data-testid='ux-call-to-action'], .notranslate[href*='cart'], #atcBtn, .btn-ter")));
                
                js.executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", addToCartButton);
                Thread.sleep(1000);
                js.executeScript("arguments[0].click();", addToCartButton);
                System.out.println("Clicked Add to Cart");
                
            } catch (Exception e) {
                // Try alternative selectors
                try {
                    WebElement buyNowButton = driver.findElement(By.cssSelector("#binBtn_btn, .notranslate[href*='rpp']"));
                    js.executeScript("arguments[0].click();", buyNowButton);
                    System.out.println("Clicked Buy It Now (alternative)");
                } catch (Exception e2) {
                    System.out.println("Could not find add to cart button");
                }
            }
            
            // Wait for cart page or popup
            Thread.sleep(5000);
            
            // Step 8: Navigate to cart if not already there
            try {
                if (!driver.getCurrentUrl().contains("cart") && !driver.getCurrentUrl().contains("checkout")) {
                    WebElement cartIcon = wait.until(ExpectedConditions.elementToBeClickable(
                        By.cssSelector("#gh-cart-n, .gh-cart, [data-testid='cart-icon']")));
                    cartIcon.click();
                    System.out.println("Navigated to cart");
                }
            } catch (Exception e) {
                System.out.println("Already in cart or checkout page");
            }
            
            // Wait for cart page to load
            Thread.sleep(3000);
            
            // Step 9: Take screenshot of cart
            System.out.println("Taking screenshot of cart...");
            TakesScreenshot takesScreenshot = (TakesScreenshot) driver;
            File sourceFile = takesScreenshot.getScreenshotAs(OutputType.FILE);
            File destFile = new File("ebay_cart_screenshot.png");
            FileUtils.copyFile(sourceFile, destFile);
            System.out.println("Screenshot saved as: " + destFile.getAbsolutePath());
            
            // Optional: Print cart summary
            try {
                List<WebElement> cartItems = driver.findElements(By.cssSelector(".item-title, .it-ttl"));
                if (!cartItems.isEmpty()) {
                    System.out.println("Items in cart:");
                    for (WebElement item : cartItems) {
                        System.out.println("- " + item.getText());
                    }
                } else {
                    System.out.println("Cart page loaded but no items found in DOM selectors");
                }
            } catch (Exception e) {
                System.out.println("Could not read cart items");
            }
            
            System.out.println("Test completed successfully!");
            
        } catch (Exception e) {
            System.err.println("Error occurred: " + e.getMessage());
            e.printStackTrace();
            
            // Take screenshot on error
            try {
                TakesScreenshot takesScreenshot = (TakesScreenshot) driver;
                File sourceFile = takesScreenshot.getScreenshotAs(OutputType.FILE);
                File destFile = new File("ebay_error_screenshot.png");
                FileUtils.copyFile(sourceFile, destFile);
                System.out.println("Error screenshot saved as: " + destFile.getAbsolutePath());
            } catch (Exception screenshotEx) {
                System.err.println("Could not take error screenshot");
            }
            
        } finally {
            // Wait a bit before closing
            Thread.sleep(5000);
            driver.quit();
            System.out.println("Browser closed");
        }
    }
}