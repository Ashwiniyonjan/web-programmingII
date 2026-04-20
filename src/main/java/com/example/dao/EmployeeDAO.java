package com.example.dao;

import com.example.dto.EmployeeDTO;

import java.util.List;

public interface EmployeeDAO {

    EmployeeDTO save(EmployeeDTO employee);

    List<EmployeeDTO> findAll();

    EmployeeDTO findById(Long id);

    EmployeeDTO update(Long id, EmployeeDTO employee);

    boolean delete(Long id);
}