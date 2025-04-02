package com.co.thales.app.thalesApp.service;

import com.co.thales.app.thalesApp.model.Employee;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface EmployeeService {
    CompletableFuture<List<Employee>> getAllEmployees();
    CompletableFuture<Employee> getEmployeeById(Long id);
}