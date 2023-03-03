package ru.practicum.user;

import org.springframework.data.domain.PageRequest;

import java.util.Collection;

public interface UserService {

    Collection<UserDto> getAllUsers(Collection<Integer> ids, PageRequest pageRequest);

    UserDto createUser(UserDto requestDto);

   void deleteUser(Integer userId);
}
