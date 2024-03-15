package org.playground.pages;

import com.microsoft.playwright.Page;
import org.playground.models.Product;

import java.util.List;

public class ProductsPage extends BasePage{
    private String inventoryItem = "div[class='inventory_item']";
    private String inventoryItemName = "div[class='inventory_item_name ']";
    private String inventoryItemPrice = "div[class='inventory_item_price']";
    private String inventoryItemDescription = "div[class='inventory_item_desc']";
    private String inventoryItemImageLink = "img[class='inventory_item_img']";
    private String inventoryAddToCartButton = "//button[text()='Add to cart']";
    private String inventoryRemoveFromCartButton = "//button[text()='Remove']";
    private String inventoryList = "div[class='inventory_list']";

    public ProductsPage(Page page) {
        super(page);
    }

    public List<Product> getAllProducts() {
     List<Product> products = page.locator(inventoryItem).all().stream().map(item -> {
            Product product = new Product();
            product.setName(item.locator(inventoryItemName).first().textContent());
            product.setDescription(item.locator(inventoryItemDescription).first().textContent());
            product.setPrice(Float.parseFloat(item.locator(inventoryItemPrice).first().textContent().replace("$", "")));
            product.setImage(item.locator(inventoryItemImageLink).first().getAttribute("src"));
            return product;
        }).toList();
        return products;
    }

    public boolean isProductListVisible() {
        return page.locator(inventoryList).isVisible();
    }

    public ProductPage openProduct(String productName) {
        page.locator(inventoryItem).all().stream()
                .filter(item -> item.locator(inventoryItemName).first().textContent().equals(productName))
                .findFirst().get().locator(inventoryItemName).click();
        return new ProductPage(page);
    }

    public void addProductToCart(String productName) {
        page.locator(inventoryItem).all().stream()
                .filter(item -> item.locator(inventoryItemName).first().textContent().equals(productName))
                .findFirst().get().locator(inventoryAddToCartButton).click();
    }

    public void removeProductFromCart(String productName) {
        page.locator(inventoryItem).all().stream()
                .filter(item -> item.locator(inventoryItemName).textContent().equals(productName))
                .findFirst().get().locator(inventoryRemoveFromCartButton).click();
    }

}
