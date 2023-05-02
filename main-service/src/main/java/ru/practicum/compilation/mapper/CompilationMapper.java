package ru.practicum.compilation.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.practicum.compilation.dto.CompilationDto;
import ru.practicum.compilation.dto.NewCompilationDto;
import ru.practicum.compilation.dto.UpdateCompilationRequest;
import ru.practicum.compilation.model.Compilation;

@Mapper(componentModel = "spring")
public interface CompilationMapper {

    @Mapping(target = "pinned", source = "pinned", defaultValue = "false")
    @Mapping(target = "eventsId", source = "events")
    Compilation toCompilation(NewCompilationDto entity);

    @Mapping(target = "pinned", source = "pinned", defaultValue = "false")
    @Mapping(target = "eventsId", source = "events")
    Compilation toCompilation(UpdateCompilationRequest entity);

    CompilationDto toCompilationDto(Compilation entity);

}
