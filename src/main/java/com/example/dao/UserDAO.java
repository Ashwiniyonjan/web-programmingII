package com.example.dao;

import com.example.dto.UserDTO;
import java.util.List;

/**
 * UserDAO interface — defines what database operations are available for users.
 *
 * The actual SQL lives in UserDAOImpl.
 * The Service layer depends on this interface so we can swap implementations
 * (e.g., switch from H2 to MySQL) without touching any other code.
 */
public interface UserDAO {

    /** INSERT a new user and return it with the generated id. */
    UserDTO save(UserDTO user);

    /** SELECT all users. */
    List<UserDTO> findAll();

    /** SELECT one user by id (returns null if not found). */
    UserDTO findById(Long id);

    /** SELECT one user by email (returns null if not found). */
    UserDTO findByEmail(String email);

    /** UPDATE a user's information (returns null if not found). */
    UserDTO update(Long id, UserDTO user);

    /** DELETE a user by id (true = deleted, false = not found). */
    boolean delete(Long id);
}
