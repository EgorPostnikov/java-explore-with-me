package ru.practicum.compilation;

import org.springframework.data.domain.PageRequest;
import ru.practicum.compilation.dto.CompilationDto;
import ru.practicum.compilation.dto.NewCompilationDto;
import ru.practicum.compilation.dto.UpdateCompilationRequest;

import java.util.Collection;

public interface CompilationService {

    CompilationDto createCompilation(NewCompilationDto requestDto);

    void deleteCompilation(Integer compId);

    CompilationDto updateCompilation(Integer compId, UpdateCompilationRequest requestDto);

    Collection<CompilationDto> getCompilations(Boolean pinned, PageRequest pageRequest);

    CompilationDto getCompilation(Integer compId);
}
