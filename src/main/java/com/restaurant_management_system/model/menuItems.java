package com.restaurant_management_system.model;

import lombok.Getter;
import lombok.Setter;

public class menuItems {
	
	@Getter
	@Setter
	private String itemName;
	
	@Getter
	@Setter
	private double prices;
	
	@Getter
	@Setter
	private int id;
	
	public menuItems() {}
	
	public menuItems(String itemName, double prices, int id) {
		this.itemName = itemName;
		this.prices = prices;
		this.id = id;
	}
	
}
