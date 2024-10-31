package com.restaurant_management_system.repository;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;

import com.restaurant_management_system.model.menuItems;

@Repository
public class menuRepo {
	
	public Map<Integer , menuItems > menuMap = new HashMap<>();
	
	public int nextId = 1;
	
	public List<menuItems> listItems(){
		return menuMap.values().stream().collect(Collectors.toList());
	}
	
    public menuItems addItem(menuItems item) {
    	   if (!menuMap.containsKey(nextId)) {
    	        item.setId(nextId);
    	        menuMap.put(nextId, item);
    	        nextId++;  
    	        return item;
    	    }
    	   return null;
    }
	
	public menuItems getItemById( int id ) {
		return menuMap.values().stream().filter( menu -> menu.getId() == id ).findFirst().orElse(null);
//		return Optional.ofNullable(menuMap.get(id)).orElse(null);
	}
	
	public boolean deleteItem(int id) {
			Optional<menuItems> item = Optional.ofNullable(menuMap.get(id));
			if( item.isPresent() && menuMap.remove(id, item.get()) && menuMap.containsKey(id)) {
				return true;
			}
			else {
				return false;
			}
	}
	
	public boolean updateItem(int id , menuItems items ) {
		menuItems existingItem = getItemById(id);
		if( existingItem != null ) {
				menuMap.put(id, items);
			existingItem.setItemName(items.getItemName());
			existingItem.setPrices(items.getPrices());
			existingItem.setDescription(items.getDescription());
			existingItem.setId(items.getId());
			return true;
		}
		return false;
	}
}
