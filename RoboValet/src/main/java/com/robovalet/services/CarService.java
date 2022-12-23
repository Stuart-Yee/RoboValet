package com.robovalet.services;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.robovalet.models.Car;
import com.robovalet.models.Customer;
import com.robovalet.repositories.CarRepository;

@Service
public class CarService {
	
	@Autowired
	CarRepository carRepo;
	
	public Car findById(Long carId) {
		if (carRepo.findById(carId).isPresent()) {
			return carRepo.findById(carId).get();
		} else {
			return null;
		}
	}
	
	public ArrayList<Car> addUnique(
			ArrayList<Car> carList, 
			List<Car> customerCarList
			) {
		Set<Long> ids = new HashSet<Long>();
		for (Car car: carList) {
			ids.add(car.getId());
		}
		
		for (Car car: customerCarList) {
			if (! ids.contains(car.getId())) {
				carList.add(car);
				ids.add(car.getId());
			}
		}
		return carList;
	}
	
	//User enters Car details as a temp Car object to find an existing
	//record ordered by plate match followed by cars owned by the customer
	public ArrayList<Car> selectCar(Customer customer, Car carDetails) {
		ArrayList<Car> cars = carRepo.findByPlate(carDetails.getPlate());
		ArrayList<Car> searchResults = this.addUnique(cars, customer.getCars());
		return searchResults;
	}
	
	public Car register(Car newCar, Customer owner) {
		newCar.setCustomer(owner);
		return carRepo.save(newCar);
	}

}
