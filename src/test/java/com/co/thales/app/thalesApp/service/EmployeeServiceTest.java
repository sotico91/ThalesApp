package com.co.thales.app.thalesApp.service;

import com.co.thales.app.thalesApp.model.Employee;
import com.co.thales.app.thalesApp.service.impl.EmployeeServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class EmployeeServiceTest {

    @Mock
    private HttpClient httpClient;

    @Mock
    private ObjectMapper objectMapper;

    @InjectMocks
    private EmployeeServiceImpl employeeServiceImpl;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getEmployeeById_handlesParsingError() throws Exception {
        String invalidJsonResponse = "invalid json";
        HttpResponse<String> httpResponse = (HttpResponse<String>) mock(HttpResponse.class);
        when(httpResponse.statusCode()).thenReturn(200);
        when(httpResponse.body()).thenReturn(invalidJsonResponse);
        when(httpClient.send(any(HttpRequest.class), any(HttpResponse.BodyHandler.class))).thenReturn(httpResponse);

        CompletableFuture<Employee> result = employeeServiceImpl.getEmployeeById(1L);

        assertThrows(RuntimeException.class, () -> {
            try {
                result.get();
            } catch (ExecutionException e) {
                throw (RuntimeException) e.getCause();
            }
        });
    }

    @Test
    void getEmployeeById_handlesRateLimitExceeded() throws Exception {
        HttpResponse<String> httpResponse = (HttpResponse<String>) mock(HttpResponse.class);
        when(httpResponse.statusCode()).thenReturn(429);
        when(httpClient.send(any(HttpRequest.class), any(HttpResponse.BodyHandler.class))).thenReturn(httpResponse);

        CompletableFuture<Employee> result = employeeServiceImpl.getEmployeeById(1L);

        assertThrows(RuntimeException.class, () -> {
            try {
                result.get();
            } catch (ExecutionException e) {
                throw (RuntimeException) e.getCause();
            }
        });
    }
}