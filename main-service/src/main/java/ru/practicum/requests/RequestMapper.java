package ru.practicum.requests;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import ru.practicum.event.Event;
import ru.practicum.event.EventFullDto;
import ru.practicum.event.EventShortDto;
import ru.practicum.event.NewEventDto;
import ru.practicum.user.User;

import java.util.Collection;

@Mapper
public interface RequestMapper {

    RequestMapper INSTANCE = Mappers.getMapper(RequestMapper.class);


    ParticipationRequestDto toParticipationRequestDto(ParticipationRequest entity);
    ParticipationRequest toParticipationRequest(ParticipationRequestDto entity);
    Collection<ParticipationRequestDto> toParticipationRequestDtos(Collection<ParticipationRequest> entities);


}
