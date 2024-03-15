package org.playground.pages;

import com.microsoft.playwright.Page;
import org.playground.models.Product;

public class ProductPage extends BasePage{
    private String inventoryItemName = "//div[contains(@class, 'inventory_details_name')]";
    private String inventoryItemPrice = "div[class='inventory_details_price']";
    private String inventoryItemDescription = "//div[contains(@class, 'inventory_details_desc ')]";
    private String inventoryItemImageLink = "img[class='inventory_details_img']";
    private String inventoryAddToCartButton = "//button[text()='Add to cart']";
    private String inventoryRemoveFromCartButton = "//button[text()='Remove']";

    public ProductPage(Page page) {
        super(page);
    }

    public Product getProductDetails() {
        Product product = new Product();
        product.setName(page.locator(inventoryItemName).first().textContent());
        product.setDescription(page.locator(inventoryItemDescription).first().textContent());
        product.setPrice(Float.parseFloat(page.locator(inventoryItemPrice).first().textContent().replace("$", "")));
        product.setImage(page.locator(inventoryItemImageLink).first().getAttribute("src"));
        return product;
    }

    public void addProductToCart() {
        page.locator(inventoryAddToCartButton).click();
    }

    public void removeProductFromCart() {
        page.locator(inventoryRemoveFromCartButton).click();
    }
}
