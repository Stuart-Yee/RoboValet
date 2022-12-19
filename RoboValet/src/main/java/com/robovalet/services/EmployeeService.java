package com.robovalet.services;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.robovalet.repositories.EmployeeRepository;
import com.robovalet.repositories.UserRepository;
import com.robovalet.models.Employee;
import com.robovalet.models.User;

@Service
public class EmployeeService {
	
	@Autowired
	EmployeeRepository eRepo;
	
	@Autowired
	UserRepository uRepo;
	
	public ArrayList<Employee> getUnassigned() {
		return eRepo.findByUserIsNull();
	}
	
	public Employee findById(Long id) {
		Optional<Employee> e = eRepo.findById(id);
		if (e.isPresent()) {
			return e.get();
		} else {
			return null;
		}
	}
	
	public Employee registerEmployee(Employee employee) {
		return eRepo.save(employee);
	}
	
	public void linkUser(User user, Employee employee) {
		employee.setUser(user);
		user.setEmployee(employee);
		eRepo.save(employee);
		uRepo.save(user);
	}

}
