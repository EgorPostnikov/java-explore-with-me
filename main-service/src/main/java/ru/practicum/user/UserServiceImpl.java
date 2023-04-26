package ru.practicum.user;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.validation.ValidationException;
import java.util.Collection;
import java.util.NoSuchElementException;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    private static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);
    private UserRepository userRepository;

    @Override
    public Collection<UserDto> getAllUsers(Collection<Integer> ids, PageRequest pageRequest) {
        Collection<User> users;
        if (ids == null) {
            users = userRepository.getAllBy(pageRequest);
        } else {
            users = userRepository.getUsersByIdIn(ids, pageRequest);
        }
        log.info("Users list found, users quantity is #{}", users.size());
        return UserMapper.INSTANCE.toUserDtos(users);
    }

    @Override
    public UserDto createUser(NewUserRequest userDto) {
        User user = UserMapper.INSTANCE.toUser(userDto);
        userValidation(user);
        User createdUser;
        createdUser = userRepository.save(user);
        log.info("User with id #{} saved", createdUser.getId());
        return UserMapper.INSTANCE.toUserDto(createdUser);
    }

    @Override
    public void deleteUser(Integer userId) {
        if (!userRepository.existsById(userId)) {
            throw new NoSuchElementException("User with id=" + userId + " was not found!");
        }
        userRepository.deleteById(userId);
        log.info("User with id #{} deleted", userId);

    }


    public Boolean userValidation(User user) {
        String name = user.getName();
        if (name == null) {
            throw new ValidationException("Field: name. Error: must not be null. Value: null");
        } else if (name.isBlank()) {
            throw new ValidationException("Field: name. Error: must not be blank. Value: null");
        } else if (userRepository.existsUserByName(user.getName())) {
            throw new SecurityException("User with name=" + user.getName() + " already exist");
        }
        return true;
    }


}

