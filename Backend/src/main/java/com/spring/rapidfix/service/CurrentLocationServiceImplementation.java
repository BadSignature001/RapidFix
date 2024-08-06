package com.spring.rapidfix.service;

import java.util.Optional;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.spring.rapidfix.entities.CurrentLocation;
import com.spring.rapidfix.entities.RidersReg;
import com.spring.rapidfix.entities.UserReg;
import com.spring.rapidfix.exceptions.UserException;
import com.spring.rapidfix.repository.CurrentLocationRepository;
import com.spring.rapidfix.repository.OrderFuelRepository;
import com.spring.rapidfix.repository.RidersRepository;
import com.spring.rapidfix.repository.UserRepository;
import com.spring.rapidfix.repository.UserTokenRepository;

import io.jsonwebtoken.Claims;

@Service
public class CurrentLocationServiceImplementation implements CurrentLocationService {
	
	@Value("${osm.api.url}")
    private String osmApiUrl;

	private final RidersRepository ridersRepository;
	private final CurrentLocationRepository currentlocationRepository;
	private final JwtService jwtService;

	public CurrentLocationServiceImplementation(
												JwtService jwtService,
												RidersRepository ridersRepository,
												CurrentLocationRepository currentlocationRepository) 
	{
		this.jwtService = jwtService;
		this.ridersRepository = ridersRepository;
		this.currentlocationRepository = currentlocationRepository;
	}
	
	

	@Override
	public CurrentLocation updateLocation(CurrentLocation currentLocation, String token) throws UserException {
		String riderName = riderId(token);
		Optional<CurrentLocation> existingLocationOpt = currentlocationRepository.findByRiderName(riderName);
		
		String riderCoordinates = geocodeLocation(currentLocation.getUpdatedLocation()) ;
		String[] userCoords = riderCoordinates.split(",");
	    double riderLat = Double.parseDouble(userCoords[0]);
	    double riderLon = Double.parseDouble(userCoords[1]);

		if (existingLocationOpt.isPresent()) {
			// Update existing location
			CurrentLocation existingLocation = existingLocationOpt.get();
			existingLocation.setLtrCost(currentLocation.getLtrCost());
			existingLocation.setPetrolpumpName(currentLocation.getPetrolpumpName());
			existingLocation.setUpdatedLocation(currentLocation.getUpdatedLocation());
			existingLocation.setLatitude(riderLat);
			existingLocation.setLongitude(riderLon);
			return currentlocationRepository.save(existingLocation);
		} else {
			// Create new location
			currentLocation.setRiderName(riderName);
			currentLocation.setLatitude(riderLat);
			currentLocation.setLongitude(riderLon);
			return currentlocationRepository.save(currentLocation);
		}

	}

	@Override
	public String riderId(String token) throws UserException {

		token = token.substring(7);
		String username = jwtService.extractClaim(token, Claims::getSubject);
		Optional<RidersReg> opt = ridersRepository.findByUsername(username);

		if (opt.isPresent()) {
			return username;
		}
		throw new UserException("rider not exist with email : " + username);
	}
	
	
	
	private String geocodeLocation(String location) 
	{
	    RestTemplate restTemplate = new RestTemplate();
	    String apiUrl = osmApiUrl + "?q=" + location + "&format=json";

	    ResponseEntity<String> response = restTemplate.getForEntity(apiUrl, String.class);
	    if (response.getStatusCode().is2xxSuccessful()) {
	        String responseBody = response.getBody();
	        try {
	            JSONArray jsonArray = new JSONArray(responseBody);
	            JSONObject firstResult = jsonArray.getJSONObject(0);

	            double latitude = firstResult.getDouble("lat");
	            double longitude = firstResult.getDouble("lon");

	            return latitude + "," + longitude;
	        } catch (JSONException e) {
	            e.printStackTrace(); // Print the JSON parsing error for debugging
	            return null;
	        }
	    } else {
	        System.out.println("Error response from API: " + response.getBody());
	        return null;
	    }
	}

}

//s
