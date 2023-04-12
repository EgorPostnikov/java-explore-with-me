package ru.practicum.event;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import ru.practicum.user.User;

import java.util.Collection;

@Mapper
public interface EventMapper {

    EventMapper INSTANCE = Mappers.getMapper(EventMapper.class);

    @Mapping(target = "initiator", expression = "java(user)" )
    @Mapping(target = "category.id", source = "entity.category")
    Event toEvent(ANewEventDto entity, User user);





    EventFullDto toEventFullDto(Event entity);
    EventShortDto toEventShortDto(Event entity);
    Collection<EventShortDto> toEventShortDtos (Collection<Event> entities);

    //NewEventDto toNewEventDto(Event entity);
    //Collection<NewEventDto> toNewEventDtos (Collection<Event> entities);
    //Collection<EventFullDto> toEventFullDtos (Collection<Event> entities);
}
