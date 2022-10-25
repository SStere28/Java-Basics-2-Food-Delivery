package com.epam.training.fooddelivery.service;

import com.epam.training.fooddelivery.data.DataStore;
import com.epam.training.fooddelivery.domain.Food;
import com.epam.training.fooddelivery.domain.Order;
import com.epam.training.fooddelivery.domain.OrderItem;
import com.epam.training.fooddelivery.domain.Statistics;

import java.math.BigDecimal;
import java.math.MathContext;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class FoodDeliveryService {

    private final DataStore dataStore;
    private static final DecimalFormat df = new DecimalFormat("0.00");

    public FoodDeliveryService(DataStore dataStore) {
        this.dataStore = dataStore;
    }

    public List<Order> getOrders(LocalDate startDate, LocalDate endDate) {

        List<Order> list = dataStore.getOrders()
                .stream()
                .filter(ord -> ord.getOrderDate().compareTo(startDate.atStartOfDay()) >= 0 && ord.getOrderDate()
                        .compareTo(endDate.atTime(23, 59, 59)) <= 0)
                .collect(Collectors.toList());

        if (list.isEmpty())
            throw new NoSuchElementException("No orders were found between " + startDate + " and " + endDate);
        return list;

    }

    public Order getMostExpensiveOrder() {
        return dataStore.getOrders()
                .stream()
                .sorted(Comparator.comparing(Order::getTotalPrice).reversed())
                .collect(Collectors.toList())
                .get(0);
    }

    public Food getMostPopularFood() {
        Food food;
        List<OrderItem> orderItems;
        Map<String, Long> foodAmount = new HashMap<>();
        foodAmount.put("Fideua", 0L);
        foodAmount.put("Paella", 0L);
        foodAmount.put("Tortilla", 0L);
        foodAmount.put("Gazpacho", 0L);
        foodAmount.put("Quesadilla", 0L);

        orderItems = dataStore.getOrders()
                .stream()
                .map(Order::getOrderItems)
                .flatMap(Collection::stream)
                .collect(Collectors.toList());

        orderItems.forEach(e -> foodAmount.put(e.getFood().getName(), (long) e.getAmount() + foodAmount.get(e.getFood().getName())));

        String foodName = foodAmount.entrySet()
                .stream()
                .max((entry1, entry2) -> entry1.getValue() > entry2.getValue() ? 1 : -1)
                .get()
                .getKey();

        food = new Food(foodName, BigDecimal.valueOf(getFoodPrice(foodName)));
        return food;
    }

    public long getMostLoyalCustomerId() {

        return dataStore.getOrders()
                .stream()
                .sorted(Comparator.comparing(Order::getTotalPrice).reversed())
                .collect(Collectors.toList())
                .get(0)
                .getCustomerId();
    }

    public Statistics getStatistics(LocalDate startDate, LocalDate endDate) {
        Statistics statistics = new Statistics();
        List<Order> order = getOrders(startDate, endDate);
        List<OrderItem> orderItems;
        Map<LocalDate, BigDecimal> dayOfHighestIncome = new HashMap<>();

        if (!order.isEmpty()) {
            orderItems = order
                    .stream()
                    .map(Order::getOrderItems)
                    .flatMap(Collection::stream)
                    .collect(Collectors.toList());

            BigDecimal totalIncome = new BigDecimal(0);
            MathContext mc = new MathContext(10);

            for (Order order1 : order) {
                totalIncome = totalIncome.add(order1.getTotalPrice(), mc);
                if (dayOfHighestIncome.containsKey(order1.getOrderDate().toLocalDate())) {
                    dayOfHighestIncome.put(order1.getOrderDate().toLocalDate(),
                            order1.getTotalPrice().add(dayOfHighestIncome.get(order1.getOrderDate().toLocalDate())));
                } else dayOfHighestIncome.put(order1.getOrderDate().toLocalDate(), order1.getTotalPrice());
            }



            statistics.setTotalIncome(totalIncome);
            statistics.setAverageIncomeByOrder(Double.parseDouble(df.format((double) totalIncome.intValue() / (double) order.size())));
            statistics.setNumberOfOrder(order.size());
            statistics.setNumberOfFood(orderItems.stream().mapToInt(OrderItem::getAmount).sum());
            statistics.setDayOfHighestIncome(dayOfHighestIncome
                    .entrySet()
                    .stream()
                    .max(Map.Entry.comparingByValue())
                    .get()
                    .getKey());
            System.out.println();
            return statistics;

        }
        return new Statistics();

    }

    public int getFoodPrice(String food) {

        return switch (food) {
            case "Fideua" -> 15;
            case "Paella", "Quesadilla" -> 13;
            case "Tortilla" -> 10;
            case "Gazpacho" -> 8;
            default -> -1;
        };

    }
}
