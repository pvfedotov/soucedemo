package org.playground;

import org.playground.blocks.BurgerMenu;
import org.playground.fixture.ConstData;
import org.playground.helpers.TestConfig;
import org.playground.pages.LoginPage;
import org.playground.pages.ProductsPage;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class BurgerMenuTest extends BaseTest{
    private BurgerMenu burgerMenu;

    @BeforeMethod
    public void login() {
        LoginPage loginPage = new LoginPage(page);
        loginPage.fillCredentials(TestConfig.getStandardUsername(), TestConfig.getPassword());
        ProductsPage productsPage = loginPage.clickLogin();
        burgerMenu = productsPage.getHeaderBlock().clickMenuButton();
    }

    @Test
    public void checkMenuStructure() {
        Assert.assertTrue(burgerMenu.isCloseMenuButtonVisible());
        Assert.assertTrue(burgerMenu.isResetAppStateButtonVisible());
        Assert.assertTrue(burgerMenu.isAboutButtonVisible());
        Assert.assertTrue(burgerMenu.isLogoutButtonVisible());
        Assert.assertTrue(burgerMenu.isAllItemsButtonVisible());
    }

    @Test
    public void checkLogout() {
        LoginPage loginPage = burgerMenu.logout();
        Assert.assertEquals(loginPage.getUrl(), TestConfig.getApplicationUrl());
    }

    @Test
    public void checkAllItems() {
        burgerMenu.allItems();
        Assert.assertTrue(true);
    }

    @Test
    public void checkAbout() {
        burgerMenu.about();
        Assert.assertEquals(page.url(), ConstData.ABOUT_URL);
        Assert.assertTrue(true);
    }

    @Test
    public void checkResetAppState() {
        burgerMenu.resetAppState();
        //TODO check if the app state is reset
    }
}
