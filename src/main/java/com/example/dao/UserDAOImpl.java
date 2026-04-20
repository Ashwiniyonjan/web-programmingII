package com.example.dao;

import com.example.dto.UserDTO;
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

    private final JdbcTemplate jdbcTemplate;

    public UserDAOImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        initTable();
    }

    private final RowMapper<UserDTO> rowMapper = (rs, rowNum) -> new UserDTO(
            rs.getLong("id"),
            rs.getString("username"),
            rs.getString("password"),
            rs.getString("email"),
            rs.getString("firstName"),
            rs.getString("lastName"),
            rs.getString("phone")
    );

    private void initTable() {
        jdbcTemplate.execute(
                "CREATE TABLE IF NOT EXISTS app_users (" +
                        "id BIGINT AUTO_INCREMENT PRIMARY KEY, " +
                        "username VARCHAR(255), " +
                        "password VARCHAR(255), " +
                        "email VARCHAR(255), " +
                        "firstName VARCHAR(255), " +
                        "lastName VARCHAR(255), " +
                        "phone VARCHAR(20)" +
                        ")"
        );
    }

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

    @Override
    public UserDTO findByUsername(String username) {
        List<UserDTO> list = jdbcTemplate.query(
                "SELECT * FROM app_users WHERE username=?",
                rowMapper,
                username
        );
        return list.isEmpty() ? null : list.get(0);
    }

    @Override
    public UserDTO findByEmail(String email) {
        List<UserDTO> list = jdbcTemplate.query(
                "SELECT * FROM app_users WHERE email=?",
                rowMapper,
                email
        );
        return list.isEmpty() ? null : list.get(0);
    }

    @Override
    public List<UserDTO> getAllUsers() {
        return jdbcTemplate.query("SELECT * FROM app_users", rowMapper);
    }
}