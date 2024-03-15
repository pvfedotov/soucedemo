package org.playground;

import org.playground.blocks.Header;
import org.playground.fixture.ConstData;
import org.playground.helpers.DataHelper;
import org.playground.helpers.TestConfig;
import org.playground.models.Product;
import org.playground.pages.CartPage;
import org.playground.pages.CheckoutPersonPage;
import org.playground.pages.LoginPage;
import org.playground.pages.ProductsPage;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;

public class CartPageTest extends BaseTest{
    private List<Product> expectedProducts;
    private CartPage cartPage;

    @BeforeClass
    public void login() {
        expectedProducts = DataHelper
                .loadJsonFromFileToListOfProducts(DataHelper.getFileFromResources(ConstData.ALL_PRODUCTS_JSON));
        expectedProducts.forEach(product -> product.setImage(""));
        LoginPage loginPage = new LoginPage(page);
        loginPage.fillCredentials(TestConfig.getStandardUsername(), TestConfig.getPassword());
        ProductsPage productsPage = loginPage.clickLogin();
        expectedProducts.forEach(product -> productsPage.addProductToCart(product.getName()));
    }

    @BeforeMethod
    public void openCartPage() {
        cartPage = new Header(page).clickCartIcon();
        Assert.assertEquals(cartPage.getHeaderBlock().getTitleText(), "Your Cart");
    }

    @Test
    public void cartItemsCheck() {
        List<Product> actualProducts = cartPage.getAllProducts();
        expectedProducts.forEach(product -> Assert.assertTrue(actualProducts.contains(product)));
    }

    @Test
    public void removeCartItemCheck() {
        Product removedProduct = expectedProducts.get(0);
        cartPage.removeProductFromCart(removedProduct.getName());
        expectedProducts.remove(removedProduct);
        List<Product> actualProducts = cartPage.getAllProducts();
        expectedProducts.forEach(product -> Assert.assertTrue(actualProducts.contains(product)));
    }

    @Test
    public void continueShoppingCheck() {
        ProductsPage productsPage = cartPage.clickContinueShopping();
        Assert.assertEquals(productsPage.getUrl(), TestConfig.getApplicationUrl() + ConstData.PRODUCTS_URL);
    }

    @Test
    public void checkoutCheck() {
        CheckoutPersonPage checkPage = cartPage.clickCheckout();
        Assert.assertEquals(checkPage.getUrl(), TestConfig.getApplicationUrl() + ConstData.CHECKOUT_1_URL);
    }
}
