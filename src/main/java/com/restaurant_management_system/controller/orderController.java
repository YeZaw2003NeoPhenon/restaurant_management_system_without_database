package com.restaurant_management_system.controller;


import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.restaurant_management_system.model.Order;
import com.restaurant_management_system.model.menuItems;
import com.restaurant_management_system.service.menuServiceImp;
import com.restaurant_management_system.service.orderServiceImp;

@Controller
public class orderController {
	
	@Autowired
	private  orderServiceImp orderServiceImp;
	
	 @Autowired
	 private menuServiceImp menuServiceImp;
	 
	@RequestMapping(value = "/orders" , method = RequestMethod.GET)
	public ModelAndView showAllOrders() {
		ModelAndView modelAndView = new ModelAndView();
		
		 List<Order> orderList = orderServiceImp.getAllOrders();
		 
		 modelAndView.addObject("ordersItems", orderList);
		 
		 modelAndView.setViewName("orders");
		return modelAndView;
	}
	
	@RequestMapping( value = "/create" , method = RequestMethod.GET)
	public ModelAndView showOrderCreationForm() {
		
		ModelAndView modelAndView = new ModelAndView();
		List<menuItems> menuItems = menuServiceImp.listItems();
		modelAndView.addObject("menuItems", menuItems);
		Order order = new Order();
		modelAndView.addObject("order", order);
		modelAndView.setViewName("createOrder");
		return modelAndView;
	}
	
	@RequestMapping( value = "/saveDatas" , method = RequestMethod.POST)
	public ModelAndView createOrder( @ModelAttribute Order order , RedirectAttributes redirectAttributes , HttpServletRequest request ){
		
		ModelAndView modelAndView = new ModelAndView();
		double totalPrices = 0 ;
		String[] menuItemsIds = request.getParameterValues("menuItem");
		if( menuItemsIds != null ) {
			List<menuItems> selectedItems = new ArrayList<>();
			for( String menuId : menuItemsIds) {
				 // we gonna have indespensibly have to turn it into obj and add it into arraylist
				menuItems menuItems = menuServiceImp.getItemById(Integer.parseInt(menuId));
				selectedItems.add(menuItems);
				totalPrices += menuItems.getPrices() * order.getQuantity();
			}
			order.setTotal_prices(totalPrices);
			order.setItem_list(selectedItems);
		}
		redirectAttributes.addFlashAttribute("successMessage", "you ordered item graciously!");
		orderServiceImp.insertIntoOrder(order);
		modelAndView.setViewName("redirect:/orders"); // just sagaciously redirecting to order page after creating order 
		return modelAndView;
	}
	
//  just flesh out my autonomous demostrations that i get carried away with boredorm to create  updaet and delete here as it turn out to be same logic from menu items(CRUD)//
	@RequestMapping(value = "/checkIn/{orderId}" , method = RequestMethod.POST)
	public ModelAndView checkInOrder(@PathVariable int orderId){
		
		ModelAndView modelAndView = new ModelAndView();
		orderServiceImp.checkedInOrder(orderId);
		modelAndView.setViewName("redirect:/orders");
		return modelAndView;
	}
	
	@RequestMapping(value = "/checkOut/{orderId}" , method = RequestMethod.POST)
	public ModelAndView checkOutOrder(@PathVariable int orderId){
		
		ModelAndView modelAndView = new ModelAndView();
		orderServiceImp.checkedOutOrder(orderId);
		modelAndView.setViewName("redirect:/orders");
		return modelAndView;
	}
	
	@RequestMapping(value = "/delete/{orderId}" , method = RequestMethod.GET)
	public ModelAndView deleteOrder(@PathVariable int orderId) {
		ModelAndView modelAndView = new ModelAndView();
		orderServiceImp.deleteOrder(orderId);
		modelAndView.setViewName("redirect:/orders");
		return modelAndView;
	}
	
}