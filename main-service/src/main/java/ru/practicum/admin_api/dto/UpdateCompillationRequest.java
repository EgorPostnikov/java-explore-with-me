package ru.practicum.admin_api.dto;

import java.util.List;

public class UpdateCompillationRequest {
    private List<Integer> events;
    private Boolean pinned;
    private String title;
}
