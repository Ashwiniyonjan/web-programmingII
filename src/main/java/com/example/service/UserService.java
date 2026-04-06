package com.example.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.example.dao.UserDAO;
import com.example.dto.UserDTO;
import com.example.model.User;

/**
 * UserService — business logic layer for user registration.
 *
 * Responsibilities:
 *  1. Validate business rules (e.g., duplicate email check)
 *  2. Convert between User (Controller model) and UserDTO (DAO/database model)
 *  3. Delegate CRUD operations to UserDAO
 */
@Service
public class UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    private final UserDAO userDAO;

    public UserService(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    /**
     * Register a new user.
     * Throws IllegalArgumentException if the email is already in use.
     */
    public User registerUser(String firstName, String lastName, String email, String password, String phone) {
        logger.info("Registering new user: firstName={}, email={}", firstName, email);

        // Business rule: email must be unique
        if (userDAO.findByEmail(email) != null) {
            throw new IllegalArgumentException("An account with this email already exists.");
        }

        UserDTO dto = new UserDTO(firstName, lastName, email, password, phone != null ? phone : "");
        UserDTO saved = userDAO.save(dto);

        logger.info("User registered successfully with id: {}", saved.getId());
        return toUser(saved);
    }

    /** Retrieve all registered users. */
    public List<User> getAllUsers() {
        List<UserDTO> dtos = userDAO.findAll();
        logger.info("Retrieved {} users from database", dtos.size());
        List<User> users = new ArrayList<>();
        for (UserDTO dto : dtos) {
            users.add(toUser(dto));
        }
        return users;
    }

    /** Find a single user by id. Returns null if not found. */
    public User getUserById(Long id) {
        UserDTO dto = userDAO.findById(id);
        if (dto == null) {
            logger.info("User not found with id: {}", id);
            return null;
        }
        return toUser(dto);
    }

    /** Find a single user by email. Returns null if not found. */
    public User getUserByEmail(String email) {
        UserDTO dto = userDAO.findByEmail(email);
        if (dto == null) {
            logger.info("User not found with email: {}", email);
            return null;
        }
        return toUser(dto);
    }

    /** Update an existing user. Returns null if not found. */
    public User updateUser(Long id, User updated) {
        UserDTO dto = new UserDTO(
            updated.getFirstName(), updated.getLastName(),
            updated.getEmail(), updated.getPassword(),
            updated.getPhone() != null ? updated.getPhone() : ""
        );
        UserDTO result = userDAO.update(id, dto);
        if (result == null) {
            logger.info("Cannot update - user not found with id: {}", id);
            return null;
        }
        return toUser(result);
    }

    /** Delete a user by id. Returns true if deleted, false if not found. */
    public boolean deleteUser(Long id) {
        boolean deleted = userDAO.delete(id);
        if (!deleted) logger.info("Cannot delete - user not found with id: {}", id);
        return deleted;
    }

    /** Convert UserDTO (database) → User (controller model). */
    private User toUser(UserDTO dto) {
        User u = new User(dto.getFirstName(), dto.getLastName(), dto.getEmail(), dto.getPassword(), dto.getPhone());
        u.setId(dto.getId());
        return u;
    }
}