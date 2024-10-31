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
	
	@Getter
	@Setter
	private String description;
	
	public menuItems() {}
	
	public menuItems( int id ,String itemName, double prices, String description) {
		this.id = id;
		this.itemName = itemName;
		this.prices = prices;
		this.description = description;
	}
	
}
