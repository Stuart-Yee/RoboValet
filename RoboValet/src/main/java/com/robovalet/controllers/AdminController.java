package com.robovalet.controllers;

import java.util.ArrayList;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.robovalet.models.User;
import com.robovalet.services.UserService;

@Controller
public class AdminController {
	@Autowired
	UserService uServ;
	
	@GetMapping("/admin")
	public String toAdmin(HttpSession session, Model model) {
		User user = uServ.findUserById((Long)session.getAttribute("id"));
		if(user.getPermission().toString() != "SUPER") {
			return "redirect:/login";
		}
		ArrayList<User> unassignedUsers = uServ.getUnassignedUsers();
		model.addAttribute("unassignedUsers", unassignedUsers);
		
		return "admin.jsp";
	}

}
