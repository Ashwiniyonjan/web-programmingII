package com.example.dao;

import com.example.dto.UserDTO;
import java.util.List;

public interface UserDAO {

    UserDTO save(UserDTO user);

    UserDTO findByUsername(String username);

    UserDTO findByEmail(String email);

    // ✅ REQUIRED FOR POSTMAN
    List<UserDTO> getAllUsers();
}