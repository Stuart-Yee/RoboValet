package com.robovalet.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.robovalet.models.Car;
import com.robovalet.models.Conversation;
import com.robovalet.models.Customer;
import com.robovalet.models.Stay;
import com.robovalet.models.Conversation.Stage;
import com.robovalet.models.Stay.Status;
import com.robovalet.repositories.ConversationRepository;
import com.robovalet.repositories.CustomerRepository;
import com.robovalet.repositories.StayRepository;

@Service
public class ConversationService {
	
	@Autowired
	ConversationRepository cRepo;
	
	@Autowired
	CustomerRepository custRepo;
	
	@Autowired
	StayRepository sRepo;
	
	public Conversation createConversation (String sms) {
		Conversation conversation = new Conversation(sms, Stage.NEW);
		cRepo.save(conversation);
		return conversation;
	}
	
	public Conversation findBySMS (String sms) {
		Optional <Conversation> conversation = cRepo.findBySMSAndClosedAtIsNull(sms);
		if (conversation.isPresent()) {
			return conversation.get();
		} else {
			return null;
		}
	}
	
	public Conversation retrieveConversation (String sms) {
		Conversation conversation = this.findBySMS(sms);
		if (conversation == null) {
			return this.createConversation(sms);
		} else {
			return conversation;
		}
	}
	
	public Conversation closeConversation(Conversation conversation) {
		conversation.setStage(Stage.CLOSED);
		conversation.setClosedAt(new Date());
		cRepo.save(conversation);
		return conversation;
	}
	
	public void addSystemResponse(Conversation conversation, String message) {
		String logs = conversation.getChatLog();
		Date now = new Date();
		conversation.setChatLog(logs + "\n" + now.toString() + " SystemResponse: " + message);
		cRepo.save(conversation);
	}
	
	public void addCustomerMessage(Conversation conversation, String message) {
		String logs = conversation.getChatLog();
		Date now = new Date();
		String custName = conversation.getStay().getCustomer().getFirstName() + " "
				+ conversation.getStay().getCustomer().getLastName();
		conversation.setChatLog(logs + "\n" + now.toString() + " " + custName + " says: " + message);
		cRepo.save(conversation);
	}
	
	public HashMap<String, Object> askStayVerification(Conversation conversation) {
		String message = "I'm sorry, we couldn't find you from this phone number. Please try again from the "
				+ "phone you used to check in or ask at the valet stand.";
		HashMap<String, Object> response = new HashMap<String, Object>();
		ArrayList<Stay> possibleStays = sRepo.findBySmsNumberAndStatusNotOrderByStatus(conversation.getSMS(), Status.DELIVERED);
		if (possibleStays.size() == 0) {
			this.addSystemResponse(conversation, message);
			this.closeConversation(conversation);
		} else if (possibleStays.size() == 1) {
			conversation.setMultipleStaysPossible(false);
			Car car= possibleStays.get(0).getCar();
			Customer customer = possibleStays.get(0).getCustomer();
			message = "Hi, is this for the " + car.getColor() + " " + car.getYear() + " " + car.getMake()
			+ " " + car.getModel() + " (" + car.getPlate() + ") under the name of " + customer.getFirstName()
			+ " " + customer.getLastName() + "?";
		} else {
			conversation.setMultipleStaysPossible(true);
			message = "I have several matches, please select by replying with the number that matches your vehicle\n";
			for (Integer i = 0; i < possibleStays.size(); i++) {
				Integer num = i + 1;
				Stay stay = possibleStays.get(i);
				Car car = stay.getCar();
				Customer customer = stay.getCustomer();
				String option = num.toString() + ": " + car.getColor() + " " + car.getModel() + " (" + car.getPlate() 
				+ ") under " + customer.getFirstName() + " " + " " + customer.getLastName() + "\n";
				message += option;
			}
		}
		// change stage AFTER message is actually sent
		cRepo.save(conversation);
		response.put("conversation", conversation);
		response.put("message", message);
		return response;
	}
	
	
	
	
	

}
