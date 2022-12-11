package com.robovalet.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.robovalet.models.Customer;
import com.robovalet.models.User;
import com.robovalet.repositories.CustomerRepository;

@Service
public class CustomerService {
	
	@Autowired
	CustomerRepository cRepo;
	
	public Customer registerCustomer(Customer newCustomer, User user) {
		newCustomer.setUser(user);
		return cRepo.save(newCustomer);
	}

}
