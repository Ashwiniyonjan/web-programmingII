package com.example.dao;

import com.example.dto.UserDTO;

/**
 * Interface defining database operations for user accounts.
 * See AUTHENTICATION_DOCUMENTATION.md for full details.
 */
public interface UserDAO {

    UserDTO save(UserDTO user);

    UserDTO findByUsername(String username);

    UserDTO findByEmail(String email);
}
