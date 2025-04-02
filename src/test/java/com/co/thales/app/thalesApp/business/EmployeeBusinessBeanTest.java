package com.co.thales.app.thalesApp.business;

import com.co.thales.app.thalesApp.model.Employee;
import com.co.thales.app.thalesApp.service.EmployeeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class EmployeeBusinessBeanTest {

    @Mock
    private EmployeeService employeeService;

    @InjectMocks
    private EmployeeBusinessBean employeeBusinessBean;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllEmployeesWithAnnualSalary_returnsEmployeeList() throws ExecutionException, InterruptedException {
        List<Employee> employees = List.of(new Employee());
        when(employeeService.getAllEmployees()).thenReturn(CompletableFuture.completedFuture(employees));

        CompletableFuture<List<Employee>> result = employeeBusinessBean.getAllEmployeesWithAnnualSalary();

        assertEquals(employees, result.get());
    }
    @Test
    void getEmployeeWithAnnualSalary_returnsEmployee() throws ExecutionException, InterruptedException {
        Employee employee = new Employee();
        when(employeeService.getEmployeeById(1L)).thenReturn(CompletableFuture.completedFuture(employee));

        CompletableFuture<Employee> result = employeeBusinessBean.getEmployeeWithAnnualSalary(1L);

        assertEquals(employee, result.get());
    }

    @Test
    void getEmployeeWithAnnualSalary_handlesNullEmployee() throws ExecutionException, InterruptedException {
        when(employeeService.getEmployeeById(1L)).thenReturn(CompletableFuture.completedFuture(null));

        CompletableFuture<Employee> result = employeeBusinessBean.getEmployeeWithAnnualSalary(1L);

        assertNull(result.get());
    }
}