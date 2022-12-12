package com.robovalet.repositories;

import java.util.ArrayList;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.robovalet.models.Customer;

@Repository
public interface CustomerRepository extends CrudRepository<Customer, Long> {
	
	ArrayList<Customer> findByUserIsNull();

}
