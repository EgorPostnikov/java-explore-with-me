package ru.practicum.categories.mapper;

import org.mapstruct.Mapper;
import ru.practicum.categories.dto.CategoryDto;
import ru.practicum.categories.dto.NewCategoryDto;
import ru.practicum.categories.model.Category;

import java.util.Collection;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

    Category toCategory(NewCategoryDto entity);

    Category toCategory(CategoryDto entity);

    CategoryDto toCategoryDto(Category entity);

    Collection<CategoryDto> toCategoryDtos(Collection<Category> entities);

}
