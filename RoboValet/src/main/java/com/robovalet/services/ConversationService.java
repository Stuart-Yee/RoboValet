package com.robovalet.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.robovalet.repositories.ConversationRepository;

@Service
public class ConversationService {
	
	@Autowired
	ConversationRepository cRepo;
	

}
