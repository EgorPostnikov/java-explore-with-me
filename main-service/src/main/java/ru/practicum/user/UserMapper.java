package ru.practicum.user;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.Collection;

@Mapper
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    UserDto toUserDto(User user);

    User toUser(NewUserRequest userDto);

    UserShortDto toUserShortDto(User user);

    Collection<UserDto> toUserDtos(Collection<User> users);

}
