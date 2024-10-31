package com.restaurant_management_system.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
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
	public ModelAndView saveOrderDatas( @ModelAttribute Order order , RedirectAttributes redirectAttributes , HttpServletRequest request ){
		
		ModelAndView modelAndView = new ModelAndView();
		
		String[] menuItemsIds = request.getParameterValues("menuItem");
			
			List<menuItems> selectedItems = menuServiceImp.getSelectedMenuItems(menuItemsIds);
			double totalPrices = menuServiceImp.calculateTotalPrices(selectedItems,order.getQuantity());
			
			order.setTotal_prices(totalPrices);
			order.setItem_list(selectedItems);
		
		redirectAttributes.addFlashAttribute("successMessage", "you ordered item graciously!");
		orderServiceImp.insertIntoOrder(order);
		modelAndView.setViewName("redirect:/orders"); 
		return modelAndView;
	}
//	
//	@RequestMapping(value = "/edit/{orderId}" , method = RequestMethod.GET)
//	public ModelAndView editOrder( @PathVariable int orderId) {
//		ModelAndView modelAndView = new ModelAndView();
//		Order order = orderServiceImp.findOrderById(orderId);
//		modelAndView.addObject("order",order);
//		modelAndView.setViewName("editOrder");
//		return modelAndView;
//	}
	
	@RequestMapping(value = "/update" , method = RequestMethod.POST)
	public ModelAndView updateOrder( @RequestParam(name = "orderId") int orderId , @ModelAttribute Order order , RedirectAttributes redirectAttributes , HttpServletRequest request) {
		ModelAndView modelAndView = new ModelAndView();
	String[] menuItemsIds = request.getParameterValues("menuItem");
	
	List<menuItems> selectedItems = menuServiceImp.getSelectedMenuItems(menuItemsIds);
	double totalPrices = menuServiceImp.calculateTotalPrices(selectedItems, order.getQuantity());
	
	System.out.println("Total Prices calculated: " + totalPrices);
	
	order.setTotal_prices(totalPrices);
	order.setItem_list(selectedItems);
	
	boolean isUpdated =	orderServiceImp.updateOrder(orderId, order);
	
	if(isUpdated) {
		redirectAttributes.addFlashAttribute("successMessage", "you updated item triumphantly!");
	}
	else {
		redirectAttributes.addFlashAttribute("errorMessage", "There is something slipped away while struggling to update item");
	}
		modelAndView.setViewName("redirect:/orders");
		return modelAndView;
	}

	@RequestMapping(value = "/delete/{orderId}" , method = RequestMethod.GET)
	public ModelAndView deleteOrder(@PathVariable int orderId , RedirectAttributes redirectAttributes) {
		ModelAndView modelAndView = new ModelAndView();
	 boolean isDeleted = orderServiceImp.deleteOrder(orderId);
	 if(isDeleted) {
		 redirectAttributes.addFlashAttribute("successMessage" , "you deleted Item successfully!");
	 }
	 else {
		 redirectAttributes.addFlashAttribute("errorMessage", "you deleted Item successfully!");
	 }
		modelAndView.setViewName("redirect:/orders");
		return modelAndView;
	}
	
	@RequestMapping(value = "/search" , method = RequestMethod.GET)
	public ModelAndView searchOrders(@RequestParam String query) {
		ModelAndView modelAndView = new ModelAndView();
		
	List<Order> filteredOrders	= orderServiceImp.searchOrders(query);
		modelAndView.addObject("ordersItems",filteredOrders);
		modelAndView.setViewName("orders");
		return modelAndView;
	}
	
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
	
}