package com.co.thales.app.thalesApp.service.impl;

import com.co.thales.app.thalesApp.model.ApiResponse;
import com.co.thales.app.thalesApp.model.Employee;
import com.co.thales.app.thalesApp.service.EmployeeService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private static final Logger logger = LoggerFactory.getLogger(EmployeeServiceImpl.class);

    private static final String BASE_URL = "http://dummy.restapiexample.com/api/v1";
    private final HttpClient httpClient;
    private final ObjectMapper objectMapper;

    public EmployeeServiceImpl() {
        this.httpClient = HttpClient.newHttpClient();
        this.objectMapper = new ObjectMapper();
        // Configure ObjectMapper to ignore unknown properties
        this.objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    public CompletableFuture<List<Employee>> getAllEmployees() {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/employees"))
                .GET()
                .build();

        return httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(response -> {
                    if (response.statusCode() == 200) {
                        return parseEmployeeList(response.body());
                    } else {
                        logger.error("Failed to fetch employees, status code: {}", response.statusCode());
                        throw new RuntimeException("Failed to fetch employees, status code: " + response.statusCode());
                    }
                });
    }

    public CompletableFuture<Employee> getEmployeeById(Long id) {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/employee/" + id))
                .GET()
                .build();

        return httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(HttpResponse::body)
                .thenApply(this::parseEmployee);
    }

    private List<Employee> parseEmployeeList(String json) {
        try {
            ApiResponse<List<Employee>> response = objectMapper.readValue(json,
                    new TypeReference<ApiResponse<List<Employee>>>() {});
            logger.info("Parsed employee list: {}", response.getData());
            return response.getData();
        } catch (Exception e) {
            logger.error("Error parsing employee list", e);
            throw new RuntimeException("Error parsing employee list", e);
        }
    }

    private Employee parseEmployee(String json) {
        try {
            ApiResponse<Employee> response = objectMapper.readValue(json,
                    new TypeReference<ApiResponse<Employee>>() {});
            logger.info("Parsed employee: {}", response.getData());
            return response.getData();
        } catch (Exception e) {
            logger.error("Error parsing employee", e);
            throw new RuntimeException("Error parsing employee", e);
        }
    }
}