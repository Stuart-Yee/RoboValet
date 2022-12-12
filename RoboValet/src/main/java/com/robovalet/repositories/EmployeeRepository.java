package com.robovalet.repositories;

import java.util.ArrayList;

import org.springframework.stereotype.Repository;
import org.springframework.data.repository.CrudRepository;

import com.robovalet.models.Employee;

@Repository
public interface EmployeeRepository extends CrudRepository<Employee, Long> {
	
	ArrayList<Employee> findByUserIsNull();
	
}
