package com.epam.training.fooddelivery.domain;

import java.math.BigDecimal;
import java.util.Objects;

public class Food {

    private String name;
    private BigDecimal price;

    public Food(String name, BigDecimal price) {
        this.name = name;
        this.price = price;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Food)) return false;
        Food food = (Food) o;
        return name.equals(food.name) && price.equals(food.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, price);
    }

    public BigDecimal getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return "Food{" +
                "name='" + name + '\'' +
                ", price=" + price +
                '}';
    }
}
