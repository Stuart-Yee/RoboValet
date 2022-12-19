package com.robovalet.repositories;

import java.util.ArrayList;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.robovalet.models.User;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
	User findByUserName(String search);
	
	ArrayList<User> findByEmployeeIsNullAndCustomerIsNull();
	
	ArrayList<User> findByEmployeeEnabledTrueAndEmployeeIsNull();
	
	ArrayList<User> findByEmployeeIsNotNull();
	
	ArrayList<User> findByEmployeeEnabledFalseOrEmployeeEnabledIsNull();
	
	
}
