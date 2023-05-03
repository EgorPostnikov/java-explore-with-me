package ru.practicum.categories.service;

import org.springframework.data.domain.PageRequest;
import ru.practicum.categories.dto.CategoryDto;
import ru.practicum.categories.dto.NewCategoryDto;

import java.util.Collection;

public interface CategoryService {
    CategoryDto createCategory(NewCategoryDto requestDto);

    void deleteCategory(Integer catId);

    CategoryDto updateCategory(Integer catId, CategoryDto requestDto);

    CategoryDto getCategory(Integer catId);

    Collection<CategoryDto> getCategories(PageRequest pageRequest);
}
