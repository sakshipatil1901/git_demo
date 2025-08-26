package baseclassutility;

import java.io.IOException;

import org.openqa.selenium.WebDriver;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;

import genericutility.Propertiesutility;
import genericutility.WebDriverutility;

public class BaseClass {
    public WebDriver driver;
    protected Propertiesutility prop = new Propertiesutility();
    protected WebDriverutility wbu = new WebDriverutility();
    
    @BeforeClass
    public void beforeClass() throws IOException {
        String browser = prop.getDatafromProperties("Browser");
        driver = wbu.launchInternet(browser);
        wbu.maximizeAndTime(driver);
        Reporter.log("Successfully launched browser: " + browser, true);
        
        String url = prop.getDatafromProperties("URL");
        driver.get(url);
        Reporter.log("Navigated to URL: " + url, true);
        
        // Wait for page to load completely
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
    
    @BeforeMethod
    public void beforeMethod() throws IOException, InterruptedException {
        // Add any setup needed before each test method
        // For now, this is empty but can be used for login or other setup
    }
    
    @AfterClass
    public void afterClass() throws InterruptedException {
        if (driver != null) {
            Thread.sleep(3000);
            driver.quit();
            Reporter.log("Browser closed successfully", true);
        }
    }
}