package com.example.controller;

import com.example.dto.UserDTO;
import com.example.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/login")
public class LoginController {

    @Autowired
    private UserService userService;

    // =========================
    // WEB LOGIN PAGE
    // =========================
    @GetMapping
    public String showForm() {
        return "Login";
    }

    // =========================
    // WEB LOGIN
    // =========================
    @PostMapping
    public String handleLogin(@RequestParam("email") String email,
                              @RequestParam("password") String password,
                              HttpSession session,
                              Model model) {

        UserDTO user = userService.getUserByEmail(email);

        if (user == null ||
            !user.getPassword().equals(userService.hashPassword(password))) {

            model.addAttribute("errorMessage", "Invalid email or password.");
            return "Login";
        }

        session.setAttribute("loggedInUser", user);
        return "redirect:/employee";
    }

    // =========================
    // WEB LOGOUT
    // =========================
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }

    // ======================================================
    // ================= POSTMAN API ========================
    // ======================================================

    @PostMapping("/api")
    @ResponseBody
    public String loginAPI(@RequestBody UserDTO user) {

        UserDTO dbUser = userService.getUserByEmail(user.getEmail());

        if (dbUser == null) {
            return "User not found";
        }

        if (!dbUser.getPassword().equals(userService.hashPassword(user.getPassword()))) {
            return "Invalid password";
        }

        return "Login successful";
    }
}