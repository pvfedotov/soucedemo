package org.playground.blocks;

import com.microsoft.playwright.Page;

public class Footer {
    private Page page;
    private String twitterLink = "//a[text()='Twitter']";
    private String facebookLink = "//a[text()='Facebook']";
    private String linkedinLink = "//a[text()='LinkedIn']";
    private String footerCopyText = "div[class='footer_copy']";
    private String footer = "footer";

    public Footer(Page page) {
        this.page = page;
    }

    public boolean isTwitterLinkpresent() {
        return page.locator(footer).locator(twitterLink).isVisible();
    }
    public boolean isFacebookLinkpresent() {
        return page.locator(footer).locator(facebookLink).isVisible();
    }
    public boolean isLinkedinLinkpresent() {
        return page.locator(footer).locator(linkedinLink).isVisible();
    }

    public String getFooterCopyText() {
        return page.locator(footer).locator(footerCopyText).textContent();
    }

    public boolean isFooterVisible() {
        return page.isVisible(footer);
    }
}
