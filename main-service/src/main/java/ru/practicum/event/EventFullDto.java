package ru.practicum.event;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ru.practicum.categories.CategoryDto;
import ru.practicum.user.UserShortDto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Setter
@Getter
@AllArgsConstructor
@ToString
public class EventFullDto {

    private String annotation;
    private String description;
    private String eventDate;
    private Integer id;
    private Boolean paid;
    private Integer participantLimit;
    private Boolean requestModeration;
    private String title;
    private UserShortDto initiator;
    private Integer views;
    private String createdOn;
    private Integer confirmedRequests;
    private CategoryDto category;
    private Location location;
    private String state;
    private String publishedOn;

}
