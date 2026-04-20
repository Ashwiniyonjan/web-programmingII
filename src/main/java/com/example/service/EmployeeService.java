package com.example.service;

import com.example.dao.EmployeeDAO;
import com.example.dto.EmployeeDTO;
import com.example.model.Employee;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmployeeService {

    private final EmployeeDAO employeeDAO;

    public EmployeeService(EmployeeDAO employeeDAO) {
        this.employeeDAO = employeeDAO;
    }

    // =========================
    // REGISTER EMPLOYEE
    // =========================
    public Employee registerEmployee(String name, String email, String contactNumber, String position) {

        // extra safety check (prevents DB crash)
        if (name == null || email == null) {
            throw new IllegalArgumentException("Invalid employee data");
        }

        EmployeeDTO dto = new EmployeeDTO(name, email, contactNumber, position);
        EmployeeDTO saved = employeeDAO.save(dto);

        return toModel(saved);
    }

    // =========================
    // GET ALL EMPLOYEES
    // =========================
    public List<Employee> getAllEmployees() {
        return employeeDAO.findAll()
                .stream()
                .map(this::toModel)
                .collect(Collectors.toList());
    }

    // =========================
    // GET BY ID
    // =========================
    public Employee getEmployeeById(Long id) {
        EmployeeDTO dto = employeeDAO.findById(id);
        return dto == null ? null : toModel(dto);
    }

    // =========================
    // DELETE
    // =========================
    public boolean deleteEmployee(Long id) {
        return employeeDAO.delete(id);
    }

    // =========================
    // CONVERT DTO → MODEL
    // =========================
    private Employee toModel(EmployeeDTO dto) {
        if (dto == null) return null;

        Employee e = new Employee();
        e.setId(dto.getId());
        e.setName(dto.getName());
        e.setEmail(dto.getEmail());
        e.setContactNumber(dto.getContactNumber());
        e.setPosition(dto.getPosition());

        return e;
    }
}