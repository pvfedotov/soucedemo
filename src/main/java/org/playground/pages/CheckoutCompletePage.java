package org.playground.pages;

import com.microsoft.playwright.Page;

public class CheckoutCompletePage extends BasePage{
    private String completeHeader = "h2[class='complete-header']";
    private String completeText = "div[class='complete-text']";
    private String backHomeButton = "button[id='back-to-products']";

    public CheckoutCompletePage(Page page) {
        super(page);
    }

    public String getCompleteHeader() {
        return page.locator(completeHeader).first().textContent();
    }

    public String getCompleteText() {
        return page.locator(completeText).first().textContent();
    }

    public ProductsPage clickBackHome() {
        page.click(backHomeButton);
        return new ProductsPage(page);
    }
}
