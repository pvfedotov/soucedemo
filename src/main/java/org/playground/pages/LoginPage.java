package org.playground.pages;

import com.microsoft.playwright.Page;
import org.playground.helpers.TestConfig;

public class LoginPage {
    private Page page;
    private String userNameInput = "input[id='user-name']";
    private String passwordInput = "input[id='password']";
    private String loginButton = "input[id='login-button']";
    private String logoText = "div[class='login_logo']";
    private String errorText = "h3[data-test='error']";

    public LoginPage(Page page) {
        page.navigate(TestConfig.getApplicationUrl());
        this.page = page;
    }

    public String getTitle() {
        return page.title();
    }

    public void fillCredentials(String username, String password) {
        page.fill(userNameInput, username);
        page.fill(passwordInput, password);
    }

    public ProductsPage clickLogin() {
        page.click(loginButton);
        return new ProductsPage(page);
    }

    public String getLogoText() {
        return page.locator(logoText).textContent();
    }

    public String getErrorMessage() {
        return page.locator(errorText).textContent();
    }

    public String getUrl() {
        return page.url();
    }
}
