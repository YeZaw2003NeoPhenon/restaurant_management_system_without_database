package com.restaurant_management_system.service;


import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.restaurant_management_system.model.menuItems;
import com.restaurant_management_system.repository.menuRepo;

@Service
public class menuServiceImp implements menuService{
	
	@Autowired
	menuRepo menuRepo;
	
	@Override
	public List<menuItems> listItems() {
		return menuRepo.listItems();
	}

	@Override
	public menuItems addItem(menuItems items) {
		return menuRepo.addItem(items);
	}

	@Override
	public menuItems getItemById(int id) {
		return menuRepo.getItemById(id);
	}

	@Override
	public boolean deleteItem( int id ) {
		return menuRepo.deleteItem(id);
	}

	@Override
	public boolean updateItem(int id , menuItems item) {
		return menuRepo.updateItem(id, item);
	}
	
	@Override
	public List<menuItems> getSelectedMenuItems(String[] menuItemsIds) {
		List<menuItems> selectedItems = new ArrayList<>();
		
		if( menuItemsIds != null ) {
			for( String menuId : menuItemsIds) {
			menuItems item = menuRepo.getItemById(Integer.parseInt(menuId));
			if(item != null) {
				selectedItems.add(item);
			}
		}
	}
	  return selectedItems.isEmpty() ? null : selectedItems;
	}

	public double calculateTotalPrices(List<menuItems> selectedItems, int quantity) {
		
		 if (selectedItems == null || selectedItems.isEmpty() || quantity <= 0) {
		        return 0.0; 
		}
		 
		return selectedItems.stream()
							.mapToDouble(item -> item.getPrices() * quantity)
							.sum();
	}
	
}
