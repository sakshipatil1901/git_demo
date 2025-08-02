# Eclipse Setup Instructions for eBay Selenium Automation

## Step 1: Download Required JAR Files

Download these JAR files and save them in a folder (e.g., `C:\selenium-jars\`):

### Required JAR Files:
1. **Selenium WebDriver** (selenium-java-4.27.0.jar)
   - Download from: https://selenium-release.storage.googleapis.com/4.27/selenium-java-4.27.0.zip
   - Extract and use `selenium-java-4.27.0.jar` and all JAR files from the `libs` folder

2. **ChromeDriver** (will be downloaded automatically by WebDriverManager)

### Direct Download Links:
- **Selenium Java 4.27.0**: https://github.com/SeleniumHQ/selenium/releases/download/selenium-4.27.0/selenium-java-4.27.0.zip

## Step 2: Setup Eclipse Project

1. **Open Eclipse IDE**

2. **Create New Java Project**:
   - File → New → Java Project
   - Project name: `EbaySeleniumTest`
   - Use default location
   - Click "Finish"

3. **Create Package**:
   - Right-click on `src` folder
   - New → Package
   - Package name: `mock`
   - Click "Finish"

4. **Add the Java File**:
   - Right-click on `mock` package
   - New → Class
   - Class name: `EbayTestSimple`
   - Check "public static void main(String[] args)"
   - Click "Finish"
   - Copy and paste the code from `EbayTestSimple.java`

## Step 3: Add JAR Files to Eclipse Project

1. **Right-click on your project** → Properties

2. **Go to Java Build Path** → Libraries tab

3. **Click "Add External JARs"**

4. **Navigate to your selenium-jars folder** and add these files:
   - `selenium-java-4.27.0.jar`
   - All JAR files from the `libs` folder (there will be many - add all of them)

5. **Click "Apply and Close"**

## Step 4: Download and Setup ChromeDriver (Manual Method)

If you want to manually manage ChromeDriver instead of auto-download:

1. **Download ChromeDriver**:
   - Go to: https://chromedriver.chromium.org/downloads
   - Download version matching your Chrome browser
   - Extract `chromedriver.exe`

2. **Place ChromeDriver**:
   - Put `chromedriver.exe` in a folder like `C:\chromedriver\`
   - Add this path to your system PATH environment variable

OR

3. **Set System Property in Code** (add this line before creating ChromeDriver):
   ```java
   System.setProperty("webdriver.chrome.driver", "C:\\path\\to\\chromedriver.exe");
   ```

## Step 5: Run the Program

1. **Right-click on `EbayTestSimple.java`**
2. **Run As → Java Application**
3. **Watch the console output** for progress messages
4. **Check your project folder** for the screenshot file `ebay_cart_screenshot.png`

## Alternative: Quick JAR Download Commands

If you have command line access, you can download the JAR files directly:

```bash
# Create directory
mkdir selenium-jars
cd selenium-jars

# Download Selenium
curl -L -O https://github.com/SeleniumHQ/selenium/releases/download/selenium-4.27.0/selenium-java-4.27.0.zip

# Extract
unzip selenium-java-4.27.0.zip
```

## Troubleshooting

### Error: "Cannot find ChromeDriver"
- **Solution**: Install Chrome browser, or download ChromeDriver manually and set the path

### Error: "ClassNotFoundException"
- **Solution**: Make sure all JAR files from the Selenium libs folder are added to the build path

### Error: "ElementNotInteractableException"
- **Solution**: The code already handles this with JavaScript clicks

### Error: "TimeoutException"
- **Solution**: Check your internet connection and increase wait times if needed

## Expected Output

When successful, you should see console output like:
```
Setting up Chrome driver...
Opening eBay...
Searching for mobiles...
Looking for a mobile to select...
Selected mobile: [Mobile Name]
Product page loaded
Checking for product options...
Adding to cart...
Clicked Add to Cart button
Taking screenshot...
Screenshot saved: [Path]\ebay_cart_screenshot.png
Test completed successfully!
```

## Project Structure in Eclipse

```
EbaySeleniumTest/
├── src/
│   └── mock/
│       └── EbayTestSimple.java
├── Referenced Libraries/
│   ├── selenium-java-4.27.0.jar
│   └── [all other selenium JAR files]
└── ebay_cart_screenshot.png (generated after run)
```

## Notes

- Make sure Chrome browser is installed and updated
- The script will automatically handle most eBay page interactions
- Screenshots are saved in the project root directory
- If you encounter issues, check the error screenshot that gets generated
- The program will close the browser automatically after 5 seconds