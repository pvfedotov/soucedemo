package org.playground.pages;

import com.microsoft.playwright.Page;

public class CheckoutPersonPage extends BasePage{
    private String firstName = "input[id='first-name']";
    private String lastName = "input[id='last-name']";
    private String postalCode = "input[id='postal-code']";
    private String errorText = "h3[data-test='error']";
    private String continueButton = "input[id='continue']";
    private String cancelButton = "button[id='cancel']";

    public CheckoutPersonPage(Page page) {
        super(page);
    }

    public void fillOutForm(String firstName, String lastName, String postalCode) {
        page.fill(this.firstName, firstName);
        page.fill(this.lastName, lastName);
        page.fill(this.postalCode, postalCode);
    }

    public CheckoutOverviewPage clickContinue() {
        page.click(continueButton);
        return new CheckoutOverviewPage(page);
    }

    public CartPage clickCancel() {
        page.click(cancelButton);
        return new CartPage(page);
    }

    public String getErrorMessage() {
        return page.textContent(errorText);
    }
}
