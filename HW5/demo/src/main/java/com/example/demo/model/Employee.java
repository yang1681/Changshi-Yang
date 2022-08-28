package com.example.demo.model;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.math.BigDecimal;


@Data
public class Employee {

    private Integer id;
    @JSONField(name="employee_name")
    private String employeeName;
    @JSONField(name="employee_salary")
    private BigDecimal employeeSalary;
    @JSONField(name="employee_age")
    private Integer employeeAge;
    @JSONField(name="profile_image")
    private String profileImage;
}
