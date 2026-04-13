package com.example.dao;

import com.example.dto.UserDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

@Repository
public class UserDAOImpl implements UserDAO {

    private static final Logger logger = LoggerFactory.getLogger(UserDAOImpl.class);

    private final JdbcTemplate jdbcTemplate;

    public UserDAOImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        System.out.println(" INIT TABLE CALLED");
        initTable();
    }

    // ==========================
    // ROW MAPPER (FIXED)
    // ==========================
    private final RowMapper<UserDTO> rowMapper = (rs, rowNum) -> new UserDTO(
            rs.getLong("id"),
            rs.getString("username"),
            rs.getString("password"),
            rs.getString("email"),
            rs.getString("firstName"),
            rs.getString("lastName"),
            rs.getString("phone")
    );

    // ==========================
    // TABLE INIT
    // ==========================
    private void initTable() {
        jdbcTemplate.execute(
                "CREATE TABLE IF NOT EXISTS app_users (" +
                        "id BIGINT AUTO_INCREMENT PRIMARY KEY, " +
                        "username VARCHAR(255) NOT NULL UNIQUE, " +
                        "password VARCHAR(255) NOT NULL, " +
                        "email VARCHAR(255) NOT NULL, " +
                        "firstName VARCHAR(255), " +
                        "lastName VARCHAR(255), " +
                        "phone VARCHAR(20)" +
                        ")"
        );
    }

    // ==========================
    // SAVE USER
    // ==========================
    @Override
    public UserDTO save(UserDTO user) {
        String sql = "INSERT INTO app_users (username, password, email, firstName, lastName, phone) VALUES (?, ?, ?, ?, ?, ?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, user.getUsername());
            ps.setString(2, user.getPassword());
            ps.setString(3, user.getEmail());
            ps.setString(4, user.getFirstName());
            ps.setString(5, user.getLastName());
            ps.setString(6, user.getPhone());
            return ps;
        }, keyHolder);

        user.setId(keyHolder.getKey().longValue());
        return user;
    }

    // ==========================
    // FIND BY USERNAME
    // ==========================
    @Override
    public UserDTO findByUsername(String username) {
        List<UserDTO> list = jdbcTemplate.query(
                "SELECT * FROM app_users WHERE username = ?",
                rowMapper,
                username
        );

        return list.isEmpty() ? null : list.get(0);
    }

    // ==========================
    // FIND BY EMAIL
    // ==========================
    @Override
    public UserDTO findByEmail(String email) {
        List<UserDTO> list = jdbcTemplate.query(
                "SELECT * FROM app_users WHERE email = ?",
                rowMapper,
                email
        );

        return list.isEmpty() ? null : list.get(0);
    }
}