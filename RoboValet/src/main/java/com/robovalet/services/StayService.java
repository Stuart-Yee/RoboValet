package com.robovalet.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.robovalet.repositories.StayRepository;

@Service
public class StayService {
	
	@Autowired
	StayRepository sRepo;

}
