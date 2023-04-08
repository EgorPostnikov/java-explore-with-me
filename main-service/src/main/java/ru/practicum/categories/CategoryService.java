package ru.practicum.categories;

import org.springframework.data.domain.PageRequest;

import java.util.Collection;

public interface CategoryService {
    CategoryDto createCategory(NewCategoryDto requestDto);

    void deleteCategory(Integer catId);

    CategoryDto updateCategory(Integer catId,CategoryDto requestDto);

    CategoryDto getCategory(Integer catId);

    Collection<CategoryDto> getCategories(PageRequest pageRequest);
}
