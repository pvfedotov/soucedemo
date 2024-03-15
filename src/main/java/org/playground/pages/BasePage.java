package org.playground.pages;

import com.microsoft.playwright.Page;
import org.playground.blocks.Footer;
import org.playground.blocks.Header;

public abstract class BasePage {
    protected Page page;
    private Header header;
    private Footer footer;
    private String logoText = "div[class='app_logo']";

    public BasePage(Page page) {
        this.page = page;
        header = new Header(page);
        footer = new Footer(page);
    }

    public Header getHeaderBlock() {
        return header;
    }

    public Footer getFooterBlock() {
        return footer;
    }

    public String getTitle() {
        return page.title();
    }

    public String getUrl() {
        return page.url();
    }

    public String getLogoText() {
        return page.locator(logoText).textContent();
    }
}
