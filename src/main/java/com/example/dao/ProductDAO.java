package com.example.dao;

import com.example.model.Product;
import java.util.List;

public interface ProductDAO {
    void save(Product product);
    List<Product> findAll();
    Product findById(int id);
}
