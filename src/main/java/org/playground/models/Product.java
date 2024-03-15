package org.playground.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.Comparator;

@Getter@Setter
public class Product {
    @JsonProperty("name")
    private String name;
    @JsonProperty("description")
    private String description;
    @JsonProperty("price")
    private float price;
    @JsonProperty("image")
    private String image;

    public static Comparator<Product> getPriceComparator() {
        return new Comparator<Product>() {
            @Override
            public int compare(Product p1, Product p2) {
                int compare = (int) (p1.getPrice() - p2.getPrice());
                if (compare == 0) {
                    return p1.getName().compareTo(p2.getName());
                } else {
                    return compare;
                }
            }
        };
    }

    public static Comparator<Product> getNameComparator() {
        return new Comparator<Product>() {
            @Override
            public int compare(Product p1, Product p2) {
                return p1.getName().compareTo(p2.getName());
            }
        };
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Product)) {
            return false;
        }
        Product that = (Product) obj;
        return getName().equals(that.getName())
                && getDescription().equals(that.getDescription())
                && getPrice()== that.getPrice()
                && (that.getImage().startsWith(getImage()) || getImage().startsWith(that.getImage()));
    }
}
