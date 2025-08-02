# eBay Selenium Automation

This project automates the process of searching for mobiles on eBay, selecting a product, adding it to cart, and capturing a screenshot.

## Prerequisites

- Java 11 or higher
- Maven 3.6 or higher
- Chrome browser installed
- ChromeDriver (will be managed automatically by WebDriverManager)

## Setup Instructions

1. **Clone or download the project files**

2. **Install dependencies**
   ```bash
   mvn clean install
   ```

3. **Run the automation**
   ```bash
   mvn exec:java -Dexec.mainClass="mock.TestCase"
   ```

   Or alternatively:
   ```bash
   mvn compile exec:java
   ```

## What the Script Does

1. **Navigate to eBay**: Opens eBay.com in Chrome browser
2. **Search for Mobiles**: Searches for "mobiles" in the search box
3. **Select a Product**: Clicks on a suitable mobile from search results (skips ads)
4. **Handle Product Variants**: Automatically selects mandatory options like color, storage, etc.
5. **Add to Cart**: Adds the selected mobile to the shopping cart
6. **Capture Screenshot**: Takes a screenshot of the cart page and saves it as `ebay_cart_screenshot.png`

## Key Improvements from Original Code

### Fixed Issues:
- **ElementNotInteractableException**: Used JavaScript click instead of regular click
- **Better Element Selection**: Improved CSS selectors and added fallback options
- **Robust Waits**: Replaced Thread.sleep with WebDriverWait for better reliability
- **Error Handling**: Added comprehensive try-catch blocks

### Features Added:
- **Chrome Options**: Added stealth options to avoid detection
- **Variant Selection**: Automatically handles product variants (color, size, storage)
- **Screenshot Capture**: Takes screenshot of cart page
- **Error Screenshots**: Captures screenshot on errors for debugging
- **Smart Product Selection**: Skips ads and auctions, selects regular products
- **Cart Navigation**: Handles different cart page scenarios

## Project Structure

```
├── src/
│   └── main/
│       └── java/
│           └── mock/
│               └── TestCase.java      # Main automation script
├── pom.xml                           # Maven dependencies
├── README.md                         # This file
├── ebay_cart_screenshot.png         # Generated screenshot (after run)
└── ebay_error_screenshot.png        # Error screenshot (if errors occur)
```

## Dependencies

- **Selenium WebDriver 4.27.0**: For browser automation
- **WebDriverManager 5.6.2**: Automatic driver management
- **Apache Commons IO 2.15.1**: File operations for screenshots
- **Chrome Options**: Configured to avoid automation detection

## Common Issues and Solutions

1. **Chrome Driver Issues**:
   - The script uses WebDriverManager to automatically download and manage ChromeDriver
   - Make sure Chrome browser is installed and up to date

2. **Element Not Found**:
   - eBay's DOM structure may change; the script includes multiple fallback selectors
   - Error screenshots are captured for debugging

3. **Network Issues**:
   - The script includes reasonable timeouts and waits
   - If eBay is slow to load, increase the timeout values

4. **CAPTCHA or Security Checks**:
   - The script includes Chrome options to minimize detection
   - Manual intervention may be required if CAPTCHA appears

## Output

- **Console Output**: Detailed logs of each step
- **Screenshot**: `ebay_cart_screenshot.png` saved in project root
- **Error Screenshot**: `ebay_error_screenshot.png` if errors occur

## Customization

You can modify the following in `TestCase.java`:
- Search term (currently "mobiles")
- Product selection criteria
- Timeout values
- Screenshot filename
- Chrome options

## Notes

- The script is designed to work with eBay's current DOM structure (as of 2024)
- It automatically skips sponsored ads and auctions
- Screenshots are saved in PNG format
- The browser window closes automatically after 5 seconds