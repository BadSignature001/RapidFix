package com.spring.rapidfix.service;

import com.spring.rapidfix.entities.RidersReg;
import com.spring.rapidfix.exceptions.RidersException;


public interface RidersService {
	
	public RidersReg registerUser(RidersReg rider)throws RidersException ;
	
	public boolean ValidateOtp(String coderesult, String otpFromUser)throws RidersException ;

}
