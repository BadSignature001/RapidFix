package com.spring.rapidfix.service;

import java.util.List;
import java.util.Optional;


import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.spring.rapidfix.entities.OrderFuelForm;
import com.spring.rapidfix.entities.UserReg;
import com.spring.rapidfix.entities.UserToken;
import com.spring.rapidfix.exceptions.UserException;
import com.spring.rapidfix.repository.OrderFuelRepository;
import com.spring.rapidfix.repository.UserRepository;
import com.spring.rapidfix.repository.UserTokenRepository;
import com.spring.rapidfix.response.AuthenticationResponse;

import io.jsonwebtoken.Claims;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;

@Service
public class UserServiceImplementation implements UserService{
	
	@Value("${osm.api.url}")
    private String osmApiUrl;

	private final OrderFuelRepository orderFuelRepository ;
	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	private final JwtService jwtService;
    private final UserTokenRepository tokenRepository;
    private final AuthenticationManager authenticationManager;
    

    public UserServiceImplementation(UserRepository userRepository,
                                 PasswordEncoder passwordEncoder,
                                 JwtService jwtService,
                                 UserTokenRepository tokenRepository,
                                 AuthenticationManager authenticationManager,
                                 OrderFuelRepository orderFuelRepository)
    {
    	
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.tokenRepository = tokenRepository;
        this.authenticationManager = authenticationManager;
        this.orderFuelRepository= orderFuelRepository ;
    }
	
	
	@Override
	public AuthenticationResponse registerUser(UserReg user) throws UserException 
	{		
		Optional<UserReg> isUsernameTaken = userRepository.findByUsername(user.getUsername()) ;
		Optional<UserReg> isMobileNoTaken = userRepository.findByMobileNumber(user.getMobileNo()) ;
		
		if(isUsernameTaken.isPresent()) 
		{
			throw new UserException("Username Already Exist");
		}
		
		if(isMobileNoTaken.isPresent()) 
		{
			throw new UserException("Already Registered Account for this Mobile Number");
		}
		
		 if (!user.getPassword().equals(user.getConfirmPassword())) 
		 {
		    throw new UserException("Confirm Password doesn't match with Entered Password");
		 }
		
		String encodedPassword = passwordEncoder.encode(user.getPassword()); 
		
		user.setPassword(encodedPassword);	
		user.setConfirmPassword(encodedPassword);
		
		 userRepository.save(user) ;
		 String jwt = jwtService.generateToken(user);
		 saveUserToken(jwt, user);

	     return new AuthenticationResponse(jwt, "User Registration Successful");
	}
	
	
	public AuthenticationResponse authenticate(UserReg request) 
	{
		authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));

		UserReg user = userRepository.findByUsername(request.getUsername()).orElseThrow();
		String jwt = jwtService.generateToken(user);

		revokeAllTokenByUser(user);
		saveUserToken(jwt, user);

		return new AuthenticationResponse(jwt, "User Login Successful");

	}
	
	
	@Override
	public OrderFuelForm orderFuel(OrderFuelForm userOrderDetails , Integer userId) throws UserException
	{	
		UserReg user = findUserById(userId) ;
		
		String autofetchAddress = userOrderDetails.getAutoAddress();
		
		RestTemplate restTemplate = new RestTemplate();
	    String apiUrl = osmApiUrl + "?q=" + autofetchAddress + "&format=json";
	    
	    
	    ResponseEntity<String> response = restTemplate.getForEntity(apiUrl, String.class);
	    if (response.getStatusCode().is2xxSuccessful()) {
	        String responseBody = response.getBody();
	        try {
	            JSONArray jsonArray = new JSONArray(responseBody);
	            JSONObject firstResult = jsonArray.getJSONObject(0);

	            double latitude = firstResult.getDouble("lat");
	            double longitude = firstResult.getDouble("lon");

	            userOrderDetails.setLatitude(latitude);
	            userOrderDetails.setLongitude(longitude);
	            
	            
	        } catch (JSONException e) {
	            e.printStackTrace(); 
	            return null;
	        }
	    } else {
	        System.out.println("Error response from API: " + response.getBody());
	        return null;
	    }
	    
	    
		
		userOrderDetails.setUser(user);
		
		return orderFuelRepository.save(userOrderDetails);
	}
	    
	    
	private void revokeAllTokenByUser(UserReg user) 
	{
		List<UserToken> validTokens = tokenRepository.findAllTokensByUser(user.getId());
		
		if (validTokens.isEmpty()) 
		{
			return;
		}
		
		validTokens.forEach(t -> {
			t.setLoggedOut(true);
		});

		tokenRepository.saveAll(validTokens);
	}
	    
	    
	private void saveUserToken(String jwt, UserReg user) {
		UserToken token = new UserToken();
		token.setToken(jwt);
		token.setLoggedOut(false);
		token.setUser(user);
		tokenRepository.save(token);
	}
	

	@Override
	public UserReg findUserById(Integer userId) throws UserException 
	{		
		Optional<UserReg> opt = userRepository.findById(userId);
		
		if(opt.isPresent()) 
		{
			return opt.get();
		}
		
		throw new UserException("user not found with userid :"+userId);
	}
	
	@Override
	public UserReg findUserProfile(String token) throws UserException 
	{
		token=token.substring(7);

	    String username = jwtService.extractClaim(token, Claims::getSubject) ;
	    
	    Optional<UserReg> opt = userRepository.findByUsername(username) ;
	    
	    if(opt.isPresent()) 
	    {	    	
	    	return opt.get();	    	
	    }
		
	    throw new UserException("user not exist with email : "+username);  
		
	}
	
	

}

//s

