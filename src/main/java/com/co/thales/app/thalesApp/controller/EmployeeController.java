package com.co.thales.app.thalesApp.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import com.co.thales.app.thalesApp.business.EmployeeBusinessBean;
import com.co.thales.app.thalesApp.model.Employee;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    private final EmployeeBusinessBean employeeBusinessBean;

    @Autowired
    public EmployeeController(EmployeeBusinessBean employeeBusinessBean) {
        this.employeeBusinessBean = employeeBusinessBean;
    }

    @GetMapping
    public ResponseEntity<List<Employee>> getAllEmployees() {
        try {
            CompletableFuture<List<Employee>> futureEmployees = employeeBusinessBean.getAllEmployeesWithAnnualSalary();
            List<Employee> employees = futureEmployees.get();
            return ResponseEntity.ok(employees);
        } catch (InterruptedException | ExecutionException e) {
            return ResponseEntity.status(500).body(null);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable Long id) {
        try {
            CompletableFuture<Employee> futureEmployee = employeeBusinessBean.getEmployeeWithAnnualSalary(id);
            Employee employee = futureEmployee.get();

            if (employee == null) {
                return ResponseEntity.status(404).body(null);
            }

            return ResponseEntity.ok(employee);
        } catch (InterruptedException | ExecutionException e) {
            return ResponseEntity.status(500).body(null);
        }
    }
}