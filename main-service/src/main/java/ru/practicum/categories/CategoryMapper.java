package ru.practicum.categories;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import ru.practicum.event.Event;
import ru.practicum.event.EventFullDto;
import ru.practicum.event.EventShortDto;
import ru.practicum.event.NewEventDto;

import java.util.Collection;

@Mapper
public interface CategoryMapper {

    CategoryMapper INSTANCE = Mappers.getMapper(CategoryMapper.class);

    Category toCategory(NewCategoryDto entity);
    CategoryDto toCategoryDto(Category entity);
    Collection<CategoryDto> toCategoryDtos (Collection<Category> entities);

}
