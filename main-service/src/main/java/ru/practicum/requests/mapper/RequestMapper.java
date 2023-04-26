package ru.practicum.requests.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import ru.practicum.requests.dto.ParticipationRequestDto;
import ru.practicum.requests.model.ParticipationRequest;

import java.util.Collection;

@Mapper
public interface RequestMapper {

    RequestMapper INSTANCE = Mappers.getMapper(RequestMapper.class);


    ParticipationRequestDto toParticipationRequestDto(ParticipationRequest entity);

    ParticipationRequest toParticipationRequest(ParticipationRequestDto entity);

    Collection<ParticipationRequestDto> toParticipationRequestDtos(Collection<ParticipationRequest> entities);


}
