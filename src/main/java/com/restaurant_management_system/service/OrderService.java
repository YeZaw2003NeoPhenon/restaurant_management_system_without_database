package com.restaurant_management_system.service;

import java.util.List;

import com.restaurant_management_system.model.Order;


public interface  OrderService {
	public abstract List<Order> getAllOrders();
	
	public abstract void insertIntoOrder(Order order);
	
	public abstract void deleteOrder(int id );
	
	public abstract boolean updateOrder(int id , Order updatedOrder);
	
	public abstract Order findOrderById(int id );
	
	public abstract void checkedInOrder(int orderId );
	
	public abstract void checkedOutOrder( int orderId );
	
}
