package pl.edu.agh.mwo.invoice.product;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
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
        SimpleDateFormat dayFormatter = new SimpleDateFormat("dd");
        SimpleDateFormat monthFormatter = new SimpleDateFormat("MM");
        MyDate myDate = new MyDate(
                Integer.parseInt(dayFormatter.format(date)),
                Integer.parseInt(monthFormatter.format(date)));
        MyDate carpenterDay = new MyDate(19, 3);
        return myDate.day == carpenterDay.day && myDate.month == carpenterDay.month;
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
