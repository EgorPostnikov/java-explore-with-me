package ru.practicum.event;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.practicum.event.Location;

import javax.persistence.Entity;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateEventUserRequest {
    private String annotation;
    private Integer category;
    private String description;
    private String eventDate;
    private Location location;
    private Boolean paid;
    private Integer participantLimit;
    private Boolean requestModeration;
    private String stateAction;
    private String title;
}
