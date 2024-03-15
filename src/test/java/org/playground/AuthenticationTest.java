package org.playground;

import org.playground.fixture.ConstData;
import org.playground.helpers.TestConfig;
import org.playground.pages.LoginPage;
import org.playground.pages.ProductsPage;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class AuthenticationTest extends BaseTest{
    @DataProvider(name = "negativeLoginData")
    public Object[][] logins(){
        return new Object[][] {{"", TestConfig.getPassword(), ConstData.MISSING_USER_ERROR_MESSAGE},
                {TestConfig.getStandardUsername(), "", ConstData.MISSING_PASSWORD_ERROR_MESSAGE},
                {ConstData.NOT_EXISTING_USER, TestConfig.getPassword(), ConstData.WRONG_CREDS_ERROR_MESSAGE},
                {TestConfig.getStandardUsername(), ConstData.WRONG_PASSWORD, ConstData.WRONG_CREDS_ERROR_MESSAGE},
                {TestConfig.getLockedUsername(), TestConfig.getPassword(), ConstData.LOCKED_USER_ERROR_MESSAGE}};
    }

    @Test
    public void checkPageStructure() {
        LoginPage loginPage = new LoginPage(page);
        Assert.assertEquals(loginPage.getTitle(), ConstData.MAIN_TITLE);
        Assert.assertEquals(loginPage.getLogoText(), ConstData.MAIN_TITLE);
    }

    @Test
    public void login() {
        LoginPage loginPage = new LoginPage(page);
        loginPage.fillCredentials(TestConfig.getStandardUsername(), TestConfig.getPassword());
        ProductsPage productsPage = loginPage.clickLogin();
        Assert.assertEquals(productsPage.getUrl(), TestConfig.getApplicationUrl() + ConstData.PRODUCTS_URL);
    }

    @Test(dataProvider = "negativeLoginData")
    public void negativeLogin(String username, String password, String expectedErrorMessage) {
        LoginPage loginPage = new LoginPage(page);
        loginPage.fillCredentials(username, password);
        loginPage.clickLogin();
        Assert.assertEquals(loginPage.getErrorMessage(), expectedErrorMessage);
    }
}
