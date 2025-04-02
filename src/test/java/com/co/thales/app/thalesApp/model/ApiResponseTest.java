package com.co.thales.app.thalesApp.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ApiResponseTest {

    @Test
    void getStatus_returnsCorrectStatus() {
        ApiResponse<String> response = new ApiResponse<>();
        response.setStatus("success");
        assertEquals("success", response.getStatus());
    }

    @Test
    void getData_returnsCorrectData() {
        ApiResponse<String> response = new ApiResponse<>();
        response.setData("data");
        assertEquals("data", response.getData());
    }

    @Test
    void getMessage_returnsCorrectMessage() {
        ApiResponse<String> response = new ApiResponse<>();
        response.setMessage("message");
        assertEquals("message", response.getMessage());
    }

    @Test
    void setStatus_setsCorrectStatus() {
        ApiResponse<String> response = new ApiResponse<>();
        response.setStatus("error");
        assertEquals("error", response.getStatus());
    }

    @Test
    void setData_setsCorrectData() {
        ApiResponse<String> response = new ApiResponse<>();
        response.setData("new data");
        assertEquals("new data", response.getData());
    }

    @Test
    void setMessage_setsCorrectMessage() {
        ApiResponse<String> response = new ApiResponse<>();
        response.setMessage("new message");
        assertEquals("new message", response.getMessage());
    }

}