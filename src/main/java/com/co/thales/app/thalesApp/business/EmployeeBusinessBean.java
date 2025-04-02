package com.co.thales.app.thalesApp.business;

import com.co.thales.app.thalesApp.model.Employee;
import com.co.thales.app.thalesApp.service.EmployeeService;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
public class EmployeeBusinessBean {

    private static final Logger logger = LoggerFactory.getLogger(EmployeeBusinessBean.class);

    @Inject
    private EmployeeService employeeService;

    public CompletableFuture<List<Employee>> getAllEmployeesWithAnnualSalary() {
           return employeeService.getAllEmployees()
                .thenApply(employees -> {
                    employees.forEach(this::calculateAnnualSalary);
                    return employees;
                });
    }

    public CompletableFuture<Employee> getEmployeeWithAnnualSalary(Long id) {
        return employeeService.getEmployeeById(id)
                .thenApply(employee -> {
                    calculateAnnualSalary(employee);
                    return employee;
                });
    }

    private void calculateAnnualSalary(Employee employee) {
        if (employee != null && employee.getSalary() != null) {
            logger.info("Calculating annual salary for employee with salary: {}", employee.getSalary());
            double annualSalary = employee.getSalary() * 12;
            employee.setAnnualSalary(annualSalary);
            logger.info("Annual salary calculated: {}", annualSalary);
        } else {
            logger.warn("Employee or salary is null, cannot calculate annual salary.");
        }
    }
}