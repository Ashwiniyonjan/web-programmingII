package com.example.controller;

import com.example.model.Employee;
import com.example.service.EmployeeService;
import com.example.dto.UserDTO;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    // =========================
    // SHOW EMPLOYEE FORM
    // =========================
    @GetMapping("/employee")
    public String showEmployeeForm(HttpSession session) {

        // ✅ CHECK LOGIN SESSION
        UserDTO user = (UserDTO) session.getAttribute("loggedInUser");

        if (user == null) {
            return "redirect:/login";
        }

        return "employeeForm";
    }

    // =========================
    // HANDLE EMPLOYEE FORM SUBMIT
    // =========================
    @PostMapping("/employee")
    public String registerEmployee(
            @RequestParam("name") String name,
            @RequestParam("email") String email,
            @RequestParam("contactNumber") String contactNumber,
            @RequestParam("position") String position,
            HttpSession session,
            Model model) {

        // ✅ CHECK LOGIN AGAIN (SECURITY)
        UserDTO user = (UserDTO) session.getAttribute("loggedInUser");

        if (user == null) {
            return "redirect:/login";
        }

        try {
            Employee employee = employeeService.registerEmployee(
                    name, email, contactNumber, position
            );

            model.addAttribute("employee", employee);

            // FINAL PAGE
            return "employeeSummary";

        } catch (Exception e) {
            model.addAttribute("errorMessage", "Employee registration failed.");
            return "employeeForm";
        }
    }
}