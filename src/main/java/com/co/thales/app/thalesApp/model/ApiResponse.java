package com.co.thales.app.thalesApp.model;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ApiResponse<T> {
    private String status;
    private T data;
    private String message;

}