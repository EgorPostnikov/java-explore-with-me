package ru.practicum.event;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.Collection;

@Mapper
public interface EventMapper {

    EventMapper INSTANCE = Mappers.getMapper(EventMapper.class);

    Event toEvent(NewEventDto entity);
    //@Mapping(source = "category.id", target = "category")
    EventFullDto toEventFullDto(Event entity);



    //NewEventDto toNewEventDto(Event entity);



    EventShortDto toEventShortDto(Event entity);


    //Collection<NewEventDto> toNewEventDtos (Collection<Event> entities);

    Collection<EventShortDto> toEventShortDtos (Collection<Event> entities);
    //Collection<EventFullDto> toEventFullDtos (Collection<Event> entities);
}
