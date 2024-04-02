package com.restaurant_management_system.service;


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
	public void getAllItems() {
		menuRepo.getAllItems();
	}

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
	public void deleteItem( int id ) {
		menuRepo.deleteItem(id);
	}

	@Override
	public boolean updateItem(int id , menuItems item) {
		return menuRepo.updateItem(id, item);
	}
}
