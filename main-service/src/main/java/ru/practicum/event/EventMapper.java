package ru.practicum.event;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.Collection;

@Mapper
public interface EventMapper {

    EventMapper INSTANCE = Mappers.getMapper(EventMapper.class);

    NewEventDto toNewEventDto(Event entity);

    Event toEvent(NewEventDto entity);
    EventShortDto toEventShortDto(Event entity);
    EventFullDto toEventFullDto(Event entity);

    Collection<NewEventDto> toNewEventDtos (Collection<Event> entities);

    Collection<EventShortDto> toEventShortDtos (Collection<Event> entities);
    Collection<EventFullDto> toEventFullDtos (Collection<Event> entities);
}
