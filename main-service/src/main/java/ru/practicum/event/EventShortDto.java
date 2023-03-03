package ru.practicum.event;


import ru.practicum.dto.CategoryDto;
import ru.practicum.user.UserShortDto;

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
