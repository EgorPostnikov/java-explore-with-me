package ru.practicum.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class StatsDto {
    private String app;
    private String uri;
    private Long hits;
}
