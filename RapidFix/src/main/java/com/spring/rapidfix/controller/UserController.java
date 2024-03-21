package com.spring.rapidfix.controller;



import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.spring.rapidfix.entities.RidersReg;
import com.spring.rapidfix.entities.UserReg;
import com.spring.rapidfix.exceptions.UserException;
import com.spring.rapidfix.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;


@Controller
@RequestMapping("/endpoint/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/homepage")
    public String showSignUpForm(Model model) {
        model.addAttribute("user", new UserReg());
        return "UserReg";
    }

    @PostMapping("/signup")
    public String registerUser(UserReg user, Model model) {
        try {
            userService.registerUser(user);
            return "redirect:/endpoint/users/loginpage";
        } catch (UserException e) {
            model.addAttribute("error", e.getMessage());
            return "UserReg";
        }
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
}

// sahil bhukal
