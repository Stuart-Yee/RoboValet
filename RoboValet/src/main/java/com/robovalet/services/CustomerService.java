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
		return cRepo.save(newCustomer);
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

}
