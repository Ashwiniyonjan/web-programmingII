package com.example.controller;

import com.example.model.Employee;
import com.example.service.EmployeeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
public class EmployeeController {

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    // =========================
    // GET ALL EMPLOYEES
    // =========================
    @GetMapping("/api/employees")
    public List<Employee> getAllEmployeesAPI() {
        return employeeService.getAllEmployees();
    }

    // =========================
    // ADD EMPLOYEE (FIXED FOR CUCUMBER + POSTMAN)
    // =========================
    @PostMapping("/api/employees")
    public ResponseEntity<?> addEmployeeAPI(@RequestBody Employee employee) {

        // ================= VALIDATION =================
        if (employee.getName() == null || employee.getName().trim().isEmpty()) {
            return ResponseEntity.badRequest()
                    .body(Map.of(
                            "status", "error",
                            "errors", Map.of("name", "Name is required")
                    ));
        }

        if (employee.getEmail() == null || !employee.getEmail().contains("@")) {
            return ResponseEntity.badRequest()
                    .body(Map.of(
                            "status", "error",
                            "errors", Map.of("email", "Invalid email format")
                    ));
        }

        if (employee.getContactNumber() == null || employee.getContactNumber().length() < 10) {
            return ResponseEntity.badRequest()
                    .body(Map.of(
                            "status", "error",
                            "errors", Map.of("contactNumber", "Invalid contact number")
                    ));
        }

        // ================= SAVE =================
        Employee saved = employeeService.registerEmployee(
                employee.getName(),
                employee.getEmail(),
                employee.getContactNumber(),
                employee.getPosition()
        );

        return ResponseEntity.ok(
                Map.of(
                        "status", "success",
                        "employee", saved
                )
        );
    }
}