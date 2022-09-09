package com.example.employee.service;

import com.example.employee.domain.EmployeeDTO;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Retryable(value = Exception.class, maxAttempts = 3, backoff = @Backoff(delay = 1000L, multiplier = 2))
public interface EmployeeService {
    List<EmployeeDTO> fetchEmpAgeLargerThan(int age);
}
