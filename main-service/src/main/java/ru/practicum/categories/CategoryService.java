package ru.practicum.categories;

public interface CategoryService {
    CategoryDto createCategory(NewCategoryDto requestDto);

    void deleteCategory(Integer catId);

    CategoryDto updateCategory(Integer catId,NewCategoryDto requestDto);

    CategoryDto getCategory(Integer catId);
}
