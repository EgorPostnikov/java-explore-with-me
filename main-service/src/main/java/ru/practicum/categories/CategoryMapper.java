package ru.practicum.categories;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.Collection;

@Mapper
public interface CategoryMapper {

    CategoryMapper INSTANCE = Mappers.getMapper(CategoryMapper.class);

    Category toCategory(NewCategoryDto entity);

    Category toCategory(CategoryDto entity);

    CategoryDto toCategoryDto(Category entity);

    Collection<CategoryDto> toCategoryDtos(Collection<Category> entities);

}
