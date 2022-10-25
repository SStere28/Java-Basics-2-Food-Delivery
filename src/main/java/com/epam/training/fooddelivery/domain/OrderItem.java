package com.epam.training.fooddelivery.domain;

import java.math.BigDecimal;
import java.util.Objects;

public class OrderItem {

    private Food food;
    private int amount;
    private BigDecimal price;

    public OrderItem(Food food, int amount, BigDecimal price) {
        this.food = food;
        this.amount = amount;
        this.price = price;
    }

    public Food getFood() {
        return food;
    }

    public void setFood(Food food) {
        this.food = food;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "OrderItem{" +
                "food=" + food +
                ", amount=" + amount +
                ", price=" + price +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof OrderItem)) return false;
        OrderItem orderItem = (OrderItem) o;
        return getAmount() == orderItem.getAmount() && getFood().equals(orderItem.getFood()) && getPrice().equals(orderItem.getPrice());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getFood(), getAmount(), getPrice());
    }
}
