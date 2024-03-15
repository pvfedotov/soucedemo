package org.playground;

import org.playground.fixture.ConstData;
import org.playground.helpers.DataHelper;
import org.playground.helpers.TestConfig;
import org.playground.models.Product;
import org.playground.pages.LoginPage;
import org.playground.pages.ProductPage;
import org.playground.pages.ProductsPage;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ProductsTest extends BaseTest{
    private ProductsPage productsPage;
    private List<Product> expectedProducts;

    @BeforeClass
    public void setup() {
        expectedProducts = DataHelper
                .loadJsonFromFileToListOfProducts(DataHelper.getFileFromResources(ConstData.ALL_PRODUCTS_JSON));
        LoginPage loginPage = new LoginPage(page);
        loginPage.fillCredentials(TestConfig.getStandardUsername(), TestConfig.getPassword());
        productsPage = loginPage.clickLogin();
    }

    @DataProvider(name = "sortingData")
    public Object[][] sorting(){
        return new Object[][] {{Product.getNameComparator(), "Name (A to Z)"},
                {Product.getNameComparator().reversed(), "Name (Z to A)"},
                {Product.getPriceComparator(), "Price (low to high)"},
                {Product.getPriceComparator().reversed(), "Price (high to low)"}};
    }

    @Test
    public void checkPageStructure() {
        Assert.assertTrue(productsPage.getHeaderBlock().isHeaderVisible());
        Assert.assertTrue(productsPage.getFooterBlock().isFooterVisible());
        Assert.assertTrue(productsPage.isProductListVisible());
        Assert.assertEquals(productsPage.getTitle(), ConstData.MAIN_TITLE);
        Assert.assertEquals(productsPage.getLogoText(), ConstData.MAIN_TITLE);
        Assert.assertEquals(productsPage.getFooterBlock().getFooterCopyText(), ConstData.FOOTER_TEXT);
        Assert.assertTrue(productsPage.getFooterBlock().isFacebookLinkpresent());
        Assert.assertTrue(productsPage.getFooterBlock().isTwitterLinkpresent());
        Assert.assertTrue(productsPage.getFooterBlock().isLinkedinLinkpresent());
        Assert.assertTrue(productsPage.getHeaderBlock().isCartIconVisible());
        Assert.assertTrue(productsPage.getHeaderBlock().isMenuButtonVisible());
        Assert.assertTrue(productsPage.getHeaderBlock().isProductsSortingSelectVisible());
        Assert.assertEquals(productsPage.getHeaderBlock().getTitleText(), "Products");
    }

    @Test(dataProvider = "sortingData")
    public void checkSorting(Comparator<Product> comparator, String sortingName) {
        Collections.sort(expectedProducts, comparator);
        productsPage.getHeaderBlock().selectProductsSorting(sortingName);
        List<Product> actualProducts = productsPage.getAllProducts();
        Assert.assertEquals(actualProducts, expectedProducts);
    }

    @Test
    public void checkProductDetails() {
        expectedProducts.stream().forEach(product -> {
            ProductPage productPage = productsPage.openProduct(product.getName());
            Product actualProductDetails = productPage.getProductDetails();
            Assert.assertEquals(actualProductDetails, product);
            productPage.getHeaderBlock().clickBackToProductsButton();
        });
    }

    @Test
    public void addAndRemoveItemsFromProducts() {
        int productsInCart = productsPage.getHeaderBlock().getShoppingCartItemsAmount();
        productsPage.addProductToCart(expectedProducts.get(0).getName());
        productsInCart++;
        Assert.assertEquals(productsPage.getHeaderBlock().getShoppingCartItemsAmount(), productsInCart);

        productsPage.addProductToCart(expectedProducts.get(1).getName());
        productsInCart++;
        Assert.assertEquals(productsPage.getHeaderBlock().getShoppingCartItemsAmount(), productsInCart);

        productsPage.removeProductFromCart(expectedProducts.get(0).getName());
        productsInCart--;
        Assert.assertEquals(productsPage.getHeaderBlock().getShoppingCartItemsAmount(), productsInCart);
    }

    @Test
    public void addAndRemoveItemsFromProduct() {
        int productsInCart = productsPage.getHeaderBlock().getShoppingCartItemsAmount();
        ProductPage product = productsPage.openProduct(expectedProducts.get(2).getName());
        product.addProductToCart();
        product.getHeaderBlock().clickBackToProductsButton();
        productsInCart++;
        Assert.assertEquals(productsPage.getHeaderBlock().getShoppingCartItemsAmount(), productsInCart);

        product = productsPage.openProduct(expectedProducts.get(3).getName());
        product.addProductToCart();
        productsInCart++;
        Assert.assertEquals(productsPage.getHeaderBlock().getShoppingCartItemsAmount(), productsInCart);

        product.removeProductFromCart();
        product.getHeaderBlock().clickBackToProductsButton();
        productsInCart--;
        Assert.assertEquals(productsPage.getHeaderBlock().getShoppingCartItemsAmount(), productsInCart);
    }
}
