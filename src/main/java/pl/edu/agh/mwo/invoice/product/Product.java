package pl.edu.agh.mwo.invoice.product;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

public abstract class Product {
    private final String name;

    private final BigDecimal price;

    private final BigDecimal taxPercent;

    public boolean isCarpenterDay = false;
    //shall not be here in release prod! only for test...

    private static final BigDecimal excise = new BigDecimal("5.56");

    protected Product(String name, BigDecimal price, BigDecimal tax) {
        if (name == null || name.equals("") || price == null || tax == null
                || tax.compareTo(new BigDecimal(0)) < 0
                || price.compareTo(new BigDecimal(0)) < 0) {
            throw new IllegalArgumentException();
        }
        this.name = name;
        this.price = price;
        this.taxPercent = tax;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public BigDecimal getTaxPercent() {
        return taxPercent;
    }

    public BigDecimal getPriceWithTax() {
        if (isCarpenterDay) { //replace with isCarpenterDay()
            if (this.getClass().equals(FuelCanister.class)) {
                return price;
            } else {
                if (!this.getClass().equals(BottleOfWine.class)) {
                    return price.multiply(taxPercent).add(price);
                }
                return price.multiply(taxPercent).add(price).add(excise);
            }
        } else {
            if (!this.getClass().equals(BottleOfWine.class)
                && !this.getClass().equals(FuelCanister.class)) {
                return price.multiply(taxPercent).add(price);
            } else {
                return price.multiply(taxPercent).add(price).add(excise);
            }
        }
    } //complexity :P

    public boolean isCarpenterDay() {
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        String res = formatter.format(date);
        //CHECKSTYLE:OFF
        return res.charAt(3) == '0'
               && res.charAt(4) == '3'
               && res.charAt(0) == '1'
               && res.charAt(1) == '9';
        //CHECKSTYLE:ON
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Product product = (Product) o;
        return Objects.equals(name, product.name)
               && Objects.equals(price, product.price)
               && Objects.equals(taxPercent, product.taxPercent);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, price, taxPercent);
    }
}
