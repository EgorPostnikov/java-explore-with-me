package ru.practicum.categories;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.Response;

import javax.validation.ValidationException;
import java.util.Collection;
import java.util.Collections;
import java.util.NoSuchElementException;


@RestController
@RequestMapping(path = "/categories")
@RequiredArgsConstructor
@Slf4j
@Validated
public class PublicCategoryController {
    private final CategoryService service;

    @GetMapping("/{catId}")
    @ResponseStatus(HttpStatus.CREATED)
    public CategoryDto getCategory(@PathVariable Integer catId) {
        log.info("Get category #{}", catId);
        return service.getCategory(catId);
    }

    @GetMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public Collection<CategoryDto> getCategories(@RequestParam(defaultValue = "0") Integer from,
                                                 @RequestParam(defaultValue = "10") Integer size) {
        log.info("Get categories");
        return Collections.emptyList();//service.getCategories();
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(NoSuchElementException.class)
    public Response handleException(NoSuchElementException exception) {
        return new Response(exception.getMessage());
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(ValidationException.class)
    public Response handleException(ValidationException exception) {
        return new Response(exception.getMessage());
    }

}
