package com.robovalet.repositories;

import org.springframework.data.repository.CrudRepository;

import com.robovalet.models.Employee;

public interface EmployeeRepository extends CrudRepository<Employee, Long> {

}
