package ru.practicum.categories.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@AllArgsConstructor
@ToString
public class NewCategoryDto {
    private Integer id;
    private String name;

}