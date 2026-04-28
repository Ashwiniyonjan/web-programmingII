package com.example.service;

import com.example.dao.ProductDAO;
import com.example.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductDAO productDAO;

    public void addProduct(Product product) {
        productDAO.save(product);
    }

    public List<Product> getAllProducts() {
        return productDAO.findAll();
    }
    
    public Product getProductById(int id) {
        return productDAO.findById(id);
    }
}
