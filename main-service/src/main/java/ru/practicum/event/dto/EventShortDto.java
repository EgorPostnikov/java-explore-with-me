package ru.practicum.event.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ru.practicum.categories.dto.CategoryDto;
import ru.practicum.user.dto.UserShortDto;


@Setter
@Getter
@AllArgsConstructor
@ToString
public class EventShortDto {
    private String annotation;
    private CategoryDto category;
    private Integer confirmedRequests;
    private String eventDate;
    private Integer id;
    private UserShortDto initiator;
    private Boolean paid;
    private String title;
    private Integer views;
    private Integer commentsQty;
}
