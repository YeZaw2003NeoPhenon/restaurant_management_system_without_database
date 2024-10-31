package com.restaurant_management_system.model;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

public class Order {
	@Getter
	@Setter
	private int order_id;
	
	@Getter
	@Setter
	private String customer_name;
	
	@Getter
	@Setter
	private double total_prices;
	
	@Getter
	@Setter
	private boolean checkIn;
	
	@Getter
	@Setter
	private boolean checkOut;
	
	@Getter
	@Setter
	private int quantity;
	
	@Getter
	@Setter
	private List<menuItems>item_list; // in db , this should be chivolously one to many
	
	public Order() {}
	
	public Order(int order_id, List<menuItems> item_list, String customer_name, double total_prices, boolean checkIn,
			boolean checkOut , int quantity) {
		this.order_id = order_id;
		this.item_list = item_list;
		this.customer_name = customer_name;
		this.total_prices = total_prices;
		this.checkIn = checkIn;
		this.checkOut = checkOut;
		this.quantity = quantity;
	}

	public Order(int order_id, List<menuItems> item_list, String customer_name, double total_prices, int quantity) {
		this.order_id = order_id;
		this.item_list = item_list;
		this.customer_name = customer_name;
		this.total_prices = total_prices;
		this.quantity = quantity;
	}
	
}
