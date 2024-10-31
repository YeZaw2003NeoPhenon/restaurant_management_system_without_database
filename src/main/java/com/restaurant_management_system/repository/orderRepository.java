package com.restaurant_management_system.repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Repository;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyEmitterReturnValueHandler;

import com.restaurant_management_system.model.Order;

@Repository
public class orderRepository {
	
	public static final List<Order> ORDERLI_LIST = new ArrayList<>();
	
	public static final Map<Integer, Order>ORDER_MAP = new HashMap<>();
	
	public List<Order> getAllOrders(){
		return new ArrayList<>(ORDER_MAP.values());
	}
	
	public void  InsertIntoOrder(Order order) {
			ORDERLI_LIST.add(order);
			ORDER_MAP.put(order.getOrder_id(), order);
	}
	
	public Order findOrderById( int id ) {
		return ORDERLI_LIST.stream().filter( order -> order.getOrder_id() == id ).findFirst().orElse(null);
	}
	
	public void deleteOrder( int id ) {
		Order targetedOrder = findOrderById(id);
		if( ORDER_MAP.containsKey(id) && targetedOrder != null ) {
			ORDERLI_LIST.remove(targetedOrder);
			ORDER_MAP.remove(id);
		}
	}
	
	public boolean updateOrder( int id , Order updatedOrder ) {
		// we can not lead to drif off into findOrderByid() as it return order(obj) and consequently, it is not appropriate for optional 
		if( ORDERLI_LIST != null || ORDERLI_LIST.size() >= 0 || ORDER_MAP.containsKey(updatedOrder.getOrder_id())) {
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
			// then vicariously update our current order as well as we are keeping track of it 
			updateOrder(orderId, currentOrder);
		}
	}
	
	public void checkedOutOrder( int orderId ) {
		// same logic with above and the only belligient difference is how we handle checked out 
		Order currentOrder = findOrderById(orderId);
		if( currentOrder != null && currentOrder.isCheckIn() && !currentOrder.isCheckOut()) {
			currentOrder.setCheckOut(true); 
			updateOrder(orderId, currentOrder);
		}
	}
	
}
