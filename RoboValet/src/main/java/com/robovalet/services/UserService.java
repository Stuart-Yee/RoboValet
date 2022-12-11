package com.robovalet.services;

import java.util.Optional;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;

import com.robovalet.models.User;
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
    public boolean authenticateUser(String userName, String password) {
        // first find the user by email
        User user = userRepository.findByUserName(userName);
        // if we can't find it by email, return false
        if(user == null) {
            return false;
        } else {
        // if the passwords match, return true, else, return false
            if(BCrypt.checkpw(password, user.getPasswordHash())) {
                return true;
            } else {
                return false;
            }
        }
    }
}
