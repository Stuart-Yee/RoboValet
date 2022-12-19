package com.robovalet.controllers;

import java.util.Objects;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.robovalet.models.Employee;
import com.robovalet.models.User;
import com.robovalet.services.EmployeeService;
import com.robovalet.services.UserService;

@Controller
public class EmployeeController {
	
	@Autowired
	EmployeeService eServ;
	
	@Autowired
	UserService uServ;
	
	@GetMapping("/employees/register")
	public String employeeForm(
			@ModelAttribute("newEmployee") Employee employee,
			HttpSession session
			) {
		Long userId = (Long) session.getAttribute("id");
		System.out.println(userId);
		if (Objects.isNull(userId)) {
			return "redirect:/login";			
		}
		User loggedInUser = uServ.findUserById(userId);
		if (!loggedInUser.getEmployeeEnabled()) {
			return "redirect:/login";
		}
		return "registerEmployee.jsp";
	}
	
	@PostMapping("/employees/register")
	public String registerEmployee(
			@Valid
			@ModelAttribute("newEmployee") Employee employee,
			BindingResult result,
			HttpSession session
			) {
		if (result.hasErrors()) {
			return "loginReg.jsp";
		}
		User user = uServ.findUserById((Long)session.getAttribute("id"));
		eServ.registerEmployee(employee);
		uServ.linkEmployee(user, employee);
		return "redirect:/login";
	}

}
