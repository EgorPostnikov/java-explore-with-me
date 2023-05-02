package ru.practicum.requests.mapper;

import org.mapstruct.Mapper;
import ru.practicum.requests.dto.ParticipationRequestDto;
import ru.practicum.requests.model.ParticipationRequest;

import java.util.Collection;

@Mapper(componentModel = "spring")
public interface RequestMapper {

    ParticipationRequestDto toParticipationRequestDto(ParticipationRequest entity);

    ParticipationRequest toParticipationRequest(ParticipationRequestDto entity);

    Collection<ParticipationRequestDto> toParticipationRequestDtos(Collection<ParticipationRequest> entities);


}
