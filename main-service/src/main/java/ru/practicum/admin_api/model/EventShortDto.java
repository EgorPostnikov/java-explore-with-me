package ru.practicum.admin_api.model;


import ru.practicum.admin_api.dto.UserShortDto;

public class EventShortDto {
    private String annotation;
    private CategoryDto category;
    private Integer confirmedRequests;
    private String eventDate;
    private Integer id;
    private UserShortDto initiator;
    private Boolean paid;
    private String title;
    private Integer views;

}
