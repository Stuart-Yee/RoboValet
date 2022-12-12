package com.robovalet.repositories;

import org.springframework.stereotype.Repository;
import org.springframework.data.repository.CrudRepository;

import com.robovalet.models.Stay;

@Repository
public interface StayRepository extends CrudRepository<Stay, Long> {

}
