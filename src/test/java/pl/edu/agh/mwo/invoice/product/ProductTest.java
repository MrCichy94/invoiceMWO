package pl.edu.agh.mwo.invoice.product;

import java.math.BigDecimal;

import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;

public class ProductTest {

    @Test
    public void testProductNameIsCorrect() {
        Product product = new OtherProduct("buty", new BigDecimal("100.0"));
        Assert.assertEquals("buty", product.getName());
    }

    @Test
    public void testProductPriceAndTaxWithDefaultTax() {
        Product product = new OtherProduct("Ogorki", new BigDecimal("100.0"));
        Assert.assertThat(new BigDecimal("100"), Matchers.comparesEqualTo(product.getPrice()));
        Assert.assertThat(new BigDecimal("0.23"), Matchers.comparesEqualTo(product.getTaxPercent()));
    }

    @Test
    public void testProductPriceAndTaxWithDairyProduct() {
        Product product = new DairyProduct("Szarlotka", new BigDecimal("100.0"));
        Assert.assertThat(new BigDecimal("100"), Matchers.comparesEqualTo(product.getPrice()));
        Assert.assertThat(new BigDecimal("0.08"), Matchers.comparesEqualTo(product.getTaxPercent()));
    }

    @Test
    public void testPriceWithTax() {
        Product product = new DairyProduct("Oscypek", new BigDecimal("100.0"));
        Assert.assertThat(new BigDecimal("108"), Matchers.comparesEqualTo(product.getPriceWithTax()));
    }

    @Test
    public void testPriceWithTaxAndExciseFuel() {
        Product product = new FuelCanister("Cannister", new BigDecimal("100.0"));
        Assert.assertThat(new BigDecimal("113.56"), Matchers.comparesEqualTo(product.getPriceWithTax()));
    }

    @Test
    public void testPriceWithTaxAndExciseWine() {
        Product product = new BottleOfWine("Wine", new BigDecimal("100.0"));
        Assert.assertThat(new BigDecimal("113.56"), Matchers.comparesEqualTo(product.getPriceWithTax()));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testProductWithNullName() {
        new OtherProduct(null, new BigDecimal("100.0"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testProductWithEmptyName() {
        new TaxFreeProduct("", new BigDecimal("100.0"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testProductWithNullPrice() {
        new DairyProduct("Banany", null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testProductWithNegativePrice() {
        new TaxFreeProduct("Mandarynki", new BigDecimal("-1.00"));
    }

    @Test
    public void testCarpenterDay() {
        Product product = new FuelCanister("DIESEL", new BigDecimal("100.0"));
        product.isCarpenterDay = true;
        Assert.assertThat(new BigDecimal("100"), Matchers.comparesEqualTo(product.getPriceWithTax()));
    }

    @Test
    public void testCarpenterDayOtherProducts() {
        Product product = new BottleOfWine("DIESEL", new BigDecimal("100.0"));
        product.isCarpenterDay = true;
        Assert.assertThat(new BigDecimal("113.56"), Matchers.comparesEqualTo(product.getPriceWithTax()));
    }

    @Test
    public void testCarpenterDayOtherProductsWithNoExcise() {
        Product product = new DairyProduct("Somethu", new BigDecimal("100.0"));
        product.isCarpenterDay = true;
        Assert.assertThat(new BigDecimal("108"), Matchers.comparesEqualTo(product.getPriceWithTax()));
    }
}
