package com.example.dao;

import com.example.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import jakarta.annotation.PostConstruct;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class ProductDAOImpl implements ProductDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    // Create table automatically
    @PostConstruct
    public void init() {
        String sql = "CREATE TABLE IF NOT EXISTS productdb (" +
                "id INT AUTO_INCREMENT PRIMARY KEY, " +
                "name VARCHAR(255), " +
                "description VARCHAR(1000), " +
                "price DOUBLE, " +
                "offer VARCHAR(255), " +
                "image_path VARCHAR(500))";
        jdbcTemplate.execute(sql);
    }

    // SAVE PRODUCT (FIXED - generates ID properly)
    @Override
    public void save(Product product) {

        String sql = "INSERT INTO productdb (name, description, price, offer, image_path) VALUES (?, ?, ?, ?, ?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, new String[]{"id"});
            ps.setString(1, product.getName());
            ps.setString(2, product.getDescription());
            ps.setDouble(3, product.getPrice());
            ps.setString(4, product.getOffer());
            ps.setString(5, product.getImagePath());
            return ps;
        }, keyHolder);

        // Set generated ID back to object
        if (keyHolder.getKey() != null) {
            product.setId(keyHolder.getKey().intValue());
        }
    }

    // GET ALL PRODUCTS
    @Override
    public List<Product> findAll() {
        String sql = "SELECT * FROM productdb";
        return jdbcTemplate.query(sql, new ProductRowMapper());
    }

    // GET PRODUCT BY ID
    @Override
    public Product findById(int id) {
        String sql = "SELECT * FROM productdb WHERE id = ?";
        List<Product> list = jdbcTemplate.query(sql, new ProductRowMapper(), id);
        return list.isEmpty() ? null : list.get(0);
    }

    // ROW MAPPER
    private static class ProductRowMapper implements RowMapper<Product> {
        @Override
        public Product mapRow(ResultSet rs, int rowNum) throws SQLException {
            Product p = new Product();
            p.setId(rs.getInt("id"));
            p.setName(rs.getString("name"));
            p.setDescription(rs.getString("description"));
            p.setPrice(rs.getDouble("price"));
            p.setOffer(rs.getString("offer"));
            p.setImagePath(rs.getString("image_path"));
            return p;
        }
    }
}