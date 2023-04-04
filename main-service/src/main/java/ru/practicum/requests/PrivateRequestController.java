package ru.practicum.requests;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.Response;
import ru.practicum.categories.CategoryDto;
import ru.practicum.categories.CategoryService;
import ru.practicum.categories.NewCategoryDto;

import javax.validation.ValidationException;
import java.util.Collection;
import java.util.Collections;
import java.util.NoSuchElementException;


@RestController
@RequestMapping(path = "/users")
@RequiredArgsConstructor
@Slf4j
@Validated
public class PrivateRequestController {
    //private final CategoryService service;

    /*@PostMapping("/{userId}/requests")
    @ResponseStatus(HttpStatus.CREATED)
    public ParticipationRequestDto createRequest(@PathVariable Integer userId) {
        log.info("Creating Request from user {}", userId);
        return null;
    }*/

    @GetMapping("/{userId}/requests")
    @ResponseStatus(HttpStatus.OK)
    public Collection<ParticipationRequestDto> getRequest(@PathVariable Integer userId) {
        log.info("Get Request from user {}", userId);
        return Collections.emptyList();
    }
    @PatchMapping("/{userId}/requests/{requestId}/cancel")
    @ResponseStatus(HttpStatus.CREATED)
    public ParticipationRequestDto cancelRequest(@PathVariable Integer userId,
                                                 @PathVariable Integer requestId) {
        log.info("Cancelling Request # {} from user {}",requestId, userId);
        return new ParticipationRequestDto() ;
    }

    @PatchMapping("/{userId}/requests/{requestId}/requests")
    @ResponseStatus(HttpStatus.CREATED)
    public ParticipationRequestDto updateRequest(@PathVariable Integer userId,
                                                 @PathVariable Integer requestId) {
        log.info("Cancelling Request # {} from user {}",requestId, userId);
        return new ParticipationRequestDto() ;
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
