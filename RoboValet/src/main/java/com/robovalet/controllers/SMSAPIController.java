package com.robovalet.controllers;

import org.springframework.web.bind.annotation.RestController;

import com.robovalet.models.Stay;
import com.robovalet.models.Stay.Status;
import com.robovalet.services.StayService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import java.util.HashMap;

@RequestMapping("/api")
@RestController
public class SMSAPIController {
	
	@Autowired
	StayService sServo;
	

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
	@PostMapping(value = "/ready", produces="application/xml")
	public String customerReady(@RequestBody HashMap<String, Object> request) {
		String beginning = "<Response><Message>";
		String end = "</Message></Response>";
		String message = "Please send the phrase 'READY' if you'd like us"
				+ " to fetch your vehicle for you.";
		String sms = (String) request.get("smsNumber");
		String requestMessage = (String) request.get("content");
		Stay stay = sServo.findBySMSandStatus(sms, Status.PARKED);
		if (stay == null) {
			message = "Sorry! Can't find you by " + sms + 
					". Please send your request from the number you checked in with"
					+ " or request your vehicle in person at the valet stand.";				
		} else if (requestMessage == "READY") { //TODO change to a contains
			message = "We received your request and will let you know when "
					+ "your vehicle is ready.";
			
		}
		return beginning + message + end;
	}

}
