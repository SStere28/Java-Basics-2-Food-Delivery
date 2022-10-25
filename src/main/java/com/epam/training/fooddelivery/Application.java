package com.epam.training.fooddelivery;

import com.epam.training.fooddelivery.data.DataStore;
import com.epam.training.fooddelivery.data.FileBasedDataStore;
import com.epam.training.fooddelivery.service.FoodDeliveryService;
import com.epam.training.fooddelivery.view.View;

import java.time.LocalDate;


public class Application {

    public static void main(String[] args) {

        DataStore fileBasedDataStore =
                new FileBasedDataStore("input/orders.csv");

        FoodDeliveryService foodDeliveryService = new FoodDeliveryService(fileBasedDataStore);
        View view = new View();
        view.printWelcomeMessage();
        view.printMostExpensiveOrder(foodDeliveryService.getMostExpensiveOrder());
        view.printMostPopularFood(foodDeliveryService.getMostPopularFood());
        view.printMostLoyalCustomer(foodDeliveryService.getMostLoyalCustomerId());

        LocalDate startDate = view.readStartDate();
        LocalDate endDate = view.readEndDate();
        view.printStatistics(foodDeliveryService.getStatistics(startDate, endDate), startDate, endDate);


    }

}
