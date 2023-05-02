package ru.practicum.event.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.practicum.event.dto.EventFullDto;
import ru.practicum.event.dto.EventShortDto;
import ru.practicum.event.dto.NewEventDto;
import ru.practicum.event.model.Event;
import ru.practicum.user.model.User;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.List;

@Mapper(componentModel = "spring")
public interface EventMapper {

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Mapping(target = "initiator", expression = "java(user)")
    @Mapping(target = "category.id", source = "entity.category")
    @Mapping(target = "confirmedRequests", source = "entity.confirmedRequests", defaultValue = "0")
    @Mapping(target = "eventDate", source = "entity.eventDate", qualifiedBy = WithStringToLocalDate.class)
    Event toEvent(NewEventDto entity, User user);

    @WithStringToLocalDate
    default String stringToLocalDate(String source) {
        return LocalDateTime.parse(source, formatter).toString();
    }

    @WithLocalDateToString
    default String localDateToString(LocalDateTime source) {
        return source.format(formatter);
    }

    @Mapping(target = "eventDate", source = "entity.eventDate", qualifiedBy = WithLocalDateToString.class)
    @Mapping(target = "id", source = "eventId")
    EventFullDto toEventFullDto(Event entity);

    @Mapping(target = "eventDate", source = "eventDate", qualifiedBy = WithLocalDateToString.class)
    @Mapping(target = "id", source = "eventId")
    EventShortDto toEventShortDto(Event entity);

    Collection<EventShortDto> toEventShortDtos(Collection<Event> entities);

    List<EventShortDto> toEventShortDtos(List<Event> entities);

    Collection<EventFullDto> toEventFullDtos(Collection<Event> entities);

}
