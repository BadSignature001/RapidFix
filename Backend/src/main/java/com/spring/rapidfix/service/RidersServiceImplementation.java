package com.spring.rapidfix.service;

import java.util.List;
import java.util.Optional;


import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.spring.rapidfix.entities.RidersReg;
import com.spring.rapidfix.entities.RidersToken;
import com.spring.rapidfix.exceptions.RidersException;
import com.spring.rapidfix.repository.RidersRepository;
import com.spring.rapidfix.repository.RidersTokenRepository;
import com.spring.rapidfix.response.AuthenticationResponse;

@Service
public class RidersServiceImplementation implements RidersService {

	private final RidersRepository ridersRepository;
	private final PasswordEncoder passwordEncoder;
	private final JwtService jwtService;
    private final RidersTokenRepository tokenrepository;
    private final AuthenticationManager authenticationManager;
	

	public RidersServiceImplementation(
										RidersRepository ridersRepository,
										PasswordEncoder passwordEncoder,				
										JwtService jwtService,
										RidersTokenRepository tokenrepository,
										AuthenticationManager authenticationManager) 
	{
		this.ridersRepository = ridersRepository;
		this.passwordEncoder = passwordEncoder;				
		this.jwtService = jwtService ;
		this.authenticationManager = authenticationManager ;
		this.tokenrepository = tokenrepository ;
	}
	
	

	@Override
	public AuthenticationResponse registerUser(RidersReg rider) throws RidersException 
	{
		Optional<RidersReg> isUsernameTaken = ridersRepository.findByUsername(rider.getUsername());
		Optional<RidersReg> isMobileNoTaken = ridersRepository.findByMobileNumber(rider.getMobileNo());

		if (isUsernameTaken.isPresent()) 
		{
			throw new RidersException("Username Already Exist");
		}

		if (isMobileNoTaken.isPresent()) 
		{
			throw new RidersException("Mobile Number Already Registered");
		}

		if (!rider.getConfirmPassword().equals(rider.getPassword())) 
		{
			throw new RidersException("Confirm Password doesn't Match to Password");
		}

		String encodedPassword = passwordEncoder.encode(rider.getPassword());

		rider.setPassword(encodedPassword);

		ridersRepository.save(rider);
		
		String jwt = jwtService.generateToken(rider);
		
		saveUserToken(jwt, rider);

	    return new AuthenticationResponse(jwt, "Rider registration was successful");	
	}
	
	
	public AuthenticationResponse authenticate(RidersReg request) 
	{
		authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));

		RidersReg rider = ridersRepository.findByUsername(request.getUsername()).orElseThrow();
		String jwt = jwtService.generateToken(rider);
		revokeAllTokenByUser(rider);
		saveUserToken(jwt, rider);

		return new AuthenticationResponse(jwt, "Rider login was successful");
	}
	

	@Override
	public boolean ValidateOtp(String GeneratedOtp, String otpFromUser) throws RidersException 
	{
		if (!otpFromUser.equals(GeneratedOtp)) 
		{
			return false;
		}

		return true;
	}
	

	private void revokeAllTokenByUser(RidersReg rider) 
	{
		List<RidersToken> validTokens = tokenrepository.findAllTokensByUser(rider.getId());

		if (validTokens.isEmpty()) {
			return;
		}

		validTokens.forEach(t -> {
			t.setLoggedOut(true);
		});

		tokenrepository.saveAll(validTokens);
	}
	
	

	private void saveUserToken(String jwt, RidersReg rider)
	{
		RidersToken token = new RidersToken();
		token.setToken(jwt);
		token.setLoggedOut(false);
		token.setRider(rider);
		tokenrepository.save(token);
	}
	
	

}

//s
