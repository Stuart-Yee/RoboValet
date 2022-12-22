package com.robovalet.repositories;

import java.util.ArrayList;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.robovalet.models.Car;

@Repository
public interface CarRepository extends CrudRepository<Car, Long> {
	
	public ArrayList<Car> findByPlate(String plate);

}
