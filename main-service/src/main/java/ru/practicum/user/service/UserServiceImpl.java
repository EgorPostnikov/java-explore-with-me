package ru.practicum.user.service;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.practicum.user.dto.NewUserRequest;
import ru.practicum.user.dto.UserDto;
import ru.practicum.user.mapper.UserMapper;
import ru.practicum.user.model.User;
import ru.practicum.user.repository.UserRepository;

import javax.validation.ValidationException;
import java.util.Collection;
import java.util.NoSuchElementException;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    private static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);
    private final UserRepository userRepository;

    private final UserMapper userMapper;

    @Override
    public Collection<UserDto> getAllUsers(Collection<Integer> ids, PageRequest pageRequest) {
        Collection<User> users;
        if (ids == null) {
            users = userRepository.getAllBy(pageRequest);
        } else {
            users = userRepository.getUsersByIdIn(ids, pageRequest);
        }
        log.info("Users list found, users quantity is #{}", users.size());
        return userMapper.toUserDtos(users);
    }

    @Override
    public UserDto createUser(NewUserRequest userDto) {
        User user = userMapper.toUser(userDto);
        userValidation(user);
        User createdUser;
        createdUser = userRepository.save(user);
        log.info("User with id #{} saved", createdUser.getId());
        return userMapper.toUserDto(createdUser);
    }

    @Override
    public void deleteUser(Integer userId) {
        if (!userRepository.existsById(userId)) {
            throw new NoSuchElementException("User with id=" + userId + " was not found!");
        }
        userRepository.deleteById(userId);
        log.info("User with id #{} deleted", userId);

    }

    private void userValidation(User user) {
        String name = user.getName();
        if (name == null) {
            throw new ValidationException("Field: name. Error: must not be null. Value: null");
        } else if (name.isBlank()) {
            throw new ValidationException("Field: name. Error: must not be blank. Value: null");
        } else if (userRepository.existsUserByName(user.getName())) {
            throw new SecurityException("User with name=" + user.getName() + " already exist");
        }
    }


}

