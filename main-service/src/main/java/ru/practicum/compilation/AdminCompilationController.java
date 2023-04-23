package ru.practicum.compilation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.apiError.Response;
import ru.practicum.compilation.dto.CompilationDto;
import ru.practicum.compilation.dto.NewCompilationDto;
import ru.practicum.compilation.dto.UpdateCompilationRequest;

import javax.validation.Valid;
import javax.validation.ValidationException;
import java.util.NoSuchElementException;


@RestController
@RequestMapping(path = "/admin/compilations")
@RequiredArgsConstructor
@Slf4j
public class AdminCompilationController {
    private final CompilationService service;

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public CompilationDto createCompilation(@Valid @RequestBody NewCompilationDto requestDto) {
        log.info("Creating compilation {}", requestDto);
        return service.createCompilation(requestDto);
    }

    @DeleteMapping("/{compId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCompilation(@PathVariable Integer compId) {
        service.deleteCompilation(compId);
    }

    @PatchMapping("/{compId}")
    @ResponseStatus(HttpStatus.OK)
    public CompilationDto updateCompilation(@RequestBody UpdateCompilationRequest requestDto,
                                      @PathVariable Integer compId) {

        log.info("Updating category {}", requestDto);
        return service.updateCompilation(compId, requestDto);
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
