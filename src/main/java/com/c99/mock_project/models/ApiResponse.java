package com.c99.mock_project.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

public class ApiResponse<T> {
    private T data;
    private ErrorResponse error;

    public ApiResponse() {
    }

    public ApiResponse(T data, ErrorResponse error) {
        this.data = data;
        this.error = error;
    }

    // Getters and Setters
    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public ErrorResponse getError() {
        return error;
    }

    public void setError(ErrorResponse error) {
        this.error = error;
    }
}
