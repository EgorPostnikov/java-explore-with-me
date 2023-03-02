package ru.practicum.admin_api.controller;

import org.springframework.data.domain.PageRequest;
import ru.practicum.admin_api.dto.UserDto;

import java.util.Collection;
import java.util.List;

public interface UserService {

    Collection<UserDto> getAllUsers(Collection<Integer> ids, PageRequest pageRequest);

    UserDto createUser(UserDto requestDto);

   void deleteUser(Integer userId);
}
