package com.co.thales.app.thalesApp.service.impl;

import com.co.thales.app.thalesApp.model.Employee;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class EmployeeMockDataProvider {
    
    private static final Logger logger = LoggerFactory.getLogger(EmployeeMockDataProvider.class);
    private final Map<Long, Employee> mockEmployees = new HashMap<>();
    
    public EmployeeMockDataProvider() {
        initializeMockData();
        logger.info("Mock employee data initialized with {} records", mockEmployees.size());
    }
    
    private void initializeMockData() {

        addEmployee(1L, "Tiger Nixon", 320800.0, 61);
        addEmployee(2L, "Garrett Winters", 170750.0, 63);
        addEmployee(3L, "Ashton Cox", 86000.0, 66);
        addEmployee(4L, "Cedric Kelly", 433060.0, 22);
        addEmployee(5L, "Airi Satou", 162700.0, 33);
        addEmployee(6L, "Brielle Williamson", 372000.0, 61);
        addEmployee(7L, "Herrod Chandler", 137500.0, 59);
        addEmployee(8L, "Rhona Davidson", 327900.2, 55);
        addEmployee(9L, "Colleen Hurst", 205500.0, 39);
        addEmployee(10L, "Sonya Frost", 103600.1, 23);

    }
    
    private void addEmployee(Long id, String name, Double salary, Integer age) {
        Employee employee = new Employee();
        employee.setName(name);
        employee.setSalary(salary);
        employee.setAge(age);
        employee.setAnnualSalary(salary * 12);
        mockEmployees.put(id, employee);
    }
    
    public List<Employee> getAllEmployees() {
        logger.info("Returning mock data for all employees");
        return new ArrayList<>(mockEmployees.values());
    }
    
    public Employee getEmployeeById(Long id) {
        logger.info("Returning mock data for employee with id: {}", id);
        Employee employee = mockEmployees.get(id);
        if (employee == null) {
            employee = new Employee();
            employee.setName("Mock Employee " + id);
            employee.setSalary(50000.0);
            employee.setAge(30);
            employee.setAnnualSalary(50000.0 * 12);
        }
        return employee;
    }
}