package ru.practicum.compilation.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@ToString
public class UpdateCompilationRequest {
    private List<Integer> events;
    private Boolean pinned;
    private String title;
}
