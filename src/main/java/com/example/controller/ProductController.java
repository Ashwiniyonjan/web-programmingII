package com.example.controller;

import com.example.model.Product;
import com.example.service.ProductService;
import jakarta.servlet.ServletContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

@Controller
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private ServletContext servletContext;

    // WEB PAGE: Show Add Product Form
    @GetMapping("/product/add")
    public String showProductForm() {
        return "productForm";
    }

    // WEB PAGE: Submit Product Form
    @PostMapping("/product/add")
    public String addProduct(
            @RequestParam("name") String name,
            @RequestParam("description") String description,
            @RequestParam("price") double price,
            @RequestParam("offer") String offer,
            @RequestParam("imageFile") MultipartFile imageFile,
            RedirectAttributes redirectAttributes) {

        String imagePath = null;

        if (!imageFile.isEmpty()) {
            try {
                String uploadDir = servletContext.getRealPath("/") + "uploads" + File.separator;
                File directory = new File(uploadDir);
                if (!directory.exists()) {
                    directory.mkdirs();
                }

                String originalFilename = imageFile.getOriginalFilename();
                String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
                String newFileName = UUID.randomUUID().toString() + extension;

                Path path = Paths.get(uploadDir + newFileName);
                Files.write(path, imageFile.getBytes());

                imagePath = "/uploads/" + newFileName;

            } catch (IOException e) {
                return "redirect:/product/add?error=upload_failed";
            }
        }

        Product product = new Product(0, name, description, price, offer, imagePath);
        productService.addProduct(product);

        redirectAttributes.addFlashAttribute("product", product);
        return "redirect:/product/summary";
    }

    // Product Summary Page
    @GetMapping("/product/summary")
    public String productSummary(Model model) {
        if (!model.containsAttribute("product")) {
            return "redirect:/product/add";
        }
        return "productSummary";
    }

    // =========================
    // API: GET ALL PRODUCTS
    // =========================
    @GetMapping("/api/products")
    @ResponseBody
    public List<Product> getAllProducts() {
        return productService.getAllProducts();
    }

    @PostMapping("/api/products/add")
    @ResponseBody
    public Map<String, Object> addProductAPI(@RequestBody Product product) {

        productService.addProduct(product);

        return Map.of(
                "status", "success",
                "message", "Product created successfully",
                "product", product
        );
    }
}