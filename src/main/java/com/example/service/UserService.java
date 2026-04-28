package com.example.service;

import com.example.dao.UserDAO;
import com.example.dto.UserDTO;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.HexFormat;
import java.util.List;

@Service
public class UserService {

    private final UserDAO userDAO;

    public UserService(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    // =========================
    // REGISTER USER (UNCHANGED)
    // =========================
    
public boolean registerUser(String firstName, String lastName, String email, String password, String phone) {

    // 🔥 CHECK BY EMAIL (NOT username)
    if (userDAO.findByEmail(email) != null) {
        return false;
    }

    String username = firstName + "_" + lastName;

    String hashedPassword = hashPassword(password);

    UserDTO user = new UserDTO(
            username,
            hashedPassword,
            email,
            firstName,
            lastName,
            phone
    );

    return userDAO.save(user) != null;
}
    // =========================
    // GET USER
    // =========================
    public UserDTO getUserByEmail(String email) {
        return userDAO.findByEmail(email);
    }

    public List<UserDTO> getAllUsers() {
        return userDAO.getAllUsers();
    }

    // =========================
    // AUTHENTICATION ( FIXED )
    // =========================
    public boolean authenticate(String usernameOrEmail, String password) {
        // Try finding by email first
        UserDTO user = userDAO.findByEmail(usernameOrEmail);
        
        // If not found, try finding by username
        if (user == null) {
            user = userDAO.findByUsername(usernameOrEmail);
        }

        // If user exists, hash the provided password and compare
        if (user != null) {
            String hashedInput = hashPassword(password);
            return user.getPassword().equals(hashedInput);
        }
        return false;
    }

    // =========================
    // HASH FUNCTION (KEEP AS IS)
    // =========================
    public String hashPassword(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(password.getBytes(StandardCharsets.UTF_8));
            return HexFormat.of().formatHex(hash);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}