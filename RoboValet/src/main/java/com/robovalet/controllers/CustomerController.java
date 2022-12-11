package com.robovalet.controllers;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.robovalet.models.Customer;
import com.robovalet.models.User;
import com.robovalet.services.CustomerService;
import com.robovalet.services.UserService;

@Controller
public class CustomerController {
	@Autowired
	CustomerService cServ;
	
	@Autowired
	UserService uServ;
	
	@GetMapping("/customers/register")
	public String register(
			@ModelAttribute("newCustomer") Customer customer,
			HttpSession session
			) {
		if (session.getAttribute("id") == null) {
			return "redirect:/login";
		}
		return "registerCustomer.jsp";
	}
	
	@PostMapping("/customers/register")
	public String registerPost(
			@Valid
			@ModelAttribute("newCustomer") Customer customer,
			BindingResult result,
			HttpSession session
			) {
		if (result.hasErrors()) {
			return "registerCustomer.jsp";
		}
		User loggedInUser = uServ.findUserById((Long)session.getAttribute("id"));
		System.out.println(customer.getSMSPermission());
		cServ.registerCustomer(customer, loggedInUser);
		return "redirect:/customers/register";
	}

}
