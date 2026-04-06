package com.example.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.model.User;
import com.example.service.UserService;

import jakarta.validation.Valid;

/**
 * UserController — handles all HTTP requests related to user registration.
 *
 * Provides two kinds of endpoints (same pattern as EmployeeController):
 *
 * 1. JSP VIEW ENDPOINTS  — return web pages for the browser
 *      GET  /signup                → shows the registration form  (signup.jsp)
 *      POST /signup                → form submission → userRegistrationSummary.jsp
 *
 * 2. REST API ENDPOINTS   — return JSON for Postman / AJAX
 *      POST   /api/users           → register a new user
 *      GET    /api/users           → list all users
 *      GET    /api/users/{id}      → get user by id
 *      PUT    /api/users/{id}      → update user
 *      DELETE /api/users/{id}      → delete user
 */
@Controller
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    // ==================== JSP VIEW ENDPOINTS ====================

    /**
     * GET /signup — show the user registration form.
     */
    @GetMapping("/signup")
    public String showSignupForm() {
        return "signup";  // /WEB-INF/views/signup.jsp
    }

    /**
     * POST /signup — handle the traditional HTML form submission.
     * On success → shows userRegistrationSummary.jsp with the registered user.
     * On failure → shows signup.jsp with an error message.
     */
    @PostMapping("/signup")
    public String handleSignup(
            @RequestParam("firstName")    String firstName,
            @RequestParam("lastName")     String lastName,
            @RequestParam("email")        String email,
            @RequestParam("password")     String password,
            @RequestParam(value = "phone", required = false, defaultValue = "") String phone,
            Model model) {

        // Basic server-side presence checks
        if (firstName == null || firstName.isBlank() ||
            lastName  == null || lastName.isBlank()  ||
            email     == null || email.isBlank()     ||
            password  == null || password.isBlank()) {

            model.addAttribute("errorMessage", "Please fill in all required fields.");
            return "signup";
        }

        try {
            User user = userService.registerUser(firstName, lastName, email, password, phone);
            logger.info("User registered via form: id={}, email={}", user.getId(), email);
            model.addAttribute("user", user);
            return "redirect:/register";
        } catch (IllegalArgumentException e) {
            logger.warn("Duplicate email during form signup: {}", email);
            model.addAttribute("errorMessage", e.getMessage());
            return "signup";
        } catch (Exception e) {
            logger.error("Error registering user via form: {}", e.getMessage(), e);
            model.addAttribute("errorMessage", "Registration failed. Please try again.");
            return "signup";
        }
    }

    // ==================== REST API ENDPOINTS ====================

    /**
     * POST /api/users
     * Register a new user via JSON (for Postman or AJAX).
     *
     * Sample request body:
     * {
     *   "firstName": "Ashwini",
     *   "lastName":  "Yonjan",
     *   "email":     "ashwini@example.com",
     *   "password":  "ashh123",
     *   "phone":     "9800000000"
     * }
     */
    @PostMapping("/api/users")
    @ResponseBody
    public ResponseEntity<?> createUser(
            @Valid @RequestBody User user,
            BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            logger.error("Validation error while creating user: {}", bindingResult.getAllErrors());
            return buildErrorResponse(bindingResult);
        }

        try {
            User registered = userService.registerUser(
                user.getFirstName(), user.getLastName(),
                user.getEmail(), user.getPassword(), user.getPhone()
            );
            logger.info("User created via API: id={}, email={}", registered.getId(), registered.getEmail());

            Map<String, Object> response = new HashMap<>();
            response.put("status",  "success");
            response.put("message", "User registered successfully");
            response.put("user",    registered);
            return ResponseEntity.ok(response);

        } catch (IllegalArgumentException e) {
            Map<String, Object> response = new HashMap<>();
            response.put("status",  "error");
            response.put("message", e.getMessage());
            return ResponseEntity.status(409).body(response);  // 409 Conflict

        } catch (Exception e) {
            logger.error("Database error while creating user: {}", e.getMessage(), e);
            Map<String, Object> response = new HashMap<>();
            response.put("status",  "error");
            response.put("message", "Failed to register user. Database error.");
            return ResponseEntity.status(500).body(response);
        }
    }

    /**
     * GET /api/users
     * Retrieve all registered users.
     */
    @GetMapping("/api/users")
    @ResponseBody
    public ResponseEntity<?> getAllUsers() {
        try {
            List<User> users = userService.getAllUsers();
            Map<String, Object> response = new HashMap<>();
            response.put("status", "success");
            response.put("count",  users.size());
            response.put("users",  users);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            logger.error("Database error while fetching users: {}", e.getMessage(), e);
            Map<String, Object> response = new HashMap<>();
            response.put("status",  "error");
            response.put("message", "Failed to fetch users. Database error.");
            return ResponseEntity.status(500).body(response);
        }
    }
       /**
     * GET /api/users/{id}
     * Retrieve a single user by id.
     */
    @GetMapping("/api/users/{id}")
    @ResponseBody
    public ResponseEntity<?> getUserById(@PathVariable("id") Long id) {
        try {
            User user = userService.getUserById(id);
            if (user == null) {
                Map<String, Object> response = new HashMap<>();
                response.put("status",  "error");
                response.put("message", "User not found with id: " + id);
                return ResponseEntity.status(404).body(response);
            }
            Map<String, Object> response = new HashMap<>();
            response.put("status", "success");
            response.put("user",   user);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            logger.error("Database error while fetching user id {}: {}", id, e.getMessage(), e);
            Map<String, Object> response = new HashMap<>();
            response.put("status",  "error");
            response.put("message", "Failed to fetch user. Database error.");
            return ResponseEntity.status(500).body(response);
        }
    }

    /**
     * PUT /api/users/{id}
     * Update an existing user's information.
     */
    @PutMapping("/api/users/{id}")
    @ResponseBody
    public ResponseEntity<?> updateUser(
            @PathVariable("id") Long id,
            @Valid @RequestBody User user,
            BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return buildErrorResponse(bindingResult);
        }

        try {
            User updated = userService.updateUser(id, user);
            if (updated == null) {
                Map<String, Object> response = new HashMap<>();
                response.put("status",  "error");
                response.put("message", "User not found with id: " + id);
                return ResponseEntity.status(404).body(response);
            }
            Map<String, Object> response = new HashMap<>();
            response.put("status",  "success");
            response.put("message", "User updated successfully");
            response.put("user",    updated);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            logger.error("Database error while updating user id {}: {}", id, e.getMessage(), e);
            Map<String, Object> response = new HashMap<>();
            response.put("status",  "error");
            response.put("message", "Failed to update user. Database error.");
            return ResponseEntity.status(500).body(response);
        }
    }

    /**
     * DELETE /api/users/{id}
     * Delete a user by id.
     */
    @DeleteMapping("/api/users/{id}")
    @ResponseBody
    public ResponseEntity<?> deleteUser(@PathVariable("id") Long id) {
        try {
            boolean deleted = userService.deleteUser(id);
            if (!deleted) {
                Map<String, Object> response = new HashMap<>();
                response.put("status",  "error");
                response.put("message", "User not found with id: " + id);
                return ResponseEntity.status(404).body(response);
            }
            Map<String, Object> response = new HashMap<>();
            response.put("status",  "success");
            response.put("message", "User deleted successfully");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            logger.error("Database error while deleting user id {}: {}", id, e.getMessage(), e);
            Map<String, Object> response = new HashMap<>();
            response.put("status",  "error");
            response.put("message", "Failed to delete user. Database error.");
            return ResponseEntity.status(500).body(response);
        }
    }

    // ==================== HELPER ====================

    /** Collects Bean Validation errors and returns a structured 400 response. */
    private ResponseEntity<?> buildErrorResponse(BindingResult bindingResult) {
        Map<String, String> errors = new HashMap<>();
        for (FieldError error : bindingResult.getFieldErrors()) {
            errors.put(error.getField(), error.getDefaultMessage());
        }
        Map<String, Object> response = new HashMap<>();
        response.put("status", "error");
        response.put("errors", errors);
        return ResponseEntity.badRequest().body(response);
    }
}
