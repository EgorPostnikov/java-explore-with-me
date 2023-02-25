package ru.practicum.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import ru.practicum.dto.StatsDto;
import ru.practicum.model.Stats;

import java.util.Collection;

@Mapper
public interface StatsMapper {
    StatsMapper INSTANCE = Mappers.getMapper(StatsMapper.class);

    StatsDto toStatsDto(Stats entity);

    Collection<StatsDto> toStatsDtos(Collection<Stats> entities);
}
