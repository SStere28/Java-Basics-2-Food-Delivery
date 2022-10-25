package com.epam.training.fooddelivery.view;

import com.epam.training.fooddelivery.domain.Food;
import com.epam.training.fooddelivery.domain.Order;
import com.epam.training.fooddelivery.domain.Statistics;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class View {
    private Scanner keyboard = new Scanner(System.in);
    private final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    public View() {
    }

    public void printWelcomeMessage() {
        System.out.println("Welcome to Food Delivery Service \n");
    }

    public LocalDate readStartDate() {
        System.out.print("Enter the start date (DD-MM-YYYY) :  ");
        return LocalDate.parse(keyboard.next(), dateTimeFormatter);
    }

    public LocalDate readEndDate() {
        System.out.print("Enter the end date (DD-MM-YYYY) :  ");
        return LocalDate.parse(keyboard.next(), dateTimeFormatter);
    }

    public void printMostExpensiveOrder(Order order) {
        System.out.println("The most expensive order was #" + order.getId() + " with a total price of " + order.getTotalPrice() + " EUR");
    }

    public void printMostPopularFood(Food food) {
        System.out.println("The most popular food is " + food.getName());
    }

    public void printMostLoyalCustomer(long customerId) {
        System.out.println("The customer who ordered the most was: #" + customerId);
    }
    public void printStatistics(Statistics statistics, LocalDate startDate, LocalDate endDate){
        if(statistics.getNumberOfFood()>0){
        System.out.println("The statistics between "+startDate+" and "+endDate+":");
        System.out.println("The total income was: "+statistics.getTotalIncome()+" EUR");
        System.out.println("The average income per order: "+statistics.getAverageIncomeByOrder()+" EUr");
        System.out.println("There were "+statistics.getNumberOfFood()+" dishes served");
        System.out.println("There were "+statistics.getNumberOfOrder()+" orders made");
        System.out.println("The day with the highest income: "+statistics.getDayOfHighestIncome());
        }

    }
}
