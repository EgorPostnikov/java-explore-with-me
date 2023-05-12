package ru.practicum.compilation.controller.pub;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.compilation.dto.CompilationDto;
import ru.practicum.compilation.service.CompilationService;

import java.util.Collection;


@RestController
@RequestMapping(path = "/compilations")
@RequiredArgsConstructor
@Slf4j
@Validated
public class PublicCompilationController {
    private final CompilationService service;

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public Collection<CompilationDto> getCompilations(@RequestParam(defaultValue = "true") Boolean pinned,
                                                      @RequestParam(defaultValue = "0") Integer from,
                                                      @RequestParam(defaultValue = "10") Integer size) {
        PageRequest pageRequest = PageRequest.of(from, size, Sort.unsorted());
        log.info("Get compilations with pinned = {}", pinned);
        return service.getCompilations(pinned, pageRequest);
    }

    @GetMapping("/{compId}")
    @ResponseStatus(HttpStatus.OK)
    public CompilationDto getCompilation(@PathVariable Integer compId) {
        log.info("Get compilation with Id {}", compId);
        return service.getCompilation(compId);
    }

}
