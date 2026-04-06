package com.example.dao;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.example.dto.UserDTO;

/**
 * UserDAOImpl — handles all SQL operations for the "users" table using H2 (same DB as employees).
 *
 * Table columns:
 *   id BIGINT AUTO_INCREMENT PRIMARY KEY
 *   first_name VARCHAR(255) NOT NULL
 *   last_name  VARCHAR(255) NOT NULL
 *   email      VARCHAR(255) NOT NULL UNIQUE
 *   password   VARCHAR(255) NOT NULL
 *   phone      VARCHAR(10)
 */
@Repository
public class UserDAOImpl implements UserDAO {

    private static final Logger logger = LoggerFactory.getLogger(UserDAOImpl.class);

    private final JdbcTemplate jdbcTemplate;

    /** Converts a database row to a UserDTO object. */
    private final RowMapper<UserDTO> rowMapper = (rs, rowNum) -> new UserDTO(
            rs.getLong("id"),
            rs.getString("first_name"),
            rs.getString("last_name"),
            rs.getString("email"),
            rs.getString("password"),
            rs.getString("phone")
    );

    public UserDAOImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        initTable();
    }

    /** Creates the "users" table if it doesn't exist yet. */
    private void initTable() {
        try {
            jdbcTemplate.execute(
                "CREATE TABLE IF NOT EXISTS users (" +
                "id         BIGINT AUTO_INCREMENT PRIMARY KEY, " +
                "first_name VARCHAR(255) NOT NULL, " +
                "last_name  VARCHAR(255) NOT NULL, " +
                "email      VARCHAR(255) NOT NULL UNIQUE, " +
                "password   VARCHAR(255) NOT NULL, " +
                "phone      VARCHAR(10)" +
                ")"
            );
            logger.info("User table initialized successfully");
        } catch (Exception e) {
            logger.error("Failed to initialize user table: {}", e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public UserDTO save(UserDTO user) {
        String sql = "INSERT INTO users (first_name, last_name, email, password, phone) VALUES (?, ?, ?, ?, ?)";
        try {
            KeyHolder keyHolder = new GeneratedKeyHolder();
            jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, user.getFirstName());
                ps.setString(2, user.getLastName());
                ps.setString(3, user.getEmail());
                ps.setString(4, user.getPassword());
                ps.setString(5, user.getPhone() != null ? user.getPhone() : "");
                return ps;
            }, keyHolder);

            Long generatedId = keyHolder.getKey().longValue();
            user.setId(generatedId);
            logger.info("User inserted successfully with id: {}", generatedId);
            return user;
        } catch (Exception e) {
            logger.error("Database error while saving user: {}", e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public List<UserDTO> findAll() {
        try {
            return jdbcTemplate.query("SELECT * FROM users", rowMapper);
        } catch (Exception e) {
            logger.error("Database error while fetching all users: {}", e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public UserDTO findById(Long id) {
        try {
            List<UserDTO> results = jdbcTemplate.query("SELECT * FROM users WHERE id = ?", rowMapper, id);
            return results.isEmpty() ? null : results.get(0);
        } catch (Exception e) {
            logger.error("Database error while fetching user with id {}: {}", id, e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public UserDTO findByEmail(String email) {
        try {
            List<UserDTO> results = jdbcTemplate.query("SELECT * FROM users WHERE email = ?", rowMapper, email);
            return results.isEmpty() ? null : results.get(0);
        } catch (Exception e) {
            logger.error("Database error while fetching user with email {}: {}", email, e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public UserDTO update(Long id, UserDTO user) {
        try {
            int rows = jdbcTemplate.update(
                "UPDATE users SET first_name = ?, last_name = ?, email = ?, password = ?, phone = ? WHERE id = ?",
                user.getFirstName(), user.getLastName(), user.getEmail(),
                user.getPassword(), user.getPhone() != null ? user.getPhone() : "", id
            );
            if (rows == 0) return null;
            user.setId(id);
            logger.info("User updated successfully with id: {}", id);
            return user;
        } catch (Exception e) {
            logger.error("Database error while updating user with id {}: {}", id, e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public boolean delete(Long id) {
        try {
            int rows = jdbcTemplate.update("DELETE FROM users WHERE id = ?", id);
            if (rows > 0) {
                logger.info("User deleted successfully with id: {}", id);
                return true;
            }
            return false;
        } catch (Exception e) {
            logger.error("Database error while deleting user with id {}: {}", id, e.getMessage(), e);
            throw e;
        }
    }
}
