package com.spring.rapidfix.service;

import com.spring.rapidfix.entities.RidersReg;
import com.spring.rapidfix.exceptions.RidersException;
import com.spring.rapidfix.response.AuthenticationResponse;


public interface RidersService {
	
	public AuthenticationResponse registerUser(RidersReg rider)throws RidersException ;
	
	public AuthenticationResponse authenticate(RidersReg request) ;	
	
	public boolean ValidateOtp(String coderesult, String otpFromUser)throws RidersException ;
	
	

}

//s