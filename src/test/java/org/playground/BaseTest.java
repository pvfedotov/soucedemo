package org.playground;


import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.Page;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;
import org.playground.helpers.PlaywrightDriver;
import org.playground.helpers.TestConfig;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

public class BaseTest {
    protected Logger logger;
    protected Page page;
    protected BrowserContext context;
    protected PlaywrightDriver driver;

    @Parameters({"browser", "headless"})
    @BeforeClass
    public void setUp(@Optional String browser, @Optional String headless) {
        TestConfig.initConfig(browser, headless);

        logger = LogManager.getLogger("");
        DOMConfigurator.configure("src/main/resources/log4j.xml");

        driver = new PlaywrightDriver();
        context = driver.getContext();
        page = context.newPage();
    }

    @AfterClass
    public void tearDown() {
        context.close();
    }
}

