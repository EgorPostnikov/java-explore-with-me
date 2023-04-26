package ru.practicum.categories.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import ru.practicum.categories.dto.CategoryDto;
import ru.practicum.categories.dto.NewCategoryDto;
import ru.practicum.categories.model.Category;

import java.util.Collection;

@Mapper
public interface CategoryMapper {

    CategoryMapper INSTANCE = Mappers.getMapper(CategoryMapper.class);

    Category toCategory(NewCategoryDto entity);

    Category toCategory(CategoryDto entity);

    CategoryDto toCategoryDto(Category entity);

    Collection<CategoryDto> toCategoryDtos(Collection<Category> entities);

}
