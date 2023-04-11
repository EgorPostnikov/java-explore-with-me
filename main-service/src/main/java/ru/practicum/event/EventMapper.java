package ru.practicum.event;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.Collection;

@Mapper
public interface EventMapper {

    EventMapper INSTANCE = Mappers.getMapper(EventMapper.class);

    //@Mapping(source = "category", target = "category.id")
    Event toEvent(NewEventDto entity);

    EventFullDto toEventFullDto(Event entity);
    EventShortDto toEventShortDto(Event entity);
    Collection<EventShortDto> toEventShortDtos (Collection<Event> entities);

    //NewEventDto toNewEventDto(Event entity);
    //Collection<NewEventDto> toNewEventDtos (Collection<Event> entities);
    //Collection<EventFullDto> toEventFullDtos (Collection<Event> entities);
}
