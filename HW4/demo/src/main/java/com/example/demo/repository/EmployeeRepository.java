package com.example.demo.repository;

import com.example.demo.domain.entity.Employee;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface EmployeeRepository {
    Employee getById(String id);
    Collection<Employee> getAll();
    String save(Employee employee);
}
