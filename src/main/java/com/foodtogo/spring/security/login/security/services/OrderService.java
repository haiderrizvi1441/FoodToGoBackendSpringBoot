package com.foodtogo.spring.security.login.security.services;

import java.util.List;
import java.util.Optional;

import com.foodtogo.spring.security.login.models.Orders;

public interface OrderService {
	// Save operation
    Orders saveOrders(Orders orders);
 
    // Read operation
    List<Orders> fetchOrderList();
 
    // Update operation
    Orders updateOrder(Orders orders,Long id);
 
    // Delete operation
    void deleteOrderById(Long id);
 
    Optional<Orders> fetchOrdersListbyId(Long id);
   
}
