package ru.practicum.admin_api.dto;

import java.util.List;

public class ApiError {
    private List<String> errors;
    private String message;
    private String reason;
    private String status;
    private String timestamp;
}
