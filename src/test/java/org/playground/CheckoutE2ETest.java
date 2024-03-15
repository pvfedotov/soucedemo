package org.playground;

import org.playground.blocks.Header;
import org.playground.fixture.ConstData;
import org.playground.helpers.DataHelper;
import org.playground.helpers.TestConfig;
import org.playground.models.Product;
import org.playground.pages.*;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.text.DecimalFormat;
import java.util.List;

public class CheckoutE2ETest extends BaseTest{
    @Test
    public void orderCheckoutE2E() {
        List<Product> expectedProducts = DataHelper
                .loadJsonFromFileToListOfProducts(DataHelper.getFileFromResources(ConstData.ALL_PRODUCTS_JSON));

        LoginPage loginPage = new LoginPage(page);
        loginPage.fillCredentials(TestConfig.getStandardUsername(), TestConfig.getPassword());
        ProductsPage productsPage = loginPage.clickLogin();
        expectedProducts.forEach(product -> productsPage.addProductToCart(product.getName()));
        Assert.assertEquals(productsPage.getHeaderBlock().getShoppingCartItemsAmount(), expectedProducts.size());
        CartPage cartPage = productsPage.getHeaderBlock().clickCartIcon();
        expectedProducts.forEach(product -> product.setImage(""));
        List<Product> actualProducts = cartPage.getAllProducts();
        expectedProducts.forEach(product -> Assert.assertTrue(actualProducts.contains(product)));
        CheckoutPersonPage checkoutPersonPage = cartPage.clickCheckout();
        checkoutPersonPage.fillOutForm(ConstData.FIRST_NAME, ConstData.LAST_NAME, ConstData.POSTAL_CODE);
        CheckoutOverviewPage overviewPage = checkoutPersonPage.clickContinue();
        Assert.assertEquals(overviewPage.getHeaderBlock().getTitleText(), "Checkout: Overview");

        float expectedSubtotal = expectedProducts.stream().map(Product::getPrice).reduce(0f, Float::sum);
        float expectedTax = expectedSubtotal * 0.08f;
        float expectedTotal = expectedSubtotal + expectedTax;

        DecimalFormat df = new DecimalFormat();
        df.setMaximumFractionDigits(2);

        Assert.assertEquals(Float.parseFloat(overviewPage.getItemSubtotal()),
                Float.parseFloat(df.format(expectedSubtotal)));
        Assert.assertEquals(Float.parseFloat(overviewPage.getTax()), Float.parseFloat(df.format(expectedTax)));
        Assert.assertEquals(Float.parseFloat(overviewPage.getTotal()), Float.parseFloat(df.format(expectedTotal)));

        Assert.assertEquals(overviewPage.getShippingInformation(), ConstData.DELIVERY);
        Assert.assertEquals(overviewPage.getPaymentInformation(), ConstData.PAYMENT);

        CheckoutCompletePage completePage = overviewPage.clickFinish();

        Assert.assertEquals(completePage.getCompleteHeader(), ConstData.CHECKOUT_COMPLETE_HEADER);
        Assert.assertEquals(completePage.getCompleteText(), ConstData.CHECKOUT_COMPLETE_TEXT);

        completePage.clickBackHome();
        Assert.assertEquals(productsPage.getHeaderBlock().getShoppingCartItemsAmount(), 0);
    }
}
