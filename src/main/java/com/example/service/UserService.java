package com.example.service;

import com.example.dao.UserDAO;
import com.example.dto.UserDTO;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.HexFormat;

@Service
public class UserService {

    private final UserDAO userDAO;

    public UserService(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    public boolean registerUser(String firstName, String lastName, String email, String password, String phone) {

        String username = firstName + "_" + lastName;

        System.out.println("Registering user: " + username);

        if (userDAO.findByUsername(username) != null) {
            return false;
        }

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

    public UserDTO getUserByEmail(String email) {
        return userDAO.findByEmail(email);
    }

    public boolean authenticate(String username, String password) {

        UserDTO user = userDAO.findByUsername(username);

        if (user == null) return false;

        return user.getPassword().equals(hashPassword(password));
    }

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