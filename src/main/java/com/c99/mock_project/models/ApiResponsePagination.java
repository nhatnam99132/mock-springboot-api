package com.c99.mock_project.models;

public class ApiResponsePagination<T> {
    private T data;
    private ErrorResponse error;
    private Long totalItems;
    private Integer totalPages;

    // Constructor, getter, setter
    public ApiResponsePagination(T data, ErrorResponse error, Long totalItems, Integer totalPages) {
        this.data = data;
        this.error = error;
        this.totalItems = totalItems;
        this.totalPages = totalPages;
    }

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

    public Long getTotalItems() {
        return totalItems;
    }

    public void setTotalItems(Long totalItems) {
        this.totalItems = totalItems;
    }

    public Integer getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(Integer totalPages) {
        this.totalPages = totalPages;
    }
}
