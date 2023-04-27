package ru.practicum.user.service;

import org.springframework.data.domain.PageRequest;
import ru.practicum.user.dto.NewUserRequest;
import ru.practicum.user.dto.UserDto;

import java.util.Collection;

public interface UserService {

    Collection<UserDto> getAllUsers(Collection<Integer> ids, PageRequest pageRequest);

    UserDto createUser(NewUserRequest requestDto);

    void deleteUser(Integer userId);
}
