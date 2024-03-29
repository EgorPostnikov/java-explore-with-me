package ru.practicum.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.practicum.dto.HitDto;
import ru.practicum.model.Hit;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Mapper(componentModel = "spring")
public interface HitMapper {

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @WithStringToLocalDate
    default LocalDateTime stringToLocalDate(String source) {
        return LocalDateTime.now();
    }

    @WithLocalDateToString
    default String localDateToString(LocalDateTime source) {
        return source.format(formatter);
    }

    @Mapping(target = "timestamp", source = "timestamp", qualifiedBy = WithStringToLocalDate.class)
    Hit toHit(HitDto entity);

    @Mapping(target = "timestamp", source = "timestamp", qualifiedBy = WithLocalDateToString.class)
    HitDto toHitDto(Hit entity);

}
