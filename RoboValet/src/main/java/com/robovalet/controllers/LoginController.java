package com.robovalet.controllers;

import javax.validation.Valid;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.robovalet.models.LoginUser;
import com.robovalet.models.User;
import com.robovalet.services.UserService;
import com.robovalet.validators.UserValidator;

@Controller
public class LoginController {
	@Autowired
	private UserValidator uVal;
	@Autowired 
	private UserService uServ;

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
			BindingResult result,
			HttpSession session,
			Model model
			) {
		User user = uServ.authenticateUser(loginUser);
		if (user == null) {
			System.out.println("Login failure");
			result.rejectValue("userName", "Match", "Incorrect username or password");
			model.addAttribute("newUser", new User());
			return "loginReg.jsp";
		}
		
		session.setAttribute("id", user.getId());
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
		uServ.registerUser(newUser);
		return "redirect:/login";
	}
}