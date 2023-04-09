package ru.practicum.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;

@Setter
@Getter
@AllArgsConstructor
@ToString
public class NewUserRequest {

    private String email;
    private String name;
}
