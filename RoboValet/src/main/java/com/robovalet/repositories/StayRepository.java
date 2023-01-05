package com.robovalet.repositories;

import org.springframework.stereotype.Repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.robovalet.models.Stay;
import com.robovalet.models.Stay.Status;

@Repository
public interface StayRepository extends CrudRepository<Stay, Long> {
	
	Optional<Stay> findBySmsNumberAndStatus(String SMS, Status status);

}
