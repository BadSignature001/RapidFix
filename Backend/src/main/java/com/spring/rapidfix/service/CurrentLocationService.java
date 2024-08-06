package com.spring.rapidfix.service;

import com.spring.rapidfix.entities.CurrentLocation;
import com.spring.rapidfix.exceptions.UserException;

public interface CurrentLocationService {
	
	public CurrentLocation updateLocation(CurrentLocation currentLocation , String token)throws UserException; 
	
	public String riderId(String token)throws UserException ;

}
//s