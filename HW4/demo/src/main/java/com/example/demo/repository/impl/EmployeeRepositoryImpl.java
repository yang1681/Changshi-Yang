package com.example.demo.repository.impl;


import com.example.demo.domain.entity.Employee;
import com.example.demo.repository.EmployeeRepository;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
public class EmployeeRepositoryImpl implements EmployeeRepository {
    private final Map<String, Employee> employeeMap;
    private final AtomicInteger idGenerator = new AtomicInteger(3);

    public EmployeeRepositoryImpl() {
        employeeMap = new ConcurrentHashMap<>();
    }

    @PostConstruct
    private void init() {
        employeeMap.put("1", new Employee("1", "Tom"));
        employeeMap.put("2", new Employee("2", "Jerry"));
    }

    @Override
    public Employee getById(String id) {
        return employeeMap.get(id);
    }

    @Override
    public Collection<Employee> getAll() {
        return employeeMap.values();
    }

    @Override
    public String save(Employee employee) {
        String id = String.valueOf(idGenerator.getAndIncrement());
        employee.setId(id);
        employeeMap.put(id, employee);
        return id;
    }
}
