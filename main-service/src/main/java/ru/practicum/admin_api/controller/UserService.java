package ru.practicum.admin_api.controller;

import ru.practicum.admin_api.dto.UserDto;

import java.util.Collection;

public interface UserService {

    Collection<UserDto> getAllUsers();

    UserDto createUser(UserDto requestDto);

    UserDto deleteUser(long userId);
}
