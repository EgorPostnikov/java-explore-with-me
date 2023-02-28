package ru.practicum.admin_api.controller;

import org.springframework.stereotype.Service;
import ru.practicum.admin_api.dto.UserDto;

import java.util.Collection;
@Service
public class UserServiceImpl implements UserService{
    @Override
    public Collection<UserDto> getAllUsers() {
        return null;
    }

    @Override
    public UserDto createUser(UserDto requestDto) {
        return null;
    }

    @Override
    public UserDto deleteUser(long userId) {
        return null;
    }
}
