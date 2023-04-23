package ru.practicum.compilation.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ru.practicum.event.Event;
import ru.practicum.event.EventShortDto;

import java.util.List;
@Setter
@Getter
@AllArgsConstructor
@ToString
public class CompilationDto {
    private List<EventShortDto> events;
    private Integer id;
    private Boolean pinned;
    private String title;
}
