package com.spring.rapidfix.controller;



import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import com.spring.rapidfix.entities.CurrentLocation;

import com.spring.rapidfix.entities.OrderFuelForm;
import com.spring.rapidfix.entities.RidersReg;
import com.spring.rapidfix.entities.UserReg;
import com.spring.rapidfix.exceptions.UserException;
import com.spring.rapidfix.repository.CurrentLocationRepository;
import com.spring.rapidfix.repository.UserRepository;
import com.spring.rapidfix.response.AuthenticationResponse;

import com.spring.rapidfix.service.RidersService;
import com.spring.rapidfix.service.UserService;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


@Controller
@RequestMapping("/endpoint/users")
public class UserController {

    @Autowired
    private UserService userService;
    
    @Autowired
    private UserRepository userRepository ;
    
	@Value("${osm.api.url}")
    private String osmApiUrl;

	@Autowired
	private RidersService riderService;
	
	
	
	 
	

	
	@Autowired
	private CurrentLocationRepository currentlocationRepository ;

    /*---- NOT USEFUL ENPOINTS START ----*/
    @GetMapping("/homepage")
    public String showSignUpForm(Model model) {
        model.addAttribute("user", new UserReg());
        return "UserReg";
    }
    
    @GetMapping("/loginpage")
    public String showLoginPage() {
        return "UserLogin";
    }
    
    @GetMapping("/rapidfixcommunity")
    public String showMainPage(Model model) {
    	model.addAttribute("worker", new RidersReg()) ;
        return "RapidFixCommunity";
    }
    /*---- NOT USEFUL ENPOINTS END ----*/
    
    
    
    

    @PostMapping("/signup")
    public ResponseEntity<AuthenticationResponse> registerUser(@RequestBody UserReg user) throws UserException 
    { 	
    	 return ResponseEntity.ok(userService.registerUser(user));
    }
    
    @PostMapping("/signin")
    public ResponseEntity<AuthenticationResponse> loginUser(@RequestBody UserReg user) 
    {
        return ResponseEntity.ok(userService.authenticate(user));
    }
    
    @PostMapping("/orderFuel")
    public ResponseEntity<String> orderFuel(@RequestBody OrderFuelForm orderfueldetails , @RequestHeader("Authorization") String token) throws UserException 
    {       
    	UserReg user = userService.findUserProfile(token) ;
    	
    	OrderFuelForm newFuelOrder = userService.orderFuel(orderfueldetails , user.getId());
    	String userLocation = orderfueldetails.getManualAddress() ;
    	
    	String userCoordinates = geocodeLocation(userLocation);

        if (userCoordinates == null) 
        {
            return ResponseEntity.badRequest().body("Failed to geocode user location");
        }

        List<CurrentLocation> nearbyRiders = queryNearbyRiders(userCoordinates);

        return ResponseEntity.ok(nearbyRiders.toString());
    	       
    }
    
    
    
    
    
    /*---- I SUCCESSFULLY INTEGRATED THIS IN ORDERFUEL ----*/
    @GetMapping("/nearby-riders")
    public ResponseEntity<String> findNearbyRiders(@RequestParam String userLocation) 
	{
        String userCoordinates = geocodeLocation(userLocation);

        if (userCoordinates == null) 
        {
            return ResponseEntity.badRequest().body("Failed to geocode user location");
        }

        List<CurrentLocation> nearbyRiders = queryNearbyRiders(userCoordinates);

        return ResponseEntity.ok(nearbyRiders.toString());
    }
	
	
	/*---- LOGIC FOR FINDING NEARBY RIDERS START ----*/
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




    private List<CurrentLocation> queryNearbyRiders(String userCoordinates) 
    {
        // For demonstration purposes, assume we have a list of riders with their coordinates
        List<CurrentLocation> riders =  currentlocationRepository.findAll() ;

        // For simplicity, assume userCoordinates is "latitude,longitude"
        String[] userCoords = userCoordinates.split(",");
        double userLat = Double.parseDouble(userCoords[0]);
        double userLon = Double.parseDouble(userCoords[1]);

        // Define the maximum distance (in kilometers) within which riders are considered nearby
        double maxDistance = 10.0;

        List<CurrentLocation> nearbyRiders = new ArrayList<>();
        for (CurrentLocation rider : riders) {
            double riderLat = rider.getLatitude();
            double riderLon = rider.getLongitude();

            // Calculate distance using Haversine formula
            double distance = haversine(userLat, userLon, riderLat, riderLon);

            // If the distance is within the maximum distance, consider the rider as nearby
            if (distance <= maxDistance) {
                nearbyRiders.add(rider);
            }
        }

        return nearbyRiders;
    }

    private double haversine(double userLat, double userLon, double riderLat, double riderLon) 
    {
        // Radius of the Earth in kilometers
        final double R = 6371.0;

        // Convert latitude and longitude from degrees to radians
        double lat1 = Math.toRadians(userLat);
        double lon1 = Math.toRadians(userLon);
        double lat2 = Math.toRadians(riderLat);
        double lon2 = Math.toRadians(riderLon);

        // Haversine formula
        double dlon = lon2 - lon1;
        double dlat = lat2 - lat1;
        double a = Math.pow(Math.sin(dlat / 2), 2) + Math.cos(lat1) * Math.cos(lat2) * Math.pow(Math.sin(dlon / 2), 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double distance = R * c;

        return distance;
    }
    /*---- LOGIC FOR FINDING NEARBY RIDERS END ----*/
    
    
    @GetMapping("/search")
    public List<UserReg> searchUsers(@RequestParam String username) {
        return userRepository.findByUsernameContaining(username);
    }
    

}






// THIS COMMENT WAS IN /SIGNUP API I CUT FROM THERE AN PASTE IT HERE FOR BETTER VIEW
//ye comment wala agar UI dikhana chahta ho to use kar sakta hai aur return type mein String karlio responseentity ki jgh
/* try {
    userService.registerUser(user);
    return "redirect:/endpoint/users/loginpage";
} catch (UserException e) {
    model.addAttribute("error", e.getMessage());
    return "UserReg";
}*/

//s
