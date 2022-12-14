package com.robovalet.controllers;

import java.util.ArrayList;
import java.util.Date;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.robovalet.models.*;
import com.robovalet.models.Stay.Status;
import com.robovalet.services.CarService;
import com.robovalet.services.ConversationService;
import com.robovalet.services.CustomerService;
import com.robovalet.services.EmployeeService;
import com.robovalet.services.StayService;
import com.robovalet.services.UserService;

@Controller
@RequestMapping("/checkin")
public class StayController {

	@Autowired
	EmployeeService eServ;
	
	@Autowired
	CustomerService cServ;

	@Autowired
	CarService carServ;
	
	@Autowired
	StayService sServ;
	
	@Autowired
	UserService uServ;
	
	@Autowired
	ConversationService convoServ;
	
	private Boolean checkEmployee(HttpSession session) {
		Long userId = (Long) session.getAttribute("id");
		
		if (userId == null) {
			System.out.println("No one logged in");
			return false;
		}
		
		User loggedInUser = uServ.findUserById(userId);
		if (loggedInUser.getEmployee() == null) {
			return false;
		}
		
		return true;
	}
	@GetMapping("/active")
	public String getStays(HttpSession session, Model model) {
		if (! this.checkEmployee(session)) {
			return "redirect:/login";
		}
		model.addAttribute("activeStays", sServ.getActiveStays());
		return "checkin/activeStays.jsp";
	}
	
	@GetMapping("/customer")
	public String getCustomerDetails(
			@ModelAttribute("customerInfo") Customer customer,
			HttpSession session
			) {
		if (! this.checkEmployee(session)) {
			return "redirect:/login";
		}
		return "checkin/customerDetails.jsp";
	}
	
	@PostMapping("/customer")
	public String findCustomer(
			@Valid
			@ModelAttribute("customerInfo") Customer customer,
			BindingResult result,
			HttpSession session
			) {
		if (! this.checkEmployee(session)) {
			return "redirect:/login";
		}
		session.setAttribute("customerDetails", customer);
		return "redirect:/checkin/customerSelect";
		
	}
	
	@GetMapping("/customerSelect")
	public String selectCustomer(HttpSession session, Model model) {
		if (! this.checkEmployee(session)) {
			return "redirect:/login";
		}
		if (session.getAttribute("customerDetails") == null) {
			return "redirect:/checkin/customer";
		}
		Customer custDetails = (Customer) session.getAttribute("customerDetails");
		ArrayList<Customer> possibleCustomers = cServ.customerSearch(custDetails);
		model.addAttribute("possibleCustomers", possibleCustomers);
		model.addAttribute("search", custDetails);
		return "/checkin/selectCustomer.jsp";
	}
	
	@PostMapping("/customer/register")
	public String newCustomer(HttpSession session) {
		if (! this.checkEmployee(session)) {
			return "redirect:/login";
		}
		Customer customer = (Customer) session.getAttribute("customerDetails");
		cServ.registerOrphanCustomer(customer);
		session.setAttribute("custId", customer.getId());
		return "redirect:/checkin/car/details";
	}
	
	@PostMapping("/customerSelect/{id}")
	public String selectExistingCustomer(
			@PathVariable("id") Long customerId,
			HttpSession session
			) {
		if (! this.checkEmployee(session)) {
			return "redirect:/login";
		}
		session.setAttribute("custId", customerId);
		return "redirect:/checkin/car/details";
	}
	
	@GetMapping("/car/details")
	public String carDetails(
			@ModelAttribute("carDetails") Car carInfo,
			HttpSession session
			) {
		if (! this.checkEmployee(session)) {
			return "redirect:/login";
		}
		return "/checkin/carDetails.jsp";
	}
	
	@PostMapping("/car")
	public String findCar(
			@Valid
			@ModelAttribute("carDetails") Car carInfo,
			BindingResult result,
			HttpSession session
			) {
		if (! this.checkEmployee(session)) {
			return "redirect:/login";
		}
		session.setAttribute("carInfo", carInfo);
		
		return "redirect:/checkin/car/select";
	}
	
	@GetMapping("/car/select")
	public String selectCarPage(
			Model model,
			HttpSession session
			) {
		if (! this.checkEmployee(session)) {
			return "redirect:/login";
		}
		if (session.getAttribute("custId") == null) {
			return "redirect:/checkin/customer";
		} else if (session.getAttribute("carInfo") == null ) {
			return "redirect:/checkin/car/details";
		}
		Car carInfo = (Car) session.getAttribute("carInfo");
		Customer customer = cServ.findById((Long)session.getAttribute("custId"));
		model.addAttribute("carInfo", carInfo);
		model.addAttribute("results", carServ.selectCar(customer, carInfo));
		return "/checkin/selectCar.jsp";
	}
	
	@PostMapping("/car/register")
	public String registerCar(HttpSession session) {
		if (! this.checkEmployee(session)) {
			return "redirect:/login";
		}
		Car newCar = (Car) session.getAttribute("carInfo");
		Customer customer = cServ.findById((Long) session.getAttribute("custId"));
		carServ.register(newCar, customer);
		session.setAttribute("carId", newCar.getId());
		return "redirect:/checkin/stay";
		
	}
	
	@PostMapping("/car/select/{carId}")
	public String selectCar(HttpSession session, @PathVariable("carId") Long carId) {
		if (! this.checkEmployee(session)) {
			return "redirect:/login";
		}
		session.setAttribute("carId", carId);
		return "redirect:/checkin/stay";
	}
	
	@GetMapping("/stay")
	public String stayDetails(HttpSession session, Model model) {
		if (! this.checkEmployee(session)) {
			return "redirect:/login";
		}
		Customer customer = cServ.findById((Long)session.getAttribute("custId"));
		Car car = carServ.findById((Long)session.getAttribute("carId"));
		Employee employee = uServ.findUserById((Long)session.getAttribute("id")).getEmployee();
		model.addAttribute("customer", customer);
		model.addAttribute("employee", employee);
		model.addAttribute("car", car);
		return "/checkin/stay.jsp";
	}
	
	@PostMapping("/stay")
	public String finalizeStay(
			HttpSession session, 
			Model model,
			@RequestParam("notes") String notes
			) {
		if (! this.checkEmployee(session)) {
			return "redirect:/login";
		}
		Customer customer = cServ.findById((Long)session.getAttribute("custId"));
		Car car = carServ.findById((Long)session.getAttribute("carId"));
		Employee employee = uServ.findUserById((Long)session.getAttribute("id")).getEmployee();
		Stay newStay = sServ.register(customer, car, employee, notes);
		model.addAttribute("stayInfo", newStay);
		return "redirect:/checkin/stay/view/" + newStay.getId().toString();
	}
	
	@GetMapping("/stay/view/{id}")
	public String viewStay(
			HttpSession session,
			Model model,
			@PathVariable("id") Long stayId
			) {
		if (! this.checkEmployee(session)) {
			return "redirect:/login";
		}
		Stay stay = sServ.findById(stayId);
		String[] chat = {"No SMS Conversation data."};
		Conversation conversation = stay.getConversation();
		if (conversation != null) {
			chat = conversation.getChatLog().split("\n");
		}
		//TESTING
		for (String msg: chat) {
			System.out.println(msg);
		}
		//END TESTING
		String[] logs = stay.getLog().split("\n");
		model.addAttribute("SMSlog", chat);
		model.addAttribute("logs", logs);
		model.addAttribute("stay", stay);
		
		return "/checkin/viewStay.jsp";
	}
	
	@PostMapping("/stay/{id}/park")
	public String park(
			HttpSession session,
			@PathVariable("id") Long stayId,
			@RequestParam("location") String location,
			@RequestParam("notes") String notes
			) {
		if (! this.checkEmployee(session)) {
			return "redirect:/login";
		}
		Employee employee = uServ.findUserById((Long) session.getAttribute("id"))
				.getEmployee();
		Stay stay = sServ.findById(stayId);
		String log = stay.getLog() + "\n" + new Date().toString() + ": Parked by " 
				+ employee.getFirstName() + " " + employee.getLastName() + ". Location: " 
				+ location + "\n" + notes;
		sServ.updateStatus(stay, Status.PARKED, employee, log);
		return "redirect:/checkin/stay/view/" + stayId;
	}
	
	@PostMapping("/stay/{id}/request")
	public String request(
			HttpSession session, 
			@PathVariable("id") Long stayId,
			@RequestParam("notes") String notes
			) {
		if (! this.checkEmployee(session)) {
			return "redirect:/login";
		}
		Employee employee = uServ.findUserById((Long) session.getAttribute("id"))
				.getEmployee();
		Stay stay = sServ.findById(stayId);
		String log = stay.getLog() + "\n" + new Date().toString() + ": Set to requested by "
				+ employee.getFirstName() + " " + employee.getLastName()
				+ "\n" + notes;
		sServ.updateStatus(stay, Status.REQUESTED, employee, log);
		return "redirect:/checkin/stay/view/" + stayId;
	}
	
	@PostMapping("/stay/{id}/fetch")
	public String fetch(
			HttpSession session, 
			@PathVariable("id") Long stayId,
			@RequestParam("notes") String notes			
			) {
		if (! this.checkEmployee(session)) {
			return "redirect:/login";
		}
		System.out.println("Fetching. Notes: " + notes);
		Employee employee = uServ.findUserById((Long) session.getAttribute("id"))
				.getEmployee();
		Stay stay = sServ.findById(stayId);
		String log = stay.getLog() + "\n" + new Date().toString() +": Fetched by "
				+ employee.getFirstName() + " " + employee.getLastName()
				+ "\n" + notes;
		sServ.updateStatus(stay, Status.FETCHING, employee, log);
		return "redirect:/checkin/stay/view/" + stayId;
	}
	
	@PostMapping("/stay/{id}/ready")
	public String setReady(
			HttpSession session, 
			@PathVariable("id") Long stayId,
			@RequestParam("notes") String notes
			) {
		if (! this.checkEmployee(session)) {
			return "redirect:/login";
		}
		Employee employee = uServ.findUserById((Long) session.getAttribute("id"))
				.getEmployee();
		Stay stay = sServ.findById(stayId);
		String log = stay.getLog() + "\n" + new Date().toString() + ": Marked ready by "
				+ employee.getFirstName() + " " + employee.getLastName()
				+ "\n" + notes;
		sServ.updateStatus(stay, Status.READY, employee, log);
		return "redirect:/checkin/stay/view/" + stayId;
	}
	
	@PostMapping("/stay/{id}/deliver")
	public String deliver(
			HttpSession session, 
			@PathVariable("id") Long stayId,
			@RequestParam("notes") String notes
			) {
		if (! this.checkEmployee(session)) {
			return "redirect:/login";
		}
		Employee employee = uServ.findUserById((Long) session.getAttribute("id"))
				.getEmployee();
		Stay stay = sServ.findById(stayId);
		String log = stay.getLog() + "\n" + new Date().toString() +": Delivered by "
				+ employee.getFirstName() + " " + employee.getLastName()
				+ "\n" + notes;
		sServ.updateStatus(stay, Status.DELIVERED, employee, log);
		return "redirect:/checkin/stay/view/" + stayId;
	}
	
}
