package ru.practicum.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import ru.practicum.dto.HitDto;
import ru.practicum.model.Hit;

@Mapper
public interface HitMapper {
    HitMapper INSTANCE = Mappers.getMapper(HitMapper.class);

    Hit toHit(HitDto entity);

    HitDto toHitDto(Hit entity);

}
