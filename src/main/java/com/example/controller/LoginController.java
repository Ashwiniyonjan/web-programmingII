package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.model.User;
import com.example.service.UserService;

@Controller
@RequestMapping("/login")
public class LoginController {

    @Autowired
    private UserService userService;

    @GetMapping
    public String showForm() {
        return "Login";  // /WEB-INF/views/Login.jsp
    }

    @PostMapping
    public String handleLogin(@RequestParam("email")    String email,
                              @RequestParam("password") String password,
                              Model model) {

        User user = userService.getUserByEmail(email);

        if (user == null || !user.getPassword().equals(password)) {
            model.addAttribute("errorMessage", "Invalid email or password.");
            return "Login";
        }

        model.addAttribute("user", user);
        return "UserSummary";
    }
}