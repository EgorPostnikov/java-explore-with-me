package ru.practicum.admin_api.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import ru.practicum.admin_api.dto.UserDto;
import ru.practicum.admin_api.model.User;


import java.util.Collection;

@Mapper
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    UserDto toUserDto(User user);

    User toUser(UserDto userDto);

    Collection<UserDto> toUserDtos(Collection<User> users);
}
