package com.spring.rapidfix.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.spring.rapidfix.entities.UserReg;
import com.spring.rapidfix.exceptions.UserException;
import com.spring.rapidfix.service.UserService;


import jakarta.validation.Valid;

@RestController
@RequestMapping("/endpoint/users")
public class UserController {
	
	@Autowired
	private UserService userService ;
	
	@PostMapping("/signup")
	public ResponseEntity<UserReg> registerUser(@Valid @RequestBody UserReg user)throws UserException
	{
		userService.registerUser(user) ;
		
		return new ResponseEntity<UserReg>(user,HttpStatus.CREATED);	
	}

}
