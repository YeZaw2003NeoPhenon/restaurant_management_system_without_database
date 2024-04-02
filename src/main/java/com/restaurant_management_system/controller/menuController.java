package com.restaurant_management_system.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.restaurant_management_system.model.menuItems;
import com.restaurant_management_system.service.menuServiceImp;

@Controller
public class menuController {
	
	@Autowired
	menuServiceImp menuServiceImp;
	
	@RequestMapping( value = {"/" , "/items"} , method = RequestMethod.GET)
	public ModelAndView viewAllItems(){
		ModelAndView modelAndView = new ModelAndView();

		menuServiceImp.getAllItems();
		
		List<menuItems> menuDatas = menuServiceImp.listItems();
		modelAndView.addObject("menuDatas", menuDatas);
		modelAndView.addObject("item", new menuItems());
		
		modelAndView.setViewName("menuItems");
		return modelAndView;
	}
	
	@RequestMapping( value ="/addItem" , method = RequestMethod.POST)
	public ModelAndView addItem( @ModelAttribute menuItems item , RedirectAttributes redirectAttributes) {
		ModelAndView modelAndView = new ModelAndView();
		//RedirectAttributes -> we ravishingly dawn on this one to flesh out our generated name 
		redirectAttributes.addFlashAttribute("successMessage", "Items added successfully!");
		
		menuServiceImp.addItem(item); // save datas in here 
		modelAndView.setViewName("redirect:/items");
		return modelAndView;
	}
	
	@RequestMapping( value ="/items/delete/{id}" , method = RequestMethod.GET)
	public ModelAndView deleteItem( @PathVariable int id ) {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("redirect:/items");
		menuServiceImp.deleteItem(id);
		return modelAndView;
	}
	
	@RequestMapping( value = "/items/edit/{id}" , method = RequestMethod.GET)
	public ModelAndView editmenuItems( @PathVariable int id ) {
		ModelAndView modelAndView = new ModelAndView();
		
		menuItems item = menuServiceImp.getItemById(id);
		
		modelAndView.addObject("item", item);
		modelAndView.setViewName("editForm");
		return modelAndView;
	}
	
	@RequestMapping( value ="/items/update/{id}" , method = RequestMethod.POST)
	public ModelAndView updateMenuItems( @PathVariable int id , @ModelAttribute menuItems updatedItem , RedirectAttributes redirectAttributes) {
		ModelAndView modelAndView = new ModelAndView();
		
		boolean isSuccess =  menuServiceImp.updateItem(id, updatedItem);
		
		if( isSuccess ){
			redirectAttributes.addFlashAttribute("successMessage", "Item updated successfully!");
		}
		else{
			redirectAttributes.addFlashAttribute("errorMessage" , "Item failed to update!");
		}

//		List<menuItems> menuList =  menuServiceImp.listItems();
//		
//		modelAndView.addObject("menuDatas", menuList);
		
		modelAndView.setViewName("redirect:/items");
		return modelAndView;
	}
	
}
