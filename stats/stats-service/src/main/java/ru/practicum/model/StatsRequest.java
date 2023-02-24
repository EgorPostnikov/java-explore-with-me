package ru.practicum.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Collection;
@AllArgsConstructor
@Getter
@Setter
public class StatsRequest {
    private LocalDateTime start;
    private LocalDateTime end;
    private Collection <String> uris;
    private Boolean unique;
}
