package com.foodtogo.spring.security.login.security.services.impl;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.foodtogo.spring.security.login.models.Orders;
import com.foodtogo.spring.security.login.repository.OrderRepository;
import com.foodtogo.spring.security.login.security.services.OrderService;


@Service
public class OrderServiceImpl implements OrderService {
	
	private static final Logger logger = LoggerFactory.getLogger(OrderServiceImpl.class);

	@Autowired
	private OrderRepository orderRepository;

	@Override
	public Orders saveOrders(Orders orders) {
		logger.info("{},Orders saved into database",OrderServiceImpl.class.getName());
		return orderRepository.save(orders);
	}

	@Override
	@Cacheable(value = "foodcaching",key = "'orderList'")
	public List<Orders> fetchOrderList() {
		logger.info("{},Orders fetched from database",OrderServiceImpl.class.getName());
		return (List<Orders>) orderRepository.findAll();
	}
	

	@Override
	@CachePut(value="foodcaching")
	public Orders updateOrder(Orders orders, Long id) {
		logger.info("{},Orders has been updated in the method updateOrder() ", OrderServiceImpl.class.getName());
		Orders ordersservice = orderRepository.findById(id).get();

		if (Objects.nonNull(ordersservice.getOrder_id()) && !"".equalsIgnoreCase(ordersservice.getOrder_id())) {
			ordersservice.setOrder_id(ordersservice.getOrder_id());
		}

		if (Objects.nonNull(ordersservice.getItems()) && !"".equalsIgnoreCase(ordersservice.getItems())) {
			ordersservice.setItems(ordersservice.getItems());
		}
		ordersservice.setStatus(ordersservice.isStatus());
		return orderRepository.save(ordersservice);
	}

	@Override
	@CacheEvict(value = "foodcaching", key = "#id")
	public void deleteOrderById(Long id) {
		logger.info("{},Orders has been deleted {} in the method deleteOrderById() ", OrderServiceImpl.class.getName(),id);
		orderRepository.deleteById(id);

	}
	@Cacheable(value="foodcaching", key="#id")
	@Override
	public Optional<Orders> fetchOrdersListbyId(Long id) {
		logger.info("{},Orders has been fetched by id  {} in the method fetchOrdersListbyId() ", OrderServiceImpl.class.getName(),id);
		return  orderRepository.findById(id);
	}

}
