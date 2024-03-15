package org.playground.helpers;

import com.microsoft.playwright.*;
import lombok.Getter;

public class PlaywrightDriver {
    private Playwright playwright;
    private Browser browser;
    @Getter
    private BrowserContext context;

    public PlaywrightDriver() {
        playwright = Playwright.create();
        browser = initBrowser();
        context = browser.newContext();
    }

    private Browser initBrowser() {
        String browserName = TestConfig.getBrowser() == null ? "chromium" :  TestConfig.getBrowser();
        Boolean headless = TestConfig.isHeadless() == null ? false : TestConfig.isHeadless();

        switch (browserName.toLowerCase()) {
            case "chromium":
                return playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(headless));
            case "firefox":
                return playwright.firefox().launch(new BrowserType.LaunchOptions().setHeadless(headless));
            case "safari":
                return playwright.webkit().launch(new BrowserType.LaunchOptions().setHeadless(headless));
            case "chrome":
                return playwright.chromium()
                        .launch(new BrowserType.LaunchOptions().setChannel("chrome").setHeadless(headless));
            case "edge":
                return playwright.chromium()
                        .launch(new BrowserType.LaunchOptions().setChannel("msedge").setHeadless(headless));
            default:
                return null;
        }
    }
}
