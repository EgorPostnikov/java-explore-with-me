package ru.practicum.event.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ru.practicum.event.model.Location;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Setter
@Getter
@AllArgsConstructor
@ToString
public class NewEventDto {
    @NotNull(message = "Annotation is required.")
    @NotBlank(message = "Annotation is required.")
    private String annotation;
    private Integer category;
    @NotNull(message = "Description is required.")
    @NotBlank(message = "Description is required.")
    private String description;
    private String eventDate;
    private Location location;
    private Boolean paid;
    private Integer participantLimit;
    private Boolean requestModeration;
    private String title;
    private Integer confirmedRequests;
}
