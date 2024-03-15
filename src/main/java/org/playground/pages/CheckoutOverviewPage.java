package org.playground.pages;

import com.microsoft.playwright.Page;
import org.playground.models.Product;

import java.util.List;

public class CheckoutOverviewPage extends BasePage{
    private String finishButton = "button[id='finish']";
    private String cancelButton = "button[id='cancel']";
    private String cartItem = "div[class='cart_item']";
    private String cartItemName = "div[class='inventory_item_name']";
    private String cartItemDescription = "div[class='inventory_item_desc']";
    private String cartItemPrice = "div[class='inventory_item_price']";
    private String paymentInformation =
            "//div[text()='Payment Information']/following-sibling::div[@class='summary_value_label'][1]";
    private String shippingInformation =
            "//div[text()='Shipping Information']/following-sibling::div[@class='summary_value_label'][1]";
    private String itemSubtotal = "div[class='summary_subtotal_label']";
    private String tax = "div[class='summary_tax_label']";
    private String total = "div[class='summary_info_label summary_total_label']";

    public CheckoutOverviewPage(Page page) {
        super(page);
    }

    public CheckoutCompletePage clickFinish() {
        page.click(finishButton);
        return new CheckoutCompletePage(page);
    }

    public ProductsPage clickCancel() {
        page.click(cancelButton);
        return new ProductsPage(page);
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

    public String getPaymentInformation() {
        return page.locator(paymentInformation).first().textContent();
    }

    public String getShippingInformation() {
        return page.locator(shippingInformation).first().textContent();
    }

    public String getItemSubtotal() {
        return page.locator(itemSubtotal).first().textContent().replace("Item total: $", "");
    }

    public String getTax() {
        return page.locator(tax).first().textContent().replace("Tax: $", "");
    }

    public String getTotal() {
        return page.locator(total).first().textContent().replace("Total: $", "");
    }
}
