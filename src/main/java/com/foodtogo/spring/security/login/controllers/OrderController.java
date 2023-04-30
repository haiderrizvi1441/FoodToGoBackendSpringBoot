package com.foodtogo.spring.security.login.controllers;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.foodtogo.spring.security.login.models.Food;
import com.foodtogo.spring.security.login.models.Orders;
import com.foodtogo.spring.security.login.pdfexport.FoodPdfExporter;
import com.foodtogo.spring.security.login.pdfexport.OrderPdfExporter;
import com.foodtogo.spring.security.login.repository.OrderRepository;
import com.foodtogo.spring.security.login.security.services.OrderService;
import com.lowagie.text.DocumentException;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping(value = "/api")
public class OrderController {

	@Autowired
	OrderService orderService;

	@Autowired
	OrderRepository orderRepository;
	@CrossOrigin(origins = "*", allowedHeaders = "*")
	@GetMapping("/orders")
	@ResponseBody
	public ResponseEntity<List<Orders>> getAllOrders(@RequestParam(required = false) Long id) {
		try {
			List<Orders> orders = new ArrayList<Orders>();

			if (id == null)
				orderService.fetchOrderList().forEach(orders::add);
			else
				orderService.fetchOrdersListbyId(id);// .forEach(foods::add);

			return new ResponseEntity<>(orders, HttpStatus.OK);

		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	@CrossOrigin(origins = "*", allowedHeaders = "*")
	@GetMapping("/orders/{id}")
	@ResponseBody
	public ResponseEntity<Orders> getOrderById(@PathVariable("id") long id) {
		Optional<Orders> ordersdata = Optional.ofNullable(orderRepository.findById(id));

		if (ordersdata.isPresent()) {
			return new ResponseEntity<>(ordersdata.get(), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@Throttling(timeFrameInSeconds = 60, calls = 10)
	@CrossOrigin(origins = "*", allowedHeaders = "*")
	@PostMapping("/orders")
	@ResponseBody
	public ResponseEntity<Orders> createTutorial(@RequestBody Orders orders) {
		try {
			orderService.saveOrders(orders);
			return new ResponseEntity<>(orders, HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Throttling(timeFrameInSeconds = 60, calls = 10)
	@CrossOrigin(origins = "*", allowedHeaders = "*")
	@PutMapping("/orders/{id}")
	public ResponseEntity<Orders> updateTutorial(@PathVariable("id") long id, @RequestBody Orders orders) {
		Optional<Orders> orders1 = Optional.ofNullable(orderRepository.findById(id));

		if (orders1.isPresent()) {
			try {
				orderService.updateOrder(orders, id);
				return new ResponseEntity<Orders>(HttpStatus.OK);
			}

			catch (Exception e) {
				return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
			}

		} else {
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}
	}

	@Throttling(timeFrameInSeconds = 60, calls = 10)
	@CrossOrigin(origins = "*", allowedHeaders = "*")
	@DeleteMapping("/orders/{id}")
	@ResponseBody
	public ResponseEntity<HttpStatus> deleteFood(@PathVariable("id") long id) {
		try {
			orderService.deleteOrderById(id);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}
	@CrossOrigin(origins = "*", allowedHeaders = "*")
	@GetMapping("/users/export/orderpdf")
	public void exportToPDF(HttpServletResponse response) throws DocumentException, IOException {
		response.setContentType("application/pdf");
		DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
		String currentDateTime = dateFormatter.format(new Date());

		String headerKey = "Content-Disposition";
		String headerValue = "attachment; filename=users_" + currentDateTime + ".pdf";

		response.setHeader(headerKey, headerValue);

		List<Orders> listOrders = orderService.fetchOrderList();

		OrderPdfExporter exporter = new OrderPdfExporter(listOrders);
		exporter.export(response);

	}

}
