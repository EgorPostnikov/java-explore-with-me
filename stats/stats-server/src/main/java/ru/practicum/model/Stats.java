package ru.practicum.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class Stats {
    private String app;
    private String uri;
    private Long hits;
}

