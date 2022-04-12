package pl.edu.agh.mwo.invoice;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import pl.edu.agh.mwo.invoice.product.Product;

public class Invoice {

    private static final Integer sizeT = 1000000000;
    private String invoiceNumber = getRandomNumberString();
    private Map<Product, Integer> products = new HashMap<Product, Integer>();

    public void addProduct(Product product) {
        addProduct(product, 1);
    }

    public void addProduct(Product product, Integer quantity) {
        if (product == null || quantity <= 0) {
            throw new IllegalArgumentException();
        }
        if (products.containsKey(product)) {
            products.replace(product, products.get(product) + quantity);
        } else {
            products.put(product, quantity);
        }
    }

    public BigDecimal getNetTotal() {
        BigDecimal totalNet = BigDecimal.ZERO;
        for (Product product : products.keySet()) {
            BigDecimal quantity = new BigDecimal(products.get(product));
            totalNet = totalNet.add(product.getPrice().multiply(quantity));
        }
        return totalNet;
    }

    public BigDecimal getTaxTotal() {
        return getGrossTotal().subtract(getNetTotal());
    }

    public BigDecimal getGrossTotal() {
        BigDecimal totalGross = BigDecimal.ZERO;
        for (Product product : products.keySet()) {
            BigDecimal quantity = new BigDecimal(products.get(product));
            totalGross = totalGross.add(product.getPriceWithTax().multiply(quantity));
        }
        return totalGross;
    }

    public String getRandomNumberString() {
        Random rnd = new Random();
        return String.format("%09d", rnd.nextInt(sizeT));
    }

    public String getInvoiceNumber() {
        return invoiceNumber;
    }

    public String getProductsList() {
        StringBuilder ans = new StringBuilder();
        for (Product p : products.keySet()) {
            ans.append(String.format(
                "Name: %s, count: %d, price: %s \n", p.getName(), products.get(p), p.getPrice())
            );
        }
        return ans.toString();
    }
}
