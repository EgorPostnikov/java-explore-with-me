package ru.practicum.dto;

import ru.practicum.event.EventShortDto;

import java.util.List;

public class CompilationDto {
    private List<EventShortDto> events;
    private Integer id;
    private Boolean pinned;
    private String title;
}