package com.restaurant_management_system.service;

import java.util.List;

import com.restaurant_management_system.model.menuItems;

public interface menuService {
	
// public abstract void getAllItems();
 
 public abstract List<menuItems> listItems();
 
 public abstract menuItems addItem(menuItems item);
 
 public abstract menuItems getItemById(int id );
 
 public abstract boolean deleteItem( int id);
 
 public abstract boolean  updateItem(int id, menuItems item);
 
 public abstract List<menuItems> getSelectedMenuItems(String[] menuItemsIds);
}
