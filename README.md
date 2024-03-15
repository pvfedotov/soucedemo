#SauceDemo Test Suite
This is test suite for SauceDemo application.

## Technologies
 - Java v17
 - Playwright - open-source automation library for browser testing
 - Maven - build automation tool
 - TestNG - testing framework
 - Allure - test report tool

### Other libraries
 - Jackson - JSON data-binding library
 - Lombok - Java library that automatically plugs into your editor and build tools, spicing up your java

## Features
 - Page Object Model
 - Allure report
 - Selenoid support
 - Cross-browser testing
 - Headless mode
 - Parallel testing

## How to run
### Prerequisites
 Set up environmetn variables:
 - SWAG_STANDARD_USER - standard username
 - SWAG_LOCKED_USER - locked username
 - SWAG_PASSWORD - user password

Run test `mvn test`

Generate allure report  - `allure serve target/allure-results`

##Next steps:
- Concider use Cucumber for better test readability
- Enable multi environments support
- Integrate with Selenium Grid https://playwright.dev/java/docs/selenium-grid
