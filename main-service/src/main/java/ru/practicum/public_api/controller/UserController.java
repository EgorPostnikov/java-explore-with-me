package ru.practicum.public_api.controller;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;


@Controller
@RequestMapping(path = "/users")
public class UserController {

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Object> createUser() {
        return null;
    }
}
