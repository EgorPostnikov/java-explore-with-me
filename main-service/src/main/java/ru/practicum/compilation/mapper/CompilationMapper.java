package ru.practicum.compilation.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import ru.practicum.compilation.dto.CompilationDto;
import ru.practicum.compilation.dto.NewCompilationDto;
import ru.practicum.compilation.dto.UpdateCompilationRequest;
import ru.practicum.compilation.model.Compilation;

import java.util.Collection;


@Mapper
public interface CompilationMapper {

    CompilationMapper INSTANCE = Mappers.getMapper(CompilationMapper.class);

    @Mapping(target = "pinned", source = "pinned", defaultValue = "false")
    @Mapping(target = "eventsId", source = "events")
    Compilation toCompilation(NewCompilationDto entity);

    @Mapping(target = "pinned", source = "pinned", defaultValue = "false")
    @Mapping(target = "eventsId", source = "events")
    Compilation toCompilation(UpdateCompilationRequest entity);

    CompilationDto toCompilationDto(Compilation entity);

    Collection<CompilationDto> toCompilationDtos(Collection<Compilation> entities);

}
