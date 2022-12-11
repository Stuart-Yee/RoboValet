package com.robovalet.repositories;

import org.springframework.data.repository.CrudRepository;

import com.robovalet.models.Car;

public interface CarRepository extends CrudRepository<Car, Long> {

}
