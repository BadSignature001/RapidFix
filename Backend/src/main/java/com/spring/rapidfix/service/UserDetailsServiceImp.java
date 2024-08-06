package com.spring.rapidfix.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.spring.rapidfix.repository.RidersRepository;
import com.spring.rapidfix.repository.UserRepository;

@Service
public class UserDetailsServiceImp implements UserDetailsService {

	private final UserRepository repository;
	private final RidersRepository ridersRepository; 

	public UserDetailsServiceImp(UserRepository repository, RidersRepository ridersRepository) {
		this.repository = repository;
		this.ridersRepository = ridersRepository;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		if (username.endsWith("@rider.com")) {
			return ridersRepository.findByUsername(username)
					.orElseThrow(() -> new UsernameNotFoundException("Rider not found"));
		}

		else {
			return repository.findByUsername(username)
					.orElseThrow(() -> new UsernameNotFoundException("User not found"));

		}

	}

	
	
	
}

//s