package objectrepository;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Reporter;

public class SearchPage {
    public WebDriver driver;
    public WebDriverWait wait;
    
    public SearchPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }
    
    // Rent/Buy selection
    @FindBy(xpath = "//div[@class='cursor-pointer '][1]")
    private WebElement selectpropertytype;
    
    // Search field
    @FindBy(id = "listPageSearchLocality")
    private WebElement searchTF;
    
    // Autocomplete dropdown
    @FindBy(xpath = "//div[@class='autocomplete-dropdown-container']/div/div/div[2]/div")
    private WebElement searchbox;
    
    // Property type filters
    @FindBy(xpath = "(//label[@class='nb-radio radio-inline'])[1]")
    private WebElement fullhouse;
    
    // BHK Type dropdown
    @FindBy(xpath = "//div[@class='nb-select__placeholder']")
    private WebElement BHKType;
    
    // Search button
    @FindBy(xpath = "//button[@class='prop-search-button flex items-center justify-center btn btn-primary btn-lg']")
    private WebElement Searchbtn;
    
    // Multiple locators for 1RK property - fallback strategy
    @FindBy(xpath = "//div[text()='1 RK']")
    private WebElement first1RKProperty;
    
    @FindBy(xpath = "//span[contains(text(), '1 RK')]")
    private WebElement first1RKPropertyAlternate1;
    
    @FindBy(xpath = "//div[contains(text(), '1RK')]")
    private WebElement first1RKPropertyAlternate2;
    
    @FindBy(xpath = "//*[contains(text(), '1 RK') or contains(text(), '1RK')]")
    private WebElement first1RKPropertyAlternate3;
    
    // Property cards container
    @FindBy(xpath = "//div[contains(@class, 'property-card') or contains(@class, 'prop-card')]")
    private List<WebElement> propertyCards;
    
    // Results container
    @FindBy(xpath = "//div[@id='results'] | //div[contains(@class, 'results')] | //div[contains(@class, 'property-list')]")
    private WebElement resultsContainer;
    
    // Getters
    public WebElement getSelectpropertytype() {
        return selectpropertytype;
    }
    
    public WebElement getSearchTF() {
        return searchTF;
    }
    
    public WebElement getSearchbox() {
        return searchbox;
    }
    
    public WebElement getFullhouse() {
        return fullhouse;
    }
    
    public WebElement getBHKType() {
        return BHKType;
    }
    
    public WebElement getSearchbtn() {
        return Searchbtn;
    }
    
    public WebElement getFirst1RKProperty() {
        return first1RKProperty;
    }
    
    public List<WebElement> getPropertyCards() {
        return propertyCards;
    }
    
    public WebElement getResultsContainer() {
        return resultsContainer;
    }
    
    // Utility methods for better element handling
    public WebElement find1RKPropertyWithFallback() {
        try {
            if (first1RKProperty.isDisplayed()) {
                return first1RKProperty;
            }
        } catch (Exception e) {
            Reporter.log("Primary 1RK locator failed: " + e.getMessage(), true);
        }
        
        try {
            if (first1RKPropertyAlternate1.isDisplayed()) {
                return first1RKPropertyAlternate1;
            }
        } catch (Exception e) {
            Reporter.log("Alternate 1RK locator 1 failed: " + e.getMessage(), true);
        }
        
        try {
            if (first1RKPropertyAlternate2.isDisplayed()) {
                return first1RKPropertyAlternate2;
            }
        } catch (Exception e) {
            Reporter.log("Alternate 1RK locator 2 failed: " + e.getMessage(), true);
        }
        
        try {
            if (first1RKPropertyAlternate3.isDisplayed()) {
                return first1RKPropertyAlternate3;
            }
        } catch (Exception e) {
            Reporter.log("Alternate 1RK locator 3 failed: " + e.getMessage(), true);
        }
        
        return null;
    }
    
    public boolean is1RKPropertyDisplayed() {
        WebElement element = find1RKPropertyWithFallback();
        if (element != null) {
            Reporter.log("1RK property found using fallback locator", true);
            return true;
        }
        
        // Try finding using dynamic search
        try {
            List<WebElement> elements = driver.findElements(By.xpath("//*[contains(text(), '1') and contains(text(), 'RK')]"));
            if (!elements.isEmpty()) {
                Reporter.log("1RK property found using dynamic search, total found: " + elements.size(), true);
                return true;
            }
        } catch (Exception e) {
            Reporter.log("Dynamic 1RK search failed: " + e.getMessage(), true);
        }
        
        return false;
    }
    
    public void debugPageContent() {
        Reporter.log("=== PAGE DEBUG INFO ===", true);
        Reporter.log("Current URL: " + driver.getCurrentUrl(), true);
        Reporter.log("Page Title: " + driver.getTitle(), true);
        
        // Check if results are loaded
        try {
            List<WebElement> allDivs = driver.findElements(By.tagName("div"));
            Reporter.log("Total div elements on page: " + allDivs.size(), true);
            
            // Look for any text containing "RK"
            List<WebElement> rkElements = driver.findElements(By.xpath("//*[contains(text(), 'RK')]"));
            Reporter.log("Elements containing 'RK': " + rkElements.size(), true);
            
            for (int i = 0; i < Math.min(rkElements.size(), 5); i++) {
                Reporter.log("RK Element " + (i+1) + ": " + rkElements.get(i).getText(), true);
            }
            
            // Check property cards
            if (!propertyCards.isEmpty()) {
                Reporter.log("Property cards found: " + propertyCards.size(), true);
                for (int i = 0; i < Math.min(propertyCards.size(), 3); i++) {
                    Reporter.log("Property card " + (i+1) + " text: " + propertyCards.get(i).getText(), true);
                }
            } else {
                Reporter.log("No property cards found", true);
            }
            
        } catch (Exception e) {
            Reporter.log("Debug failed: " + e.getMessage(), true);
        }
        Reporter.log("=== END DEBUG INFO ===", true);
    }
}