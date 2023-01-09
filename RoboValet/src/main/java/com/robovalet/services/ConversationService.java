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
		String custName = "UNKNOWN";
		if (conversation.getStay() != null && conversation.getStay().getCustomer() != null) {
			custName = conversation.getStay().getCustomer().getFirstName() + " "
					+ conversation.getStay().getCustomer().getLastName();
		}
		conversation.setChatLog(logs + "\n" + now.toString() + " " + custName + " says: " + message);
		cRepo.save(conversation);
	}
	
	public Conversation getActive(Stay stay) {
		ArrayList <Conversation> conversations = cRepo.findByStayAndStageNot(stay, Stage.CLOSED);
		if (conversations.size() == 1) {
			return conversations.get(0);
		} else {
			return null;
		}
	}
	
	public HashMap<String, Object> askStayVerification(Conversation conversation) {
		String message = "I'm sorry, we couldn't find you from this phone number. Please try again from the "
				+ "phone you used to check in or ask at the valet stand.";
		HashMap<String, Object> response = new HashMap<String, Object>();
		ArrayList<Stay> possibleStays = sRepo.findBySmsNumberAndStatusNotOrderByStatus(conversation.getSMS(), Status.DELIVERED);
		response.put("advanceStage", true);
		response.put("advanceStatus", false);
		if (possibleStays.size() == 0) {
			this.addSystemResponse(conversation, message);
			response.put("advanceStage", false);
			this.closeConversation(conversation);
		} else if (possibleStays.size() == 1) {
			conversation.setMultipleStaysPossible(false);
			Car car= possibleStays.get(0).getCar();
			Customer customer = possibleStays.get(0).getCustomer();
			message = "Hi, is this for the " + car.getColor() + " " + car.getYear() + " " + car.getMake()
			+ " " + car.getModel() + " (" + car.getPlate() + ") under the name of " + customer.getFirstName()
			+ " " + customer.getLastName() + "? Reply with YES";
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
		this.addSystemResponse(conversation, message);
		response.put("conversation", conversation);
		response.put("message", message);
		
		return response;
	}
	
	public HashMap<String, Object> verifyStay (Conversation conversation, String message) {
		HashMap<String, Object> response = new HashMap<String, Object>();
		String sysMsg = "Awesome! Let us know when you're ready to go";
		response.put("conversation", conversation);
		response.put("advanceStatus", false);
		ArrayList<Stay> possibleStays = sRepo.findBySmsNumberAndStatusNotOrderByStatus(conversation.getSMS(), Status.DELIVERED);
		if (conversation.getMultipleStaysPossible() || possibleStays.size() > 1) {
			ArrayList<String> options = new ArrayList<String>();
			for (Integer i=0; i < possibleStays.size(); i++) {
				Integer idx = i+1;
				String option = idx.toString();
				if (message.contains(option)) {
					options.add(option);
				}
			}
			if (options.size() > 1) {
				sysMsg = "Multiple numbers received, please reply with only one digit";
				response.put("advanceStage", false);
			} else if (options.size() == 0) {
				sysMsg = "I didn't get a number. Please send a NUMBER corresponding to the options above.";
				response.put("advanceStage", false);
			} else {
				Integer stayIdx = Integer.parseInt(options.get(0)) - 1;
				Stay stay = possibleStays.get(stayIdx);
				conversation.setStay(stay);
				response.put("advanceStage", true);
			}
		} else {
			if (message.contains("YES")) {
				conversation.setStay(possibleStays.get(0));
				response.put("advanceStage", true);
			} else {
				sysMsg = "Sorry, can you send YES if this is you?";
				response.put("advanceStage", false);
			}
		}
		response.put("message", sysMsg);
		this.addSystemResponse(conversation, sysMsg);
		return response;
		/*
		 * response is a hashmap as follows:
		 * "conversation": conversation object
		 * "message": String value to be sent as a reply to customer
		 * "advanceStatus": Should the status of the stay be advanced?
		 * "advanceStage": true or false - advance conversation stage?
		 */
	}
	
	public HashMap<String, Object> confirmReady(Conversation conversation, String message) {
		HashMap <String, Object> response = new HashMap<String, Object>();
		String sysMessage = "Ready to go? Send YES";
		this.addSystemResponse(conversation, sysMessage);
		response.put("advanceStatus", false);
		response.put("conversation", conversation);
		response.put("message", sysMessage);
		response.put("advanceStage", true);
		return response;
		
	}
	
	public HashMap<String, Object> requestCar(Conversation conversation, String message) {
		HashMap <String, Object> response = new HashMap<String, Object>();
		String sysMsg = "Great! Fetching your car now. You'll be notified when ready";
		if (message.contains("YES")) {
			response.put("advanceStatus", true);
			response.put("conversation", conversation);
			response.put("advanceStage", true);
			response.put("status", Status.REQUESTED);
			Stay stay = conversation.getStay();
			String logs = stay.getLog();
			Customer customer = stay.getCustomer();
			Date now = new Date();
			String update = "\n" + now.toString() + ": " + customer.getFirstName() 
			+ " " + customer.getLastName() + " requested their vehicle via SMS";
			stay.setLog(logs + update);
			sRepo.save(stay);
		} else {
			sysMsg="Sorry, I didn't understand that. Reply with YES if you're ready to go.";
			response.put("advanceStatus", false);
			response.put("conversation", conversation);
			response.put("advanceStage", false);
		}
		response.put("message", sysMsg);
		this.addSystemResponse(conversation, sysMsg);
		return response;
	}
	
	public HashMap <String, Object> placateCustomer(Conversation conversation, String message) {
		HashMap <String, Object> response = new HashMap <String, Object>();
		String sysMsg;
		response.put("advanceStatus", false);
		response.put("advanceStage", false);
		Stay stay = conversation.getStay();
		switch (stay.getStatus()) {
		case FETCHING: sysMsg = "The valet is on their way in your vehicle now and will be there in just a few moments!";
			break;
		case READY: sysMsg = "Your car is ready, we're just waiting for you!";
			break;
		default: sysMsg = "Just getting the keys now, thank you for your patience!";
			break;		
		}
		this.addSystemResponse(conversation, sysMsg);
		response.put("customer", conversation);
		response.put("message", sysMsg);
		return response;
	}
	
	public HashMap <String, Object> processInboundText(String sms, String message) {
		Conversation conversation = this.retrieveConversation(sms);
		System.out.println(conversation.getStage());
		HashMap <String, Object> response = new HashMap <String, Object>();
		this.addCustomerMessage(conversation, message);
		switch (conversation.getStage()) {
		case NEW: response = this.askStayVerification(conversation);
			break;
		case ASKEDSTAYVERIFICATION: response = this.verifyStay(conversation, message);
			break;
		case STAYVERIFIED: response = this.confirmReady(conversation, message);
			break;
		case ASKEDREADYVERIFICATION: response = this.requestCar(conversation, message);
			break;
		case READYVERIFIED: response = this.placateCustomer(conversation, message);
			break;
		}
		return response;
	}
	
	public void advanceStage(Conversation conversation) {
		switch(conversation.getStage()) {
		case NEW: conversation.setStage(Stage.ASKEDSTAYVERIFICATION);
			break;
		case ASKEDSTAYVERIFICATION: conversation.setStage(Stage.STAYVERIFIED);
			break;
		case STAYVERIFIED: conversation.setStage(Stage.ASKEDREADYVERIFICATION);
			break;
		case ASKEDREADYVERIFICATION: conversation.setStage(Stage.READYVERIFIED);
			break;
		case READYVERIFIED: conversation.setStage(Stage.READYVERIFIED);
			break;
		}
		cRepo.save(conversation);
	}
	
	
	
	
	

}
