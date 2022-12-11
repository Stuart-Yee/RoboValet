package com.robovalet.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.robovalet.models.LoginUser;
import com.robovalet.models.User;
import com.robovalet.validators.UserValidator;

@Controller
public class LoginController {
	@Autowired
	private UserValidator uVal;

	@GetMapping("/login")
	public String showLoginPage(
			@ModelAttribute("loginUser") LoginUser loginUser,
			@ModelAttribute("newUser") User newUser
			){
		
		return "loginReg.jsp";
	}
	
	@PostMapping("/login")
	public String login(
			@Valid 
			@ModelAttribute("loginUser") LoginUser loginUser, 
			BindingResult result
			) {
		System.out.println(loginUser.getUserName());
		return "redirect:/login";
	}
	
	@PostMapping("/register")
	public String registerUser(
			@Valid
			@ModelAttribute("newUser") User newUser,
			BindingResult result,
			Model model
			) {
		uVal.validate(newUser, result);
		if (result.hasErrors()) {
			LoginUser blankUser = new LoginUser();
			model.addAttribute("loginUser", blankUser);
			return "loginReg.jsp";
		}
		System.out.println("Registration success");
		return "redirect:/login";
	}
}
