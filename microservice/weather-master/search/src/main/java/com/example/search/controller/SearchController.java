package com.example.search.controller;

import com.example.search.exception.GlobalErrorHandler;
import com.example.search.exception.InvalidInputException;
import com.example.search.service.EmployeeService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class SearchController {

    private final EmployeeService employeeService;

    public SearchController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping("/search/employees")
    public ResponseEntity<?> getDetails(@RequestParam List<Integer> ages) {
        //TODO
        try {
            throw new GlobalErrorHandler();
        } catch (GlobalErrorHandler e) {

        }
        return new ResponseEntity<>(employeeService.fetchAllEmployeesByAges(ages), HttpStatus.OK);
    }
}
