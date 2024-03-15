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

import java.util.List;

public class CheckoutPersonTest extends BaseTest{
    CheckoutPersonPage checkoutPersonPage;
    @BeforeClass
    public void login() {
        LoginPage loginPage = new LoginPage(page);
        loginPage.fillCredentials(TestConfig.getStandardUsername(), TestConfig.getPassword());
        ProductsPage productsPage = loginPage.clickLogin();
    }

    @BeforeMethod
    public void openCheckoutPage() {
        CartPage cartPage = new Header(page).clickCartIcon();
        checkoutPersonPage = cartPage.clickCheckout();
        Assert.assertEquals(cartPage.getHeaderBlock().getTitleText(), "Checkout: Your Information");
    }

    @DataProvider(name = "negativePersonalData")
    public Object[][] personalData(){
        return new Object[][] {{"", ConstData.LAST_NAME, ConstData.POSTAL_CODE, ConstData.NO_FIRST_NAME_ERROR_MESSAGE},
                {ConstData.FIRST_NAME, "", ConstData.POSTAL_CODE, ConstData.NO_LAST_NAME_ERROR_MESSAGE},
                {ConstData.FIRST_NAME, ConstData.LAST_NAME, "", ConstData.NO_ZIP_ERROR_MESSAGE}};
    }

    @Test(dataProvider = "negativePersonalData")
    public void negativeLogin(String firstName, String lastName, String postalCode, String expectedErrorMessage) {
        checkoutPersonPage.fillOutForm(firstName, lastName, postalCode);
        checkoutPersonPage.clickContinue();
        Assert.assertEquals(checkoutPersonPage.getErrorMessage(), expectedErrorMessage);
    }

    @Test
    public void negativeMaximumLengthTest() {
        //TODO - implement maximum length test;
    }

    @Test
    public void cancelCheck() {
        CartPage cartPage = checkoutPersonPage.clickCancel();
        Assert.assertEquals(cartPage.getUrl(), TestConfig.getApplicationUrl() + ConstData.CART_URL);
    }

    @Test
    public void continueCheck() {
        checkoutPersonPage.fillOutForm(ConstData.FIRST_NAME, ConstData.LAST_NAME, ConstData.POSTAL_CODE);
        CheckoutOverviewPage overviewPage = checkoutPersonPage.clickContinue();
        Assert.assertEquals(overviewPage.getUrl(), TestConfig.getApplicationUrl() + ConstData.CHECKOUT_2_URL);
    }
}
