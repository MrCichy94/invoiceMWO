package pl.edu.agh.mwo.invoice;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collection;

import pl.edu.agh.mwo.invoice.product.Product;

public class Invoice {

    public Invoice() {
        products = new ArrayList<>();
    }

    private final Collection<Product> products;

    public void addProduct(Product product) {
        products.add(product);
    }

    public void addProduct(Product product, Integer quantity) {
        if (quantity <= 0) throw new IllegalArgumentException();
        for (int i = 0; i < quantity; i++) {
            products.add(product);
        }
    }

    public BigDecimal getSubtotal() {
        BigDecimal sum = BigDecimal.ZERO;
        for (Product product : products) {
            sum = sum.add(product.getPrice());
        }
        return sum;
    }

    public BigDecimal getTax() {
        BigDecimal sum = BigDecimal.ZERO;
        for (Product product : products) {
            BigDecimal withVat = product.getPrice();
            BigDecimal withoutVat = product.getPriceWithTax();
            //sum.add(withVat.add(withoutVat.negate()));
            sum = sum.add(withVat.negate().add(withoutVat));
        }
        return sum;
    }

    public BigDecimal getTotal() {
        BigDecimal sum = BigDecimal.ZERO;
        for (Product product : products) {
            sum = sum.add(product.getPriceWithTax());
        }
        return sum;
    }
}
