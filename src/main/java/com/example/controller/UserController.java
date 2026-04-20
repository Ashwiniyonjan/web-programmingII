package com.example.controller;

import com.example.dto.UserDTO;
import com.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    // =========================
    // WEB: HOME
    // =========================
    @GetMapping("/")
    public String home() {
        return "redirect:/signup";
    }

    // =========================
    // WEB: SIGNUP PAGE
    // =========================
    @GetMapping("/signup")
    public String showSignupForm() {
        return "signup";
    }

    // =========================
    // WEB: SIGNUP SUBMIT
    // =========================
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

        userService.registerUser(firstName, lastName, email, password, phone);
        return "redirect:/login";
    }

    // ======================================================
    // ================= POSTMAN APIs =======================
    // ======================================================

    // GET ALL USERS (POSTMAN)
    @GetMapping("/api/users")
    @ResponseBody
    public List<UserDTO> getAllUsers() {
        return userService.getAllUsers();
    }

    // SIGNUP USER (POSTMAN)
    @PostMapping("/api/users/signup")
    @ResponseBody
    public String signupAPI(@RequestBody UserDTO user) {

        userService.registerUser(
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getPassword(),
                user.getPhone()
        );

        return "User registered successfully";
    }
}