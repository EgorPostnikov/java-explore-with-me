package ru.practicum.mapper;

import org.mapstruct.Mapper;
import ru.practicum.dto.StatsDto;
import ru.practicum.model.Stats;

import java.util.Collection;

@Mapper(componentModel = "spring")
public interface StatsMapper {

    StatsDto toStatsDto(Stats entity);

    Collection<StatsDto> toStatsDtos(Collection<Stats> entities);
}
