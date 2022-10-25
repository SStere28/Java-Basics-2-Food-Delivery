package com.epam.training.fooddelivery.domain;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

public class Statistics {

    private BigDecimal totalIncome;
    private double averageIncomeByOrder;
    private int numberOfFood;
    private int numberOfOrder;
    private LocalDate dayOfHighestIncome;

    public Statistics() {
    }

    @Override
    public String toString() {
        return "Statistics{" + "totalIncome=" + totalIncome + ", averageIncomeByOrder=" + averageIncomeByOrder + ", numberOfFood=" + numberOfFood + ", numberOfOrder=" + numberOfOrder + ", dayOfHighestIncome=" + dayOfHighestIncome + '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Statistics)) return false;
        Statistics that = (Statistics) o;
        return Double.compare(that.getAverageIncomeByOrder(), getAverageIncomeByOrder()) == 0 && getNumberOfFood() == that.getNumberOfFood() && getNumberOfOrder() == that.getNumberOfOrder() && getTotalIncome().equals(that.getTotalIncome()) && getDayOfHighestIncome().equals(that.getDayOfHighestIncome());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getTotalIncome(), getAverageIncomeByOrder(), getNumberOfFood(), getNumberOfOrder(), getDayOfHighestIncome());
    }

    public BigDecimal getTotalIncome() {
        return totalIncome;
    }

    public void setTotalIncome(BigDecimal totalIncome) {
        this.totalIncome = totalIncome;
    }

    public double getAverageIncomeByOrder() {
        return averageIncomeByOrder;
    }

    public void setAverageIncomeByOrder(double averageIncomeByOrder) {
        this.averageIncomeByOrder = averageIncomeByOrder;
    }

    public int getNumberOfFood() {
        return numberOfFood;
    }

    public void setNumberOfFood(int numberOfFood) {
        this.numberOfFood = numberOfFood;
    }

    public int getNumberOfOrder() {
        return numberOfOrder;
    }

    public void setNumberOfOrder(int numberOfOrder) {
        this.numberOfOrder = numberOfOrder;
    }

    public LocalDate getDayOfHighestIncome() {
        return dayOfHighestIncome;
    }

    public void setDayOfHighestIncome(LocalDate datOfHighestIncome) {
        this.dayOfHighestIncome = datOfHighestIncome;
    }
}
