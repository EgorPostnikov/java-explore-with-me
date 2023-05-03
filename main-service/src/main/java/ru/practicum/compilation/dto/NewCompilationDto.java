package ru.practicum.compilation.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
public class NewCompilationDto {
    private List<Integer> events;
    private Boolean pinned;
    @NotNull(message = "Title is required.")
    private String title;
}
