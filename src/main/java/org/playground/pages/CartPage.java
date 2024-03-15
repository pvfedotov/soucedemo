package org.playground.pages;

import com.microsoft.playwright.Page;
import org.playground.models.Product;

import java.util.List;

public class CartPage extends BasePage{
    private String cartItem = "div[class='cart_item']";
    private String checkoutButton = "button[id='checkout']";
    private String continueShoppingButton = "button[id='continue-shopping']";
    private String cartItemName = "div[class='inventory_item_name']";
    private String cartItemDescription = "div[class='inventory_item_desc']";
    private String cartItemPrice = "div[class='inventory_item_price']";
    private String cartItemRemoveButton = "//button[text()='Remove']";


    public CartPage(Page page) {
        super(page);
    }

    public List<Product> getAllProducts() {
        List<Product> products = page.locator(cartItem).all().stream().map(item -> {
            Product product = new Product();
            product.setName(item.locator(cartItemName).first().textContent());
            product.setDescription(item.locator(cartItemDescription).first().textContent());
            product.setPrice(Float.parseFloat(item.locator(cartItemPrice).first().textContent().replace("$", "")));
            product.setImage("");
            return product;
        }).toList();
        return products;
    }

    public void removeProductFromCart(String productName) {
        page.locator(cartItem).all().stream()
                .filter(item -> item.locator(cartItemName).first().textContent().equals(productName))
                .findFirst().get().locator(cartItemRemoveButton).click();
    }

    public CheckoutPersonPage clickCheckout() {
        page.click(checkoutButton);
        return new CheckoutPersonPage(page);
    }

    public ProductsPage clickContinueShopping() {
        page.click(continueShoppingButton);
        return new ProductsPage(page);
    }
}
