package ru.practicum.compilation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.apiError.Response;
import ru.practicum.compilation.dto.CompilationDto;
import ru.practicum.compilation.dto.NewCompilationDto;
import ru.practicum.compilation.dto.UpdateCompilationRequest;

import javax.validation.ValidationException;
import java.util.Collection;
import java.util.NoSuchElementException;


@RestController
@RequestMapping(path = "/compilations")
@RequiredArgsConstructor
@Slf4j
@Validated
public class PublicCompilationController {
    private final CompilationService service;

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    //Получение подборок событий
    public Collection<CompilationDto> getCompilations( @RequestParam(defaultValue = "true") Boolean pinned,
                                                       @RequestParam(defaultValue = "0") Integer from,
                                                       @RequestParam(defaultValue = "10") Integer size){
        PageRequest pageRequest = PageRequest.of(from, size, Sort.unsorted());
        log.info("Get compilations with pinned = {}", pinned);
        return service.getCompilations(pinned,pageRequest);
    }
    @GetMapping("/{compId}")
    @ResponseStatus(HttpStatus.OK)
    //Получение подборки событий по его айди
    public CompilationDto getCompilation(@PathVariable Integer compId) {
        log.info("Get compilation with Id {}", compId);
        return service.getCompilation(compId);
    }


    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ValidationException.class)
    public Response handleException(ValidationException exception) {
        return new Response(exception.getMessage());
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(SecurityException.class)
    public Response handleException(RuntimeException exception) {
        return new Response(exception.getMessage());
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NoSuchElementException.class)
    public Response handleException(NoSuchElementException exception) {
        return new Response(exception.getMessage());
    }
}
