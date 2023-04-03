package ru.practicum.event;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ru.practicum.categories.CategoryDto;
import ru.practicum.user.UserShortDto;

import java.time.LocalDateTime;

@Setter
@Getter
@AllArgsConstructor
@ToString
public class EventShortDto {
    private String annotation;
    //private CategoryDto category;
    private Integer confirmedRequests;
    private LocalDateTime eventDate;
    private Integer id;
    private UserShortDto initiator;
    private Boolean paid;
    private String title;
    private Integer views;

}
