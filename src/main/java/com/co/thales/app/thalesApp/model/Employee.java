package com.co.thales.app.thalesApp.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Employee {

    @JsonProperty("employee_name")
    private String name;

    @JsonProperty("employee_salary")
    private Double salary;

    @JsonProperty("employee_age")
    private Integer age;

    private Double annualSalary;
}