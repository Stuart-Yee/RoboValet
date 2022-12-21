package com.robovalet.services;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

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
	
	public ArrayList<Customer> getUniques(
			ArrayList<Customer> customers,
			Set<Long> ids
			) {
		ArrayList<Customer> returnList = new ArrayList<Customer>();
		for (Customer c: customers) {
			if (!ids.contains(c.getId())) {
				ids.add(null);
				returnList.add(c);
			}
		}
		return returnList;
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
		Set<Long> ids = new HashSet<Long>();
		for (Customer c: possibleCustomers) {
			ids.add(c.getId());
		}
		possibleCustomers.addAll(this.getUniques(byFirstAndLastNames, ids));
		possibleCustomers.addAll(this.getUniques(byLastName, ids));
		//TODO Remove duplicates by ID
		//TODO add search by email
		return possibleCustomers;
	}

}
