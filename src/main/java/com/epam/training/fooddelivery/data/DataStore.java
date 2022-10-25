package com.epam.training.fooddelivery.data;

import com.epam.training.fooddelivery.domain.Order;

import java.util.List;

public interface DataStore {

    List<Order> getOrders();

}
