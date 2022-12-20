package com.robovalet.services;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.robovalet.models.Customer;
import com.robovalet.models.User;
import com.robovalet.repositories.CustomerRepository;
import com.robovalet.repositories.UserRepository;

@Service
public class CustomerService {
	
	@Autowired
	CustomerRepository cRepo;
	
	@Autowired
	UserRepository uRepo;
	
	public Customer registerCustomer(Customer newCustomer, User user) {
		newCustomer.setUser(user);
		user.setCustomer(newCustomer);
		uRepo.save(user);
		return newCustomer;
	}
	
	public Customer findById(Long id) {
		Optional<Customer> c = cRepo.findById(id);
		if(c.isPresent()) {
			return c.get();
		} else {
			return null;
		}
	}
	
	public ArrayList<Customer> getUnassigned() {
		return cRepo.findByUserIsNull();
	}
	
	public ArrayList<Customer> customerSearch(Customer customerInfo) {
		//The Customer object customerInfo is not persisted and is just a temp
		//object to hold customer information
		ArrayList<Customer> byPhone = cRepo.findBySMSPhone(customerInfo.getSMSPhone());
		ArrayList<Customer> byFirstAndLastNames = cRepo.findByLastNameAndFirstName(
				customerInfo.getLastName(), 
				customerInfo.getFirstName()
				);
		ArrayList<Customer> byLastName = cRepo.findByLastName(customerInfo.getLastName());
		ArrayList<Customer> possibleCustomers = new ArrayList<Customer>();
		possibleCustomers.addAll(byPhone);
		possibleCustomers.addAll(byFirstAndLastNames);
		possibleCustomers.addAll(byLastName);
		//TODO Remove duplicates by ID
		//TODO add search by email
		return possibleCustomers;
	}

}
