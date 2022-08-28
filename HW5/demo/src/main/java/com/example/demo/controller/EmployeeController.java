package com.example.demo.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.demo.constant.ApplicationConst;
import com.example.demo.model.Employee;
import com.example.demo.model.EmployeesData;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.stream.Collectors;


@RestController
public class EmployeeController {

    @RequestMapping("/firstEndPoint")
    public String firstEndPoint()  {
        RestTemplate restTemplate = new RestTemplate();
        EmployeesData data = restTemplate.getForObject(ApplicationConst.EMPLOYEES_WEB_URL, EmployeesData.class);
        return JSONObject.toJSONString(data);
    }

    @RequestMapping("/secondEndPoint")
    public String secondEndPoint()  {
        RestTemplate restTemplate = new RestTemplate();
        EmployeesData data = restTemplate.getForObject(ApplicationConst.EMPLOYEES_WEB_URL, EmployeesData.class);
        List<Employee> list = data.getEmployeeList().stream().filter(employee -> employee.getEmployeeAge() > 30).collect(Collectors.toList());
        return JSONObject.toJSONString(list);
    }

    @RequestMapping("/hello")
    public String hello()  {
        return "hello";
    }
}
