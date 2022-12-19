package com.robovalet.services;

import java.util.ArrayList;
import java.util.Date;

import java.util.Optional;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.robovalet.models.Customer;
import com.robovalet.models.Employee;
import com.robovalet.models.LoginUser;
import com.robovalet.models.User;
import com.robovalet.models.User.Permission;
import com.robovalet.repositories.CustomerRepository;
import com.robovalet.repositories.EmployeeRepository;
import com.robovalet.repositories.UserRepository;


@Service
public class UserService {
	
	@Autowired
    UserRepository userRepository;
	
	@Autowired
	CustomerRepository cRepo;
	
	@Autowired
	EmployeeRepository eRepo;
    
    // register user and hash their password
    public User registerUser(User user) {
        String hashed = BCrypt.hashpw(user.getPasswordString(), BCrypt.gensalt());
        user.setPasswordHash(hashed);
        Permission perm = Permission.REGULAR;
        user.setPermission(perm);
        user.setEmployeeEnabled(false);
        return userRepository.save(user);
    }

    // find user by email
    public User findByUserName(String search) {
        return userRepository.findByUserName(search);
    }
    
    // find user by id
    public User findUserById(Long id) {
    	Optional<User> u = userRepository.findById(id);
    	
    	if(u.isPresent()) {
            return u.get();
    	} else {
    	    return null;
    	}
    }
    
    // authenticate user
    public User authenticateUser(LoginUser loginUser) {
        // first find the user by email
        User user = userRepository.findByUserName(loginUser.getUserName());
        // if we can't find it by email, return false
        if(user == null) {
            return null;
        } else {
        // if the passwords match, return true, else, return false
            if(BCrypt.checkpw(loginUser.getPassword(), user.getPasswordHash())) {
            	user.setLastLoggedIn(new Date());
            	userRepository.save(user);
                return user;
            } else {
                return null;
            }
        }
    }
    
    public ArrayList<User> getUnassignedUsers() {
    	return userRepository.findByEmployeeIsNullAndCustomerIsNull();
    }
    
    public void linkCustomer(User user, Customer customer) {
    	user.setCustomer(customer);
    	customer.setUser(user);
    	userRepository.save(user);
    	cRepo.save(customer);
    }
    
    public void linkEmployee(User user, Employee employee) {
    	user.setEmployee(employee);
    	employee.setUser(user);
    	userRepository.save(user);
    	eRepo.save(employee);
    }
    
    public void deleteUser(User user) {
    	userRepository.delete(user);
    }
    
    public ArrayList<User> findToRegisterAsEmployees(){
    	return userRepository.findByEmployeeEnabledTrueAndEmployeeIsNull();
    }
    
    public ArrayList<User> findNotEmployeeEnabled() {
    	return userRepository.findByEmployeeEnabledFalseOrEmployeeEnabledIsNull();
    }
    
    public void enableAsEmployee(User user) {
    	user.setEmployeeEnabled(true);
    	userRepository.save(user);
    }
    
    
}
