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
    private String description;
    private String eventDate;
    private Integer id;
    private Boolean paid;
    private Integer participantLimit;
    private Boolean requestModeration;
    private String title;
    private UserShortDto initiator;
    private Integer views;
    private String publishedOn;
    private Integer confirmedRequests;
    private CategoryDto category;


    //private List<String> state;
    //private Location location;
    //
    //

    //
    //
}
