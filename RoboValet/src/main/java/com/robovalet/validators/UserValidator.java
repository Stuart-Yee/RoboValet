package com.robovalet.validators;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.robovalet.models.User;
import com.robovalet.services.UserService;

@Component
public class UserValidator implements Validator {
	@Autowired
	UserService uServ;
    
    // 1
    @Override
    public boolean supports(Class<?> clazz) {
        return User.class.equals(clazz);
    }
    
    // 2
    @Override
    public void validate(Object target, Errors errors) {
        User user = (User) target;
        
        if (!user.getConfirmPassword().equals(user.getPasswordString())) {
            // 3
            errors.rejectValue("confirmPassword", "Match", "password does not match SPY");
        }   
        
        if (uServ.findByUserName(user.getUserName()) != null) {
        	errors.rejectValue("userName", "Match", "This user already exists");
        }
    }
}