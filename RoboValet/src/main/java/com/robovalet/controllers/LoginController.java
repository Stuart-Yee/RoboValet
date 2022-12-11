package com.robovalet.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.robovalet.models.LoginUser;
import com.robovalet.models.User;

@Controller
public class LoginController {

	@GetMapping("/login")
	public String showLoginPage(
			@ModelAttribute("loginUser") LoginUser loginUser,
			@ModelAttribute("newUser") User newUser
			){
		
		return "loginReg.jsp";
	}
}
