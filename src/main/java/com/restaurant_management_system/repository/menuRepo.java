package com.restaurant_management_system.repository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.restaurant_management_system.model.menuItems;

@Repository
public class menuRepo {

	private List<menuItems> menuList = new ArrayList<>();
	
	private Map<Integer , menuItems > menuMap = new HashMap<>();
	
	private boolean isIncluded = false;
	
	public void getAllItems(){
		
		if( !isIncluded ) {
			List<menuItems> list = Arrays.asList(
					new menuItems("Pizza" , 50.50 , 1 ),
					new menuItems("potatoe salad" , 30.50 , 2),
					new menuItems("Humbuger" , 20.50 , 3 ),
					new menuItems("Sunkit" , 10.50 , 4 ));

			for( menuItems items : list ) {
				menuList.add(items);
				menuMap.put( items.getId(), items);
				}
			isIncluded = true;
			return;
		}
	}
	
	public List<menuItems> listItems(){
		return menuList;
	}
	
	public menuItems addItem( menuItems item) {
		if(menuList.size() >= 0 && !menuMap.containsKey(item.getId())) {
			menuList.add(item);
			menuMap.put(item.getId(), item );
			return item;
		}
		
		return null;
	}
	
	public menuItems getItemById( int id ) {
		return menuList.stream().filter( menu -> menu.getId() == id ).findFirst().orElse(null);
	}
	
	public void deleteItem(int id) {
		menuItems itemTodelete = getItemById(id);
		if( itemTodelete != null ) {
			menuList.remove(itemTodelete);
			menuMap.remove(id);
		}
	}
	
	public boolean updateItem(int id , menuItems items ) {
		menuItems existingItem = getItemById(id);
		if( existingItem != null ) {
			int index = menuList.indexOf(existingItem);
			if( index != -1 ) {
				menuList.set(index, items);
				menuMap.put(id, items);
			}
			existingItem.setItemName(items.getItemName());
			existingItem.setPrices(items.getPrices());
			existingItem.setId(items.getId());
			return true;
		}
		return false;
	}
	
}
