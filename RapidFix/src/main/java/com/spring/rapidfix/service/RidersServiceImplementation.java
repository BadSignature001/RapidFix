package com.spring.rapidfix.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.spring.rapidfix.entities.RidersReg;
import com.spring.rapidfix.exceptions.RidersException;
import com.spring.rapidfix.repository.RidersRepository;

@Service
public class RidersServiceImplementation implements RidersService{
	
	@Autowired
	private RidersRepository ridersRepository ;
	
	@Autowired
	private PasswordEncoder passwordEncoder ;

	@Override
	public RidersReg registerUser(RidersReg rider) throws RidersException {
	
		Optional<RidersReg> isUsernameTaken = ridersRepository.findByUsername(rider.getUsername()) ;
		Optional<RidersReg> isMobileNoTaken = ridersRepository.findByMobileNumber(rider.getMobileNo()) ;
		
		if(isUsernameTaken.isPresent())
		{
			throw new RidersException("User by this username already exist"); 
		}
		
		if(isMobileNoTaken.isPresent())
		{
			throw new RidersException("Mobile Number Already Registered"); 
		}
		
		
		if(!rider.getConfirmPassword().equals(rider.getPassword()))
		{
			throw new RidersException("Confirm Password doesn't Match to Password") ;
		}
		
		
		String encodedPassword = passwordEncoder.encode(rider.getPassword()) ;
		
		rider.setPassword(encodedPassword); 
		
		
		return ridersRepository.save(rider);
	}

	@Override
	public boolean ValidateOtp(String GeneratedOtp, String otpFromUser) throws RidersException {
		
		
		
		if(!otpFromUser.equals(GeneratedOtp))
		{
			return false;
		}
		
		return true;
	}
	
	
	

}
