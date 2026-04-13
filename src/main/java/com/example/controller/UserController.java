package com.example.controller;

import com.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    // HOME → SIGNUP PAGE
    @GetMapping("/")
    public String home() {
        return "redirect:/signup";
    }

    // SHOW SIGNUP PAGE
    @GetMapping("/signup")
    public String showSignupForm() {
        return "signup";
    }

    // HANDLE SIGNUP FORM
    @PostMapping("/signup")
    public String handleSignup(
            @RequestParam("firstName") String firstName,
            @RequestParam("lastName") String lastName,
            @RequestParam("email") String email,
            @RequestParam("password") String password,
            @RequestParam(value = "phone", required = false) String phone,
            Model model) {

        if (firstName.isBlank() || lastName.isBlank() ||
            email.isBlank() || password.isBlank()) {

            model.addAttribute("errorMessage", "Please fill all fields.");
            return "signup";
        }

        try {
            userService.registerUser(firstName, lastName, email, password, phone);

            // ✅ FIXED FLOW → LOGIN FIRST
            return "redirect:/login";

        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("errorMessage", e.getMessage());
            return "signup";
        }
    }
}
