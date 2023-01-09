package com.robovalet.controllers;

import org.springframework.web.bind.annotation.RestController;

import com.robovalet.models.Conversation;
import com.robovalet.models.Stay;
import com.robovalet.models.Stay.Status;
import com.robovalet.services.ConversationService;
import com.robovalet.services.StayService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import java.util.HashMap;

import javax.servlet.http.HttpSession;

@RequestMapping("/api")
@RestController
public class SMSAPIController {
	
	@Autowired
	StayService sServo;
	
	@Autowired
	ConversationService convoServ;
	

	@RequestMapping("")
	public String testRoute() {
		return "Hello!!!";
	}
	
	//Receives JSON, returns XML
	@PostMapping(value = "/xml-request-test", produces="application/xml")
	public String request1(@RequestBody HashMap<String, Object> request) {
		String beginning = "<Response><Message>";
		String end = "</Message></Response>";
		String message = "Success!";
		System.out.println(request.get("attribute"));
		return beginning + message + end;
	}
	
	//Receives JSON, returns XML
	/*
	 * JSON schema
	 * {
	 * 		"smsNumber": "14155551234",
	 * 		"content": "String"
	 * }
	 */
	@PostMapping(value = "/inbound", produces="application/xml")
	public String customerReady(HttpSession session, @RequestBody HashMap<String, Object> request) {
		String beginning = "<Response><Message>";
		String end = "</Message></Response>";
		String message;
		String sms = (String) request.get("smsNumber");
		String requestMessage = (String) request.get("content");
		HashMap<String, Object> systemResponse = convoServ.processInboundText(sms, requestMessage);
		message = (String) systemResponse.get("message");
		if ((boolean) systemResponse.get("advanceStatus")) {
			Conversation conversation = (Conversation) systemResponse.get("conversation");
			sServo.changeStatus(conversation.getStay(), (Status) systemResponse.get("status"));
		}
		if ((boolean) systemResponse.get("advanceStage")) {
			Conversation conversation = (Conversation) systemResponse.get("conversation");
			convoServ.advanceStage(conversation);
			
		}
		return beginning + message + end;
	}

}
