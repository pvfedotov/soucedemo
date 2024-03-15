package org.playground.blocks;

import com.microsoft.playwright.Page;
import org.playground.pages.CartPage;

public class Header {
    private Page page;
    private BurgerMenu burgerMenu;
    private String headerText = "div[class='app_logo']";
    private String cartIcon = "div[id='shopping_cart_container']";
    private String titleText = "span[class='title']";
    private String menuButton = "button[id='react-burger-menu-btn']";
    private String productsSortingSelect = "select[class='product_sort_container']";
    private String headerContainer = "div[id='header_container']";
    private String backToProductsButton = "button[id='back-to-products']";
    private String shoppingCartBage = "span[class='shopping_cart_badge']";

    public Header(Page page) {
        this.page = page;
    }

    public String getHeaderText() {
        return page.locator(headerText).textContent();
    }

    public CartPage clickCartIcon() {
        page.click(cartIcon);
        return new CartPage(page);
    }

    public String getTitleText() {
        return page.locator(titleText).textContent();
    }

    public BurgerMenu clickMenuButton() {
        page.click(menuButton);
        burgerMenu = new BurgerMenu(page);
        return burgerMenu;
    }

    public void selectProductsSorting(String sortingOption) {
        page.selectOption(productsSortingSelect, sortingOption);
    }

    public BurgerMenu getBurgerMenu() {
        return burgerMenu;
    }

    public boolean isHeaderVisible() {
        return page.isVisible(headerContainer);
    }

    public boolean isCartIconVisible() {
        return page.isVisible(cartIcon);
    }

    public boolean isMenuButtonVisible() {
        return page.isVisible(menuButton);
    }

    public boolean isProductsSortingSelectVisible() {
        return page.isVisible(productsSortingSelect);
    }

    public void clickBackToProductsButton() {
        page.click(backToProductsButton);
    }

    public int getShoppingCartItemsAmount() {
        return page.locator(shoppingCartBage)
                .isVisible() ? Integer.parseInt(page.locator(shoppingCartBage).textContent()) : 0;
    }
}
