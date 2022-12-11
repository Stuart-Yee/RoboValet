package com.robovalet.repositories;

import org.springframework.data.repository.CrudRepository;

import com.robovalet.models.Customer;

public interface CustomerRepository extends CrudRepository<Customer, Long> {

}
