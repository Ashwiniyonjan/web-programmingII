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

    // SHOW LOGIN PAGE
    @GetMapping
    public String showForm() {
        return "Login";
    }

    // HANDLE LOGIN
    @PostMapping
    public String handleLogin(@RequestParam("email") String email,
                              @RequestParam("password") String password,
                              HttpSession session,
                              Model model) {

        UserDTO user = userService.getUserByEmail(email);

        // ❌ INVALID LOGIN
        if (user == null ||
            !user.getPassword().equals(userService.hashPassword(password))) {

            model.addAttribute("errorMessage", "Invalid email or password.");
            return "Login";
        }

        // ✅ STORE USER IN SESSION (VERY IMPORTANT)
        session.setAttribute("loggedInUser", user);

        // ✅ GO TO EMPLOYEE PAGE
        return "redirect:/employee";
    }
}