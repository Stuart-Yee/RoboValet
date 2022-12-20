package com.robovalet.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.robovalet.models.Customer;
import com.robovalet.services.CustomerService;
import com.robovalet.services.EmployeeService;

@Controller
public class StayController {

	@Autowired
	EmployeeService eServ;
	
	@Autowired
	CustomerService cServ;

//TODO
//	@Autowired
//	CarService carServ;
	
	@GetMapping("/checkin/customer")
	public String getCustomerDetails(
			@ModelAttribute("customerInfo") Customer customer
			) {
		return "checkin/customerDetails.jsp";
	}
	
	//TODO
	/*
	 * 1) Save customer details as temp Customer object in session
	 * 2) Use customer info to find existing customer or register new customer
	 * 3) Enter car info
	 * 4) Use car info to find or register car
	 * 5) Create Stay
	 */
	
}
