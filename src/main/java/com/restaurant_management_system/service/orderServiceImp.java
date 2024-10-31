package com.restaurant_management_system.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.restaurant_management_system.model.Order;
import com.restaurant_management_system.repository.orderRepository;

@Service
public class orderServiceImp implements OrderService{

	private final orderRepository orderRepository;
	
	@Autowired
	public orderServiceImp(orderRepository orderRepository) {
		this.orderRepository = orderRepository;
	}
	
	@Override
	public List<Order> getAllOrders() {
		return orderRepository.getAllOrders();
	}

	@Override
	public void insertIntoOrder(Order order) {
		orderRepository.InsertIntoOrder(order);
	}

	@Override
	public boolean  deleteOrder(int id) {
	  return orderRepository.deleteOrder(id);
	}

	@Override
	public boolean updateOrder(int id , Order updatedOrder) {
		return orderRepository.updateOrder(id, updatedOrder);
	}

	@Override
	public Order findOrderById(int id) {
		return orderRepository.findOrderById(id);
	}

	@Override
	public void checkedInOrder(int orderId) {
		orderRepository.checkedInOrder(orderId);
	}

	@Override
	public void checkedOutOrder(int orderId) {
		orderRepository.checkedOutOrder(orderId);
		
	}
	
	@Override
	public List<Order> searchOrders(String query) {
		return orderRepository.searchOrders(query);
	}
	
}

