package com.example.controller;

import com.example.dto.UserDTO;
import com.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map; 
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
public Map<String, String> signupAPI(@RequestBody UserDTO user) {

    boolean success = userService.registerUser(
            user.getFirstName(),
            user.getLastName(),
            user.getEmail(),
            user.getPassword(),
            user.getPhone()
    );

    if (!success) {
        return Map.of(
                "status", "error",
                "message", "User already exists"
        );
    }

    return Map.of(
            "status", "success",
            "message", "User registered successfully"
    );
}
}