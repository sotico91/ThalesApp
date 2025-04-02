package com.co.thales.app.thalesApp.controller;

import com.co.thales.app.thalesApp.business.EmployeeBusinessBean;
import com.co.thales.app.thalesApp.model.Employee;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class EmployeeControllerTest {

    @Mock
    private EmployeeBusinessBean employeeBusinessBean;

    @InjectMocks
    private EmployeeController employeeController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllEmployees_returnsEmployeeList() throws ExecutionException, InterruptedException {
        List<Employee> employees = List.of(new Employee());
        when(employeeBusinessBean.getAllEmployeesWithAnnualSalary()).thenReturn(CompletableFuture.completedFuture(employees));

        ResponseEntity<List<Employee>> response = employeeController.getAllEmployees();

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(employees, response.getBody());
    }

    @Test
    void getEmployeeById_returnsEmployee() throws ExecutionException, InterruptedException {
        Employee employee = new Employee();
        when(employeeBusinessBean.getEmployeeWithAnnualSalary(1L)).thenReturn(CompletableFuture.completedFuture(employee));

        ResponseEntity<Employee> response = employeeController.getEmployeeById(1L);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(employee, response.getBody());
    }

    @Test
    void getEmployeeById_handlesNotFound() throws ExecutionException, InterruptedException {
        when(employeeBusinessBean.getEmployeeWithAnnualSalary(1L)).thenReturn(CompletableFuture.completedFuture(null));

        ResponseEntity<Employee> response = employeeController.getEmployeeById(1L);

        assertEquals(404, response.getStatusCodeValue());
    }

}