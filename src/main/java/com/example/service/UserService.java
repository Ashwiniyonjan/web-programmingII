package com.example.service;

import com.example.model.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

    private List<User> users = new ArrayList<>();

    // Register user with full User object
    public void registerUser(User user) {
        users.add(user);  // just add the User object directly
    }

    // Login check
    public User loginUser(String email, String password) {
        for (User u : users) {
            if (u.getEmail().equals(email) && u.getPassword().equals(password)) {
                return u;
            }
        }
        return null;
    }
}