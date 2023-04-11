package ru.practicum.event;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
@Setter
@Getter
@AllArgsConstructor
@ToString
public class NewEventDto {
    private String annotation;
    private Integer category;
    private String description;
    private String eventDate;
    //private Location location;
    private Boolean paid;
    private Integer participantLimit;
    private Boolean requestModeration;
    private String title;
}
