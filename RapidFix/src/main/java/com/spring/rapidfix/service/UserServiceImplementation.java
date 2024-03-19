package com.spring.rapidfix.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.rapidfix.entities.UserReg;
import com.spring.rapidfix.exceptions.UserException;
import com.spring.rapidfix.repository.UserRepository;

import org.springframework.security.crypto.password.PasswordEncoder;

@Service
public class UserServiceImplementation implements UserService{

	@Autowired
	private UserRepository userRepository  ;

	@Autowired
	private PasswordEncoder passwordEncoder; 
	
	
	@Override
	public UserReg registerUser(UserReg user) throws UserException {
		
		Optional<UserReg> isUsernameTaken = userRepository.findByUsername(user.getUsername()) ;
		Optional<UserReg> isMobileNoTaken = userRepository.findByMobileNumber(user.getMobileNo()) ;
		
		if(isUsernameTaken.isPresent()) {
			throw new UserException("Username Already Taken");
		}
		
		if(isMobileNoTaken.isPresent()) {
			throw new UserException("Already Registered Account for this Mobile Number");
		}
		
		 if (!user.getPassword().equals(user.getConfirmPassword())) {
		        throw new UserException("Confirm Password doesn't match with Entered Password");
		    }
		
		String encodedPassword = passwordEncoder.encode(user.getPassword()); 
		
	/*	UserReg new_user = new UserReg() ;
		new_user.setUsername(new_user.getUsername());
		new_user.setMobileNo(new_user.getMobileNo());  */
		user.setPassword(encodedPassword);	
		user.setConfirmPassword(encodedPassword);
		
		return userRepository.save(user) ;
			
		
	}

}
