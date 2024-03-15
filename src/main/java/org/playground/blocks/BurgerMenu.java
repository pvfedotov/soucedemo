package org.playground.blocks;

import com.microsoft.playwright.Page;
import org.playground.pages.LoginPage;

public class BurgerMenu {
    private Page page;
    private String resetAppState = "a[id='reset_sidebar_link']";
    private String allItems = "a[id='inventory_sidebar_link']";
    private String about = "a[id='about_sidebar_link']";
    private String logout = "a[id='logout_sidebar_link']";
    private String closeMenu = "button[id='react-burger-cross-btn']";

    BurgerMenu(Page page) {
        this.page = page;
    }

    public void resetAppState() {
        page.click(resetAppState);
    }
    public void allItems() {
        page.click(allItems);
    }
    public void about() {
        page.click(about);
    }
    public LoginPage logout() {
        page.click(logout);
        return new LoginPage(page);
    }
    public void closeMenu() {
        page.click(closeMenu);
    }

    public boolean isCloseMenuButtonVisible() {
        return page.isVisible(closeMenu);
    }

    public boolean isResetAppStateButtonVisible() {
        return page.isVisible(resetAppState);
    }

    public boolean isAllItemsButtonVisible() {
        return page.isVisible(allItems);
    }

    public boolean isAboutButtonVisible() {
        return page.isVisible(about);
    }

    public boolean isLogoutButtonVisible() {
        return page.isVisible(logout);
    }
}
