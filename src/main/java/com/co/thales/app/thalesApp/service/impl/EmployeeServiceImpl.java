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
import java.util.function.Function;

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

    private static final int MAX_RETRIES = 3;
    private static final long BACKOFF_DELAY_MS = 1000;

    public CompletableFuture<List<Employee>> getAllEmployees() {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/employees"))
                .GET()
                .build();

        return sendRequestWithRetry(request, this::parseEmployeeList);
    }

    public CompletableFuture<Employee> getEmployeeById(Long id) {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/employee/" + id))
                .GET()
                .build();

        return sendRequestWithRetry(request, this::parseEmployee);
    }

    private <T> CompletableFuture<T> sendRequestWithRetry(HttpRequest request, Function<String, T> parser) {
        return CompletableFuture.supplyAsync(() -> {
            int attempts = 0;
            while (attempts < MAX_RETRIES) {
                try {
                    HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
                    if (response.statusCode() == 200) {
                        return parser.apply(response.body());
                    } else if (response.statusCode() == 429) {
                        logger.warn("Rate limit exceeded, retrying... Attempt: {}", attempts + 1);
                        Thread.sleep(BACKOFF_DELAY_MS * (attempts + 1));
                    } else {
                        logger.error("Failed to fetch data, status code: {}", response.statusCode());
                        throw new RuntimeException("Failed to fetch data, status code: " + response.statusCode());
                    }
                } catch (Exception e) {
                    logger.error("Error during request", e);
                    throw new RuntimeException("Error during request", e);
                }
                attempts++;
            }
            throw new RuntimeException("Max retries reached, unable to fetch data");
        });
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