package com.restaurant_management_system.repository;

import java.awt.event.ItemEvent;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;
import com.restaurant_management_system.model.Order;

@Repository
public class orderRepository {
	
//	public static final List<Order> ORDERLI_LIST = new ArrayList<>();
	
	public static final Map<Integer, Order>ORDER_MAP = new HashMap<>();
	
	public List<Order> getAllOrders(){
		return new ArrayList<>(ORDER_MAP.values());
	}
	
	public void  InsertIntoOrder(Order order) {
			ORDER_MAP.put(order.getOrder_id(), order);
	}
	
	public Order findOrderById( int id ) {
		return ORDER_MAP.values().stream().filter( order -> order.getOrder_id() == id ).findFirst().orElse(null);
	}
	
	public boolean deleteOrder( int id ) {
		Order targetedOrder = findOrderById(id);
		if(ORDER_MAP.containsKey(id) && targetedOrder != null && ORDER_MAP.remove(id, targetedOrder)) {
			return true;
		}
		else {
			return false;
		}
	}
	
	public boolean updateOrder( int id , Order updatedOrder ) {
		// we can not lead to drif off into findOrderByid() as it return order(obj) and consequently, it is not appropriate for optional 
		if( ORDER_MAP.get(id) != null || ORDER_MAP.size() >= 0 || ORDER_MAP.containsKey(updatedOrder.getOrder_id())) {
			return Optional.ofNullable(ORDER_MAP.get(id)).map(existingOrder -> {
				existingOrder.setCustomer_name(updatedOrder.getCustomer_name());
				existingOrder.setItem_list(updatedOrder.getItem_list());
				existingOrder.setOrder_id(updatedOrder.getOrder_id());
				existingOrder.setCheckIn(updatedOrder.isCheckIn());
				existingOrder.setCheckOut(updatedOrder.isCheckOut());
				// update order with new details
				return existingOrder; // return updatedOrder
			}).map(updated -> {
				ORDER_MAP.put(id, updatedOrder);
				return true;
			}).orElse(false);
		}
		return false;
	}
	
	public void checkedInOrder( int orderId ) {
		Order currentOrder = findOrderById(orderId);
		if( currentOrder != null && !currentOrder.isCheckIn()) {
			// if it is currently checked in then customer itself make it checked in
			currentOrder.setCheckIn(true); 
			currentOrder.setCheckInDate(LocalDate.now());
			// then vicariously update our current order as well as we are keeping track of it 
			updateOrder(orderId, currentOrder);
		}
	}
	
	public void checkedOutOrder( int orderId ) {
		// same logic with above and the only belligient difference is how we handle checked out 
		Order currentOrder = findOrderById(orderId);
		if( currentOrder != null && currentOrder.isCheckIn() && !currentOrder.isCheckOut()) {
			currentOrder.setCheckOut(true); 
			currentOrder.setCheckOutDate(LocalDate.now());
			updateOrder(orderId, currentOrder);
		}
	}
	
	public List<Order> searchOrders(String query){
		// query can be essentially either customer name , id, or item name
		List<Order> allOrders = getAllOrders();
		return allOrders.stream().filter(order -> order.getCustomer_name().toLowerCase().contains(query.toLowerCase()) ||
												   String.valueOf(order.getOrder_id()).contains(query.toLowerCase()) || 
												   order.getItem_list().stream()
												   .anyMatch(item -> item.getItemName().toLowerCase().contains(query.toLowerCase())))
												   .collect(Collectors.toList());
	}
	
}
