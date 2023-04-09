package ru.practicum.user;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import org.springframework.validation.annotation.Validated;

import ru.practicum.apiError.ApiError;
import ru.practicum.apiError.Response;

import javax.validation.ValidationException;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.NoSuchElementException;

@RestController
@RequestMapping(path = "/admin/users")
@RequiredArgsConstructor
@Slf4j
@Validated
public class UserController {
    private final UserService service;
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserDto createUser(@RequestBody NewUserRequest requestDto) {
        log.info("Creating user {}", requestDto);
        return service.createUser(requestDto);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Collection<UserDto> getAllUsers(@RequestParam(required = false) Collection<Integer> ids,
                                           @RequestParam(defaultValue = "0") Integer from,
                                           @RequestParam(defaultValue = "10") Integer size) {
        PageRequest pageRequest = PageRequest.of(from, size, Sort.unsorted());
        log.info("Get all users from {},size {}, with ids{}", from, size,ids);
        return service.getAllUsers(ids, pageRequest);
    }



    @DeleteMapping("/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteUser(@PathVariable Integer userId) {
        log.info("Delete User, userId={}", userId);
        service.deleteUser(userId);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ValidationException.class)
    public ApiError handleException(ValidationException exception) {
        return new ApiError(exception.getMessage(),
                //exception.getCause().toString(),
                HttpStatus.BAD_REQUEST.toString(),
                LocalDateTime.now().toString()
        );
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