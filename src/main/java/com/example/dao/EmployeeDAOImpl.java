package com.example.dao;

import com.example.dto.EmployeeDTO;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

@Repository
public class EmployeeDAOImpl implements EmployeeDAO {

    private final JdbcTemplate jdbcTemplate;

    public EmployeeDAOImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        initTable();
    }

    private final RowMapper<EmployeeDTO> rowMapper = (rs, rowNum) -> new EmployeeDTO(
            rs.getLong("id"),
            rs.getString("name"),
            rs.getString("email"),
            rs.getString("contact_number"),
            rs.getString("position")
    );

    private void initTable() {
        jdbcTemplate.execute(
                "CREATE TABLE IF NOT EXISTS employees (" +
                        "id BIGINT AUTO_INCREMENT PRIMARY KEY, " +
                        "name VARCHAR(255), " +
                        "email VARCHAR(255), " +
                        "contact_number VARCHAR(20), " +
                        "position VARCHAR(255)" +
                        ")"
        );
    }

    @Override
    public EmployeeDTO save(EmployeeDTO employee) {

        String sql = "INSERT INTO employees (name,email,contact_number,position) VALUES (?,?,?,?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(conn -> {
            PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, employee.getName());
            ps.setString(2, employee.getEmail());
            ps.setString(3, employee.getContactNumber());
            ps.setString(4, employee.getPosition());
            return ps;
        }, keyHolder);

        Number key = keyHolder.getKey();
        if (key != null) {
            employee.setId(key.longValue());
        }

        return employee;
    }

    @Override
    public List<EmployeeDTO> findAll() {
        return jdbcTemplate.query("SELECT * FROM employees", rowMapper);
    }

    @Override
    public EmployeeDTO findById(Long id) {
        List<EmployeeDTO> list = jdbcTemplate.query(
                "SELECT * FROM employees WHERE id=?",
                rowMapper,
                id
        );
        return list.isEmpty() ? null : list.get(0);
    }

    @Override
    public EmployeeDTO update(Long id, EmployeeDTO employee) {

        int rows = jdbcTemplate.update(
                "UPDATE employees SET name=?, email=?, contact_number=?, position=? WHERE id=?",
                employee.getName(),
                employee.getEmail(),
                employee.getContactNumber(),
                employee.getPosition(),
                id
        );

        if (rows == 0) return null;

        employee.setId(id);
        return employee;
    }

    @Override
    public boolean delete(Long id) {
        return jdbcTemplate.update("DELETE FROM employees WHERE id=?", id) > 0;
    }
}