package com.co.thales.app.thalesApp.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EmployeeTest {

    @Test
    void employeeNameIsSetCorrectly() {
        Employee employee = new Employee();
        employee.setName("Juan Andres Perez");
        assertEquals("Juan Andres Perez", employee.getName());
    }

    @Test
    void employeeSalaryIsSetCorrectly() {
        Employee employee = new Employee();
        employee.setSalary(50000.0);
        assertEquals(50000.0, employee.getSalary());
    }

    @Test
    void employeeAgeIsSetCorrectly() {
        Employee employee = new Employee();
        employee.setAge(30);
        assertEquals(30, employee.getAge());
    }

    @Test
    void employeeAnnualSalaryIsSetCorrectly() {
        Employee employee = new Employee();
        employee.setAnnualSalary(600000.0);
        assertEquals(600000.0, employee.getAnnualSalary());
    }

    @Test
    void employeeNameIsNullByDefault() {
        Employee employee = new Employee();
        assertNull(employee.getName());
    }

    @Test
    void employeeSalaryIsNullByDefault() {
        Employee employee = new Employee();
        assertNull(employee.getSalary());
    }

    @Test
    void employeeAgeIsNullByDefault() {
        Employee employee = new Employee();
        assertNull(employee.getAge());
    }

    @Test
    void employeeAnnualSalaryIsNullByDefault() {
        Employee employee = new Employee();
        assertNull(employee.getAnnualSalary());
    }

}