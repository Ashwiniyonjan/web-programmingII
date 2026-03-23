package com.example.controller;

import com.example.model.User;
import com.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/user")  // Base path for all user URLs
public class UserController {

    @Autowired
    private UserService userService;

    // Show Register Page

    @GetMapping("/register")
    public String showRegister() {
        return "userForm"; // JSP file for registration
    }

    // Handle Register Form Submission
    @PostMapping("/register")
    public String registerUser(
            @RequestParam("name") String name,
            @RequestParam("email") String email,
            @RequestParam("password") String password,
            @RequestParam("phone") String phone,
            @RequestParam("address") String address,
            @RequestParam("gender") String gender,
            @RequestParam(value = "agree", required = false) String agree,
            Model model) {

        if (agree == null) {
            model.addAttribute("error", "Please agree to terms and conditions!");
            return "userForm";
        }

        // Create user object
        User user = new User();
        user.setName(name);
        user.setEmail(email);
        user.setPassword(password);
        user.setPhone(phone);
        user.setAddress(address);
        user.setGender(gender);

        // Register user
        userService.registerUser(user);

        model.addAttribute("message", "Registration Successful! Please Login.");
        return "login"; 
    }

    // Show Login Page
    @GetMapping("/login")
    public String showLogin() {
        return "login"; // JSP file for login
    }

    // Handle Login Form Submission
    @PostMapping("/login")
    public String loginUser(
            @RequestParam("email") String email,
            @RequestParam("password") String password,
            Model model) {

        User user = userService.loginUser(email, password);

        if (user == null) {
            model.addAttribute("error", "Invalid Email or Password!");
            return "login"; // Stay on login page if failed
        }

        model.addAttribute("user", user);
        model.addAttribute("message", "Login Successful!");
        return "userSummary"; // Success page with user details
    }
}