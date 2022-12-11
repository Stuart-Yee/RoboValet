package com.robovalet.services;

import java.util.ArrayList;
import java.util.Date;

import java.util.Optional;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;

import com.robovalet.models.LoginUser;
import com.robovalet.models.User;
import com.robovalet.models.User.Permission;
import com.robovalet.repositories.UserRepository;


@Service
public class UserService {
    private final UserRepository userRepository;
    
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    
    // register user and hash their password
    public User registerUser(User user) {
        String hashed = BCrypt.hashpw(user.getPasswordString(), BCrypt.gensalt());
        user.setPasswordHash(hashed);
        Permission perm = Permission.REGULAR;
        user.setPermission(perm);
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
}
