package ru.practicum.categories;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@AllArgsConstructor
@ToString
public class CategoryDto {
    private Integer id;
    private String name;

}
