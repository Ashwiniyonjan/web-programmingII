package com.example.model;

import jakarta.validation.*;
import org.junit.jupiter.api.*;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class ProductValidationTest {

    private static Validator validator;

    @BeforeAll
    static void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void blankName_shouldFailValidation() {
        Product product = new Product(0, "", "desc", 100, "offer", null);

        Set<ConstraintViolation<Product>> violations = validator.validate(product);

        assertFalse(violations.isEmpty());
    }

    @Test
    void negativePrice_shouldFailValidation() {
        Product product = new Product(0, "Shirt", "desc", -10, "offer", null);

        Set<ConstraintViolation<Product>> violations = validator.validate(product);

        assertFalse(violations.isEmpty());
    }
}