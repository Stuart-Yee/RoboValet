package com.robovalet.controllers;

import java.util.ArrayList;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.robovalet.models.Customer;
import com.robovalet.services.CustomerService;
import com.robovalet.services.EmployeeService;

@Controller
@RequestMapping("/checkin")
public class StayController {

	@Autowired
	EmployeeService eServ;
	
	@Autowired
	CustomerService cServ;

//TODO
//	@Autowired
//	CarService carServ;
	
	@GetMapping("/customer")
	public String getCustomerDetails(
			@ModelAttribute("customerInfo") Customer customer
			) {
		return "checkin/customerDetails.jsp";
	}
	
	@PostMapping("/customer")
	public String findCustomer(
			@Valid
			@ModelAttribute("customerInfo") Customer customer,
			BindingResult result,
			HttpSession session
			) {
		session.setAttribute("customerDetails", customer);
		return "redirect:/checkin/customerSelect";
		
	}
	
	@GetMapping("/customerSelect")
	public String selectCustomer(HttpSession session, Model model) {
		if (session.getAttribute("customerDetails") == null) {
			return "redirect:/checkin/customer";
		}
		Customer custDetails = (Customer) session.getAttribute("customerDetails");
		ArrayList<Customer> possibleCustomers = cServ.customerSearch(custDetails);
		for (Customer customer : possibleCustomers) {
			System.out.println(customer.getId());
			System.out.println(customer.getFirstName()+customer.getLastName());
		}
		return "/checkin/selectCustomer.jsp";
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
