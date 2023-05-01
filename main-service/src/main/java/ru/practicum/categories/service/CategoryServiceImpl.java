package ru.practicum.categories.service;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.practicum.categories.mapper.CategoryMapper;
import ru.practicum.categories.dto.CategoryDto;
import ru.practicum.categories.dto.NewCategoryDto;
import ru.practicum.categories.model.Category;
import ru.practicum.categories.repository.CategoryRepository;
import ru.practicum.event.repository.EventRepository;

import javax.validation.ValidationException;
import java.util.Collection;
import java.util.NoSuchElementException;

@Service
@AllArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private static final Logger log = LoggerFactory.getLogger(CategoryServiceImpl.class);
    CategoryRepository repository;
    EventRepository eventRepository;

    @Override
    public CategoryDto createCategory(NewCategoryDto requestDto) {
        Category entity = CategoryMapper.INSTANCE.toCategory(requestDto);
        categoryValidation(entity);
        Category createdEntity = repository.save(entity);
        log.info("Category {} with id #{} saved", createdEntity.getName(), createdEntity.getId());
        return CategoryMapper.INSTANCE.toCategoryDto(createdEntity);
    }

    @Override
    public CategoryDto updateCategory(Integer catId, CategoryDto requestDto) {

        Category entity = CategoryMapper.INSTANCE.toCategory(requestDto);
        entity.setId(catId);
        isCategoryExist(catId);
        categoryValidation(entity);
        Category updatedEntity = repository.save(entity);
        log.info("Category with id #{} updated", catId);
        return CategoryMapper.INSTANCE.toCategoryDto(updatedEntity);
    }

    @Override
    public void deleteCategory(Integer catId) {
        isCategoryExist(catId);
        isCategoryFree(catId);
        repository.deleteById(catId);
        log.info("Category with id #{} deleted", catId);
    }

    @Override
    public CategoryDto getCategory(Integer catId) {
        isCategoryExist(catId);
        Category category = repository.findById(catId).
                orElseThrow(() -> new NoSuchElementException("Category was not found"));
        log.info("Category with id #{} got", catId);
        return CategoryMapper.INSTANCE.toCategoryDto(category);
    }

    @Override
    public Collection<CategoryDto> getCategories(PageRequest pageRequest) {
        Collection<Category> categories = repository.findAllBy(pageRequest);
        log.info("Categories got, qty={}", categories.size());
        return CategoryMapper.INSTANCE.toCategoryDtos(categories);
    }

    void isCategoryExist(Integer catId) {
        if (!repository.existsById(catId)) {
            throw new NoSuchElementException("Category with id=" + catId + " was not found");
        }
    }


    void categoryValidation(Category category) {
        String name = category.getName();
        if (name == null) {
            throw new ValidationException("Field: name. Error: must not be null. Value: null");
        } else if (name.isBlank()) {
            throw new ValidationException("Field: name. Error: must not be blank. Value: null");
        } else if (repository.existsCategoryByNameIs(category.getName())) {
            throw new SecurityException("Category with name=" + category.getName() + " already exist");
        }
    }

    void isCategoryFree(Integer id) {
        if (eventRepository.existsEventsByCategoryIdIs(id)) {
            throw new SecurityException("Category with id=" + id + " have links");
        }
    }
}
