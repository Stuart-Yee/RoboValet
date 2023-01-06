package com.robovalet.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;

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
	
	public Stay findBySMSandStatus(String SMS, Status status) {
		Optional<Stay> stay = sRepo.findBySmsNumberAndStatus(SMS, status);
		if (stay.isPresent()) {
			return stay.get();
		}
		return null;
	}
	
	public void requestVehicle(Stay stay) {
		stay.setStatus(Status.REQUESTED);
		String logs = stay.getLog();
		Date now = new Date();
		stay.setStatusChange(now);
		logs = logs + "\n" + now.toString() + ": Customer has requested the car via SMS.";
		stay.setLog(logs);
		sRepo.save(stay);
	}
	
	public void updateStatus(
			Stay stay, 
			Status status,
			Employee employee,
			String logs
			) {
		if (!(logs == null)) {
			stay.setLog(logs);
		}
		stay.setEmployee(employee);
		stay.setStatusChange(new Date());
		stay.setStatus(status);
		if (status == Status.DELIVERED) {
			stay.setCheckOutTime(new Date());
		}
		sRepo.save(stay);
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
	
	public ArrayList<Stay> getActiveStays(){
		return sRepo.findByStatusNotOrderByStatusDesc(Status.DELIVERED);
	}

}
