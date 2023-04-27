package ru.practicum.compilation.service;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.practicum.compilation.dto.CompilationDto;
import ru.practicum.compilation.dto.NewCompilationDto;
import ru.practicum.compilation.dto.UpdateCompilationRequest;
import ru.practicum.compilation.mapper.CompilationMapper;
import ru.practicum.compilation.model.Compilation;
import ru.practicum.compilation.repository.CompilationRepository;
import ru.practicum.event.dto.EventShortDto;
import ru.practicum.event.service.EventServiceImpl;

import java.util.*;

@Service
@AllArgsConstructor
public class CompilationServiceImpl implements CompilationService {
    private static final Logger log = LoggerFactory.getLogger(CompilationServiceImpl.class);
    CompilationRepository repository;
    EventServiceImpl eventService;


    @Override
    public CompilationDto createCompilation(NewCompilationDto requestDto) {
        Compilation entity = CompilationMapper.INSTANCE.toCompilation(requestDto);
        Compilation createdEntity = repository.save(entity);
        log.info("Compilation {} with id #{} saved", createdEntity.getTitle(), createdEntity.getId());
        return convertCompilationToDto(createdEntity);
    }

    @Override
    public void deleteCompilation(Integer compId) {
        isCompilationExist(compId);
        repository.deleteById(compId);
        log.info("Compilation with id #{} deleted", compId);

    }

    void isCompilationExist(Integer compId) {
        if (!repository.existsById(compId)) {
            throw new NoSuchElementException("Compilation with id=" + compId + " was not found");
        }
    }

    @Override
    public CompilationDto updateCompilation(Integer compId, UpdateCompilationRequest requestDto) {

        Compilation newEntity = CompilationMapper.INSTANCE.toCompilation(requestDto);
        isCompilationExist(compId);
        Compilation oldEntity = repository.findById(compId).
                orElseThrow(() -> new NoSuchElementException("Compilation was not found"));

        if (!(newEntity.getTitle() == null)) {
            oldEntity.setTitle(newEntity.getTitle());
        }
        if (!(newEntity.getPinned() == null)) {
            oldEntity.setPinned(newEntity.getPinned());
        }
        if (!(newEntity.getEventsId() == null)) {
            oldEntity.setEventsId(newEntity.getEventsId());
        }
        Compilation updatedEntity = repository.save(newEntity);
        log.info("Compilation with id #{} updated", compId);

        return convertCompilationToDto(updatedEntity);
    }

    @Override
    public Collection<CompilationDto> getCompilations(Boolean pinned, PageRequest pageRequest) {
        Collection<Compilation> entities = repository.getCompilationsByPinnedIs(pinned, pageRequest);
        if (entities.isEmpty()) {
            return Collections.emptyList();
        }
        Collection<CompilationDto> compilation = new ArrayList<>();
        for (Compilation comp : entities) {
            compilation.add(convertCompilationToDto(comp));
        }
        return compilation;
    }

    @Override
    public CompilationDto getCompilation(Integer compId) {
        Compilation compilation = repository.findById(compId).
                orElseThrow(() -> new NoSuchElementException("Compilation with id=" + compId + " was not found"));
        return convertCompilationToDto(compilation);
    }

    public CompilationDto convertCompilationToDto(Compilation compilation) {
        List<EventShortDto> events = eventService.getShortEvents(compilation.getEventsId());
        CompilationDto compilationDto = CompilationMapper.INSTANCE.toCompilationDto(compilation);
        compilationDto.setEvents(events);
        return compilationDto;
    }
}
