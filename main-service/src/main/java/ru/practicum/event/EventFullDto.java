package ru.practicum.event;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ru.practicum.categories.CategoryDto;
import ru.practicum.user.UserShortDto;


import java.util.List;
@Setter
@Getter
@AllArgsConstructor
@ToString
public class EventFullDto {

    private String annotation;
    //private CategoryDto category;
    //private Integer confirmedRequests;
    //private String createdOn;
    private String description;
    private String eventDate;
    private Integer id;
    //private UserShortDto initiator;
    private Location location;
    private Boolean paid;
    private Integer participantLimit;
    //private String publishedOn;
    private Boolean requestModeration;
    private List<String> state;
    private String title;
    //private Integer views;
}
