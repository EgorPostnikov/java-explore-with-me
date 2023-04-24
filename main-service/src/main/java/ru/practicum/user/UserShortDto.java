package ru.practicum.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Setter
@Getter
@AllArgsConstructor
@ToString
public class UserShortDto {
    private Integer id;
    private String name;
}

