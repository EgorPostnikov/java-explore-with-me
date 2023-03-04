package ru.practicum.categories;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.practicum.event.*;

import javax.persistence.EntityNotFoundException;
import java.util.Collection;

@Service
@AllArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private static final Logger log = LoggerFactory.getLogger(CategoryServiceImpl.class);

    CategoryRepository repository;
    @Override
    public CategoryDto createCategory(NewCategoryDto requestDto){
        Category entity = CategoryMapper.INSTANCE.toCategory(requestDto);
        Category createdEntity = repository.save(entity);
        log.info("Category {} with id #{} saved",createdEntity.getName(), createdEntity.getId());
        return CategoryMapper.INSTANCE.toCategoryDto(createdEntity);
    };
    @Override
    public void deleteCategory(Integer catId){
        repository.deleteById(catId);
        log.info("Category with id #{} deleted",catId);
    }

    @Override
    public CategoryDto updateCategory(Integer catId, NewCategoryDto requestDto) {
        Category entity = CategoryMapper.INSTANCE.toCategory(requestDto);
        entity.setId(catId);
        Category updatedEntity = repository.save(entity);
        log.info("Category with id #{} updated", catId);
        return CategoryMapper.INSTANCE.toCategoryDto(updatedEntity);
    }
}
