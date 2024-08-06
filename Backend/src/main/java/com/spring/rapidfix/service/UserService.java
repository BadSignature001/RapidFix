package com.spring.rapidfix.service;

import com.spring.rapidfix.entities.OrderFuelForm;
import com.spring.rapidfix.entities.UserReg;
import com.spring.rapidfix.exceptions.UserException;
import com.spring.rapidfix.response.AuthenticationResponse;


public interface UserService {
	
	public AuthenticationResponse registerUser(UserReg user)throws UserException ;
	
	public AuthenticationResponse authenticate(UserReg request) ;
	
	public UserReg findUserById(Integer userId) throws UserException;
	
	public OrderFuelForm orderFuel(OrderFuelForm userOrderDetails , Integer userId)throws UserException ;
	
	public UserReg findUserProfile(String token) throws UserException ;
	
	

}
//s