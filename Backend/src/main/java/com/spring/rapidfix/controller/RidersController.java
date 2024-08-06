package com.spring.rapidfix.controller;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import com.spring.rapidfix.entities.CurrentLocation;
import com.spring.rapidfix.entities.OrderFuelForm;

import com.spring.rapidfix.entities.RidersReg;
import com.spring.rapidfix.entities.UserReg;
import com.spring.rapidfix.exceptions.RidersException;
import com.spring.rapidfix.exceptions.UserException;
import com.spring.rapidfix.repository.CurrentLocationRepository;
import com.spring.rapidfix.repository.RidersRepository;
import com.spring.rapidfix.response.AuthenticationResponse;
import com.spring.rapidfix.service.CurrentLocationService;
import com.spring.rapidfix.service.JwtService;
import com.spring.rapidfix.service.RidersService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;

@Controller
@RequestMapping("/endpoint/riders")
public class RidersController {
	
	@Autowired
	private RidersService riderService; 
	
	@Autowired
	private CurrentLocationService currentlocationService ;
	
	@Autowired
	private CurrentLocationRepository currentriderlocationRepository;
	
	@Autowired
	private JwtService jwtService ;
	
	
	/*---- NOT USEFUL THINGS START ----*/
	@GetMapping("/workers")
	public String JointoCommunityPage(Model model) {
		model.addAttribute("rider", new RidersReg());
		return "RidersReg";
	}
	
	@GetMapping("/signin")
	public String getSigninPage(Model model) {
	    model.addAttribute("rider", new RidersReg());
	    return "RidersSignin";
	}
	
	
	/*---- NOT USEFUL THINGS END ----*/
	
	
	
	
	
	@PostMapping("/register")
	public ResponseEntity<AuthenticationResponse> register(@RequestBody RidersReg rider) throws RidersException
	{		
		return ResponseEntity.ok(riderService.registerUser(rider));
	}
	
	@PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(@RequestBody RidersReg rider)throws RidersException 
    {
        return ResponseEntity.ok(riderService.authenticate(rider));
    }
	
	
	@PostMapping("/signup")
	public String RegisterRider(@RequestBody String coderesult1, RidersReg rider, Model model) throws RidersException 
	{
		String otpFromUser = rider.getConfirmOtp();
		String GeneratedOtp = extractOTP(coderesult1);

		if (!riderService.ValidateOtp(GeneratedOtp, otpFromUser)) {
			model.addAttribute("errorMessage", "Invalid OTP");
			return "RidersReg";
		}

		riderService.registerUser(rider);
		return "UserMainPage";
	}

	private String extractOTP(String coderesult1) 
	{
		String[] params = coderesult1.split("&");

		for (String param : params) 
		{
			String[] keyValue = param.split("=");

			if (keyValue.length == 2 && keyValue[0].equals("confirmOtp")) 
			{
				return keyValue[1];
			}
		}
		
		return null;
	}
	
	
	
	
	
	
	@PutMapping("/updatelocation")
	 public ResponseEntity<CurrentLocation> updateLocation(@RequestBody CurrentLocation newLocation, @RequestHeader("Authorization") String token) throws UserException
	 {		 
		 CurrentLocation currentLocation = currentlocationService.updateLocation(newLocation , token) ;
		 
		 return new ResponseEntity<>(currentLocation, HttpStatus.CREATED); 
	 }
	
	
	@DeleteMapping("/logout")
	@Transactional
    public ResponseEntity<?> logout(@RequestHeader("Authorization") String token) {
        if (token.startsWith("Bearer ")) {
            token = token.substring(7);
        }

        String riderName = jwtService.extractUsername(token);
        
        if (riderName != null) {
           
            currentriderlocationRepository.deleteByRiderName(riderName);

           
            SecurityContextHolder.clearContext();

            return ResponseEntity.ok("User logged out and entry deleted");
        }

        return ResponseEntity.badRequest().body("Invalid token");
    }

	
	
	
	
	
	
	

	
    
    
    
    
    
    
    
    

	/*
	 * @PostMapping("/signup") public String RegisterRider(RidersReg rider,
	 * HttpServletRequest request, Model model) { /*
	 * model.addAttribute("registerrider", new RidersReg());
	 * 
	 * String otpFromUser = rider.getConfirmOtp();
	 * 
	 * try { String coderesult = (String)
	 * request.getSession().getAttribute("coderesult1");
	 * 
	 * if (!riderService.ValidateOtp(coderesult, otpFromUser)) {
	 * model.addAttribute("errorMessage", "Invalid OTP"); return "RidersReg"; }
	 * 
	 * // OTP is valid, proceed with registration riderService.registerUser(rider);
	 * return "UserMainPage"; } catch (RidersException e) {
	 * model.addAttribute("errorMessage", e.getMessage()); return "RidersReg"; } }
	 * 
	 * 
	 * 
	 * 
	 * 
	 * }
	 */


	 
	/* 
	 private String geocodeLocation(String location) 
	 {
	   RestTemplate restTemplate = new RestTemplate();
	   String apiUrl = osmApiUrl + "?q=" + location + "&format=json";

	   ResponseEntity<String> response = restTemplate.getForEntity(apiUrl, String.class);
	   	if (response.getStatusCode().is2xxSuccessful()) {
	            return "latitude,longitude"; // Replace with actual parsing logic
	        } else {
	            return null;
	        }
	    } 
	 */
	 
	 
	
	
	
}

//s
