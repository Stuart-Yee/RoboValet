package com.robovalet.services;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.robovalet.models.Car;
import com.robovalet.models.Customer;
import com.robovalet.models.Employee;
import com.robovalet.models.Stay;
import com.robovalet.repositories.StayRepository;
import com.robovalet.models.Stay.Status;

@Service
public class StayService {
	
	@Autowired
	StayRepository sRepo;
	
	public Stay findById(Long id) {
		if(sRepo.findById(id).isPresent()) {
			return sRepo.findById(id).get();
		}
		return null;
	}
	
	public Stay register(Customer customer, Car car, Employee employee, String notes) {
		Date now = new Date ();
		Stay newStay = new Stay();
		newStay.setStatus(Status.PARKING);
		newStay.setCustomer(customer);
		newStay.setEmployee(employee);
		newStay.setCar(car);
		newStay.setCheckInTime(now);
		newStay.setNotes(notes);
		newStay.setLog(
				now.toString() + ": " + employee.getFirstName() + " " + employee.getLastName() +
				" checked in this " + car.getModel() + " for " + customer.getFirstName() + " " +
				customer.getLastName() + "."				
				);
		
		return sRepo.save(newStay);
	}

}
