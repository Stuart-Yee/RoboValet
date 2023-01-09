package com.robovalet.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.robovalet.models.Car;
import com.robovalet.models.Conversation;
import com.robovalet.models.Customer;
import com.robovalet.models.Employee;
import com.robovalet.models.Stay;
import com.robovalet.models.Conversation.Stage;
import com.robovalet.repositories.ConversationRepository;
import com.robovalet.repositories.StayRepository;
import com.robovalet.models.Stay.Status;

@Service
public class StayService {
	
	@Autowired
	StayRepository sRepo;
	
	@Autowired
	ConversationRepository convoRepo;
	
	@Autowired
	ConversationService convoServ;
	
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
	
//	public void requestVehicle(Stay stay) {
//		stay.setStatus(Status.REQUESTED);
//		String logs = stay.getLog();
//		Date now = new Date();
//		stay.setStatusChange(now);
//		logs = logs + "\n" + now.toString() + ": Customer has requested the car via SMS.";
//		stay.setLog(logs);
//		sRepo.save(stay);
//	}
	
	public void closeStay(Stay stay) {
		stay.setCheckOutTime(new Date());
		stay.setStatus(Status.DELIVERED);
		sRepo.save(stay);
		ArrayList<Conversation> conversations = convoRepo.findByStayAndStageNot(stay, Stage.CLOSED);
		for (int i = 0; i < conversations.size(); i++) {
			convoServ.closeConversation(conversations.get(i));
		}
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
			this.closeStay(stay);
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
		newStay.setSmsNumber(customer.getSMSPhone());
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
	
	public ArrayList<Stay> getActiveStaysBySMS(String SMS) {
		return sRepo.findBySmsNumberAndStatusNotOrderByStatus(SMS, Status.DELIVERED);
	}
	
	public void advanceStatus(Stay stay) {
		switch (stay.getStatus()) {
		case PARKING: stay.setStatus(Status.PARKED);
			break;
		case PARKED: stay.setStatus(Status.REQUESTED);
			break;
		case REQUESTED: stay.setStatus(Status.FETCHING);
			break;
		case FETCHING: stay.setStatus(Status.READY);
			break;
		case READY: this.closeStay(stay);
			break;
		}
		sRepo.save(stay);
	}
	
	public void changeStatus(Stay stay, Status status ) {
		stay.setStatus(status);
		sRepo.save(stay);
		if (status == Status.DELIVERED) {
			this.closeStay(stay);
		}
	}

}
