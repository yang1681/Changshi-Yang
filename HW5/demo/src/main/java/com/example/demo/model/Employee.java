package com.example.demo.model;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.math.BigDecimal;

/**
 * Title: <br>
 * Description: <br>
 * Company: wondersgroup.com <br>
 *
 * @author 袁浩霆
 * @version 1.0
 */
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
