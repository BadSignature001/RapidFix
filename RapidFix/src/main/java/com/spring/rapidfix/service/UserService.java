package com.spring.rapidfix.service;

import com.spring.rapidfix.entities.UserReg;
import com.spring.rapidfix.exceptions.UserException;

public interface UserService {
	
	public UserReg registerUser(UserReg user)throws UserException ;

}
