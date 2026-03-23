/*package com.example.controller;

import com.example.model.Employee;
import com.example.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/employee")
public class EmployeeController {

    @Autowired
    // Fixed: The type must be 'EmployeeService' (matching your import) 
    // and the variable name should be 'employeeService'.
    private EmployeeService employeeService;

    @GetMapping("/register")
    public String showRegistrationForm() {
        return "employeeForm";
    }

    @PostMapping("/register")
    public String registerEmployee(
            @RequestParam("name") String name,
            @RequestParam("email") String email,
            @RequestParam("contactNumber") String contactNumber,
            @RequestParam("position") String position,
            @RequestParam("address") String address,
            @RequestParam("age") int age,
            Model model) {

        Employee employee = employeeService.registerEmployee(name, email, contactNumber, position, address, age);
        model.addAttribute("employee", employee);
        return "employeeSummary";
    }
}*/