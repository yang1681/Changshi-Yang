package com.example.demo.service;


import com.example.demo.domain.dto.EmployeeResponseDTO;
import com.example.demo.domain.entity.Employee;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public interface EmployeeService {
    EmployeeResponseDTO getById(String id);
    Collection<EmployeeResponseDTO> getAllEmp();
    String save(Employee emp);
}
