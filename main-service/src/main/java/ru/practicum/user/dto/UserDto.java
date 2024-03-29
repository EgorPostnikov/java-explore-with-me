package ru.practicum.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Setter
@Getter
@AllArgsConstructor
@ToString
public class UserDto {
    @NotNull(message = "Email is required.")
    @Email(message = "Invalid email.")
    private String email;
    private Integer id;
    @NotNull(message = "Name is required.")
    private String name;

}

