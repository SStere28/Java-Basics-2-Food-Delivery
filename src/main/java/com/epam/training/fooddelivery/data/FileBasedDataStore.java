package com.epam.training.fooddelivery.data;

import com.epam.training.fooddelivery.domain.Food;
import com.epam.training.fooddelivery.domain.Order;
import com.epam.training.fooddelivery.domain.OrderItem;

import java.io.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class FileBasedDataStore implements DataStore {

    private final List<Order> orders;
    private static final int ORDERID=0;
    private static final int CUSTOMERID=1;
    private static final int DATE=2;
    private static final int FOODNAME=3;
    private static final int FOODPIECES=4;


    public FileBasedDataStore(String inputFilePath) {
        orders = new ArrayList<>();
        List<String> listClean = new ArrayList<>();
        File input = new File(inputFilePath);
        try (InputStream inputStream = new FileInputStream(input);
             BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream))) {

            listClean = bufferedReader.lines().collect(Collectors.toList());

        } catch (IOException exception) {
            exception.printStackTrace();
        }

        listClean.forEach(list -> {
            String[] orderValues = list.split(",");
            int orderID = Integer.parseInt(orderValues[ORDERID].replace("\uFEFF", ""));
            Optional<Order> orderByID = findOrderByID(orderID);

            Order order = orderByID.orElseGet(Order::new);

            int customerID = Integer.parseInt(orderValues[CUSTOMERID]);
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
            LocalDateTime localDateTime = LocalDateTime.parse(orderValues[DATE], dateTimeFormatter);
            String foodName = orderValues[FOODNAME];
            int foodPieces = Integer.parseInt(orderValues[FOODPIECES]);

            int foodPrice = getFoodPrice(foodName);
            Food food = new Food(foodName, BigDecimal.valueOf(foodPrice));
            OrderItem orderItem = new OrderItem(food, foodPieces, BigDecimal.valueOf(foodPrice));


            List<OrderItem> orderItems = order.getOrderItems();
            orderItems.add(orderItem);

            if (orderByID.isEmpty()) {
                order.setId(orderID);
                order.setCustomerId(customerID);
                order.setOrderDate(localDateTime);
                order.setTotalPrice(BigDecimal.valueOf((long) foodPrice * foodPieces));
                orders.add(order);
            } else {
                order.setTotalPrice(BigDecimal.valueOf((long) foodPrice * foodPieces).add(order.getTotalPrice()));
            }

        });


    }


    private Optional<Order> findOrderByID(int orderID) {
        return orders.stream().filter(o -> o.getId() == orderID).findFirst();
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


    @Override
    public List<Order> getOrders() {
        return orders;
    }
}
