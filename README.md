# NoBroker Property Search Automation

This project contains Selenium WebDriver automation tests for the NoBroker property search functionality.

## Project Structure

```
src/
├── test/
    ├── java/
    │   ├── baseclassutility/        # Base test class with common setup
    │   ├── genericutility/          # Utility classes for WebDriver and Properties
    │   ├── objectrepository/        # Page Object Model classes
    │   └── test/                    # Test classes
    └── resources/
        ├── data.properties          # Test data configuration
        └── testng.xml              # TestNG suite configuration
```

## Features

### Fixed Issues from Original Code:
1. **Enhanced Element Locators**: Multiple fallback strategies for finding 1RK property elements
2. **Better Wait Strategies**: Explicit waits with proper timeout handling
3. **Robust Error Handling**: Comprehensive exception handling and debugging
4. **Improved Page Object Model**: Better encapsulation and utility methods
5. **WebDriverManager Integration**: Automatic driver management
6. **Enhanced Debugging**: Page content analysis and detailed logging

### Key Improvements:
- **Fallback Element Location**: Multiple XPath strategies to find 1RK properties
- **Dynamic Wait Conditions**: Smart waiting for page elements and content loading
- **Comprehensive Debugging**: Page state analysis when tests fail
- **Better Browser Configuration**: Optimized Chrome options for automation
- **Modular Design**: Clean separation of concerns with proper utility classes

## Prerequisites

- Java 21 or higher
- Maven 3.6 or higher
- Chrome browser (latest version)

## Setup and Installation

1. **Clone the repository**
2. **Install dependencies**:
   ```bash
   mvn clean install
   ```

## Configuration

### Test Data Configuration (`src/test/resources/data.properties`):
```properties
Browser=chrome
URL=https://www.nobroker.in/
Locality=R Layout, Bengaluru, Karnataka, India
Mobile=7709402806
```

## Running Tests

### Using Maven:
```bash
# Run all tests
mvn test

# Run specific test class
mvn test -Dtest=SearchPropertyTest

# Run with specific browser
mvn test -Dbrowser=chrome
```

### Using TestNG XML:
```bash
mvn test -DsuiteXmlFile=src/test/resources/testng.xml
```

## Test Case: VerifySearch1RKProperty

This test automates the following steps:
1. Navigate to NoBroker website
2. Select Buy/Rent option
3. Enter locality (R Layout, Bengaluru)
4. Select "Full House" property type
5. Select "1 RK" from BHK Type dropdown
6. Click Search button
7. Verify 1RK properties are displayed in results

### Verification Strategies:
The test uses multiple fallback strategies to verify results:
1. **Primary Locator**: Exact match for "1 RK" text
2. **Fallback Locators**: Alternative XPath expressions
3. **Dynamic Search**: Runtime element discovery
4. **General Results**: Verification that search executed successfully
5. **URL Verification**: Confirmation that search parameters were applied

## Debugging Features

When tests fail, the framework automatically captures:
- Current page URL
- Page title
- Total number of page elements
- Elements containing "RK" text
- Property card information
- Detailed error messages and stack traces

## Browser Configuration

The framework includes optimized Chrome settings:
- Disabled notifications
- Disabled popup blocking
- Automation detection bypass
- Enhanced stability options

## Error Handling

The framework handles common Selenium issues:
- **Element Not Found**: Multiple locator strategies
- **Timing Issues**: Smart explicit waits
- **Page Load Problems**: Extended timeouts and retries
- **Dynamic Content**: Flexible waiting strategies

## Reporting

Tests generate detailed reports with:
- Step-by-step execution logs
- Debug information on failures
- Performance timing data
- Screenshot capture capabilities (can be extended)

## Troubleshooting

### Common Issues and Solutions:

1. **TimeoutException**: 
   - Check if page is loading properly
   - Verify element locators are correct
   - Increase wait timeouts if needed

2. **Element Not Found**:
   - Use the debugging output to understand page structure
   - Update locators based on current page design
   - Check if dynamic content loading is complete

3. **Browser Issues**:
   - Ensure Chrome browser is updated
   - Check WebDriverManager is downloading correct driver version
   - Verify browser options are compatible

## Extending the Framework

To add new test cases:
1. Create new test methods in `SearchPropertyTest` class
2. Add new page objects in `objectrepository` package
3. Update `data.properties` with new test data
4. Add new test classes to `testng.xml`

## Best Practices Implemented

- **Page Object Model**: Clean separation of test logic and page elements
- **Data-Driven Testing**: External configuration for test data
- **Explicit Waits**: Proper synchronization with dynamic content
- **Error Recovery**: Graceful handling of test failures
- **Logging**: Comprehensive test execution reporting
- **Maintainability**: Modular design for easy updates