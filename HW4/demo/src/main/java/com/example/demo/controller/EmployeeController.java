package com.example.demo.controller;


import com.example.demo.domain.dto.EmployeeResponseDTO;
import com.example.demo.domain.entity.Employee;
import com.example.demo.exception.CommonErrorResponse;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
public class EmployeeController {

    private final EmployeeService employeeService;

    @Autowired
    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping("/employees")
    public ResponseEntity<Collection<EmployeeResponseDTO>> getAll() {
        return new ResponseEntity<>(employeeService.getAllEmp(), HttpStatus.OK);
    }

    @GetMapping("/employees/{id}")
    public ResponseEntity<EmployeeResponseDTO> getEmpById(@PathVariable String id) {
        return new ResponseEntity<>(employeeService.getById(id), HttpStatus.OK);
    }

    @PostMapping("/employees")
    public ResponseEntity<?> createEmp(@RequestBody Employee employee) {
        return new ResponseEntity<>(employeeService.save(employee), HttpStatus.CREATED);
    }


    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<?> resourceNoutFoundHandler() {
        return new ResponseEntity<>(new CommonErrorResponse("cannot find that resource"), HttpStatus.NOT_FOUND);
    }
}
