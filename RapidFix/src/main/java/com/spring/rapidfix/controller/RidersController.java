package com.spring.rapidfix.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.spring.rapidfix.entities.RidersReg;
import com.spring.rapidfix.exceptions.RidersException;
import com.spring.rapidfix.exceptions.UserException;
import com.spring.rapidfix.service.RidersService;

import jakarta.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/endpoint/riders")
public class RidersController {

    @Autowired
    private RidersService riderService;

    @GetMapping("/workers")
    public String JointoCommunityPage(Model model) {
        model.addAttribute("rider", new RidersReg());
        return "RidersReg";
    }

  /*  @PostMapping("/signup")
    public String RegisterRider(RidersReg rider, HttpServletRequest request, Model model) {
      /*  model.addAttribute("registerrider", new RidersReg());

        String otpFromUser = rider.getConfirmOtp();

        try {
            String coderesult = (String) request.getSession().getAttribute("coderesult1");

            if (!riderService.ValidateOtp(coderesult, otpFromUser)) {
                model.addAttribute("errorMessage", "Invalid OTP");
                return "RidersReg";
            }

            // OTP is valid, proceed with registration
            riderService.registerUser(rider);
            return "UserMainPage";
        } catch (RidersException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "RidersReg";
        }
    }
    	
    	
    	
    	
    	
} */
    
    
    
    
    
    
    @PostMapping("/signup")
    public String RegisterRider(@RequestBody String coderesult1 , RidersReg rider , Model model) throws RidersException{
       
    	String otpFromUser = rider.getConfirmOtp() ;
    	String GeneratedOtp = extractOTP(coderesult1);
    	
    	if(!riderService.ValidateOtp(GeneratedOtp, otpFromUser))
    	{
    		model.addAttribute("errorMessage" , "Invalid OTP") ;
    		return "RidersReg" ;
    	}
    	
    	riderService.registerUser(rider) ;
    	return "UserMainPage" ;
    	
    }
    
    
    
    
    
    
    
    
    
    
    private String extractOTP(String coderesult1) {
        
        String[] params = coderesult1.split("&");
        
        for (String param : params) {
           
            String[] keyValue = param.split("=");

            
            if (keyValue.length == 2 && keyValue[0].equals("confirmOtp")) {
                return keyValue[1];
            }
        }        
        return null;
}
}
