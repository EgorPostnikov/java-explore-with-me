package ru.practicum.categories.controller.admin;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.categories.dto.CategoryDto;
import ru.practicum.categories.dto.NewCategoryDto;
import ru.practicum.categories.service.CategoryService;

import javax.validation.ValidationException;


@RestController
@RequestMapping(path = "/admin/categories")
@RequiredArgsConstructor
@Slf4j
@Validated
public class AdminCategoryController {
    private final CategoryService service;

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public CategoryDto createCategory(@RequestBody NewCategoryDto requestDto) {
        log.info("Creating category {}", requestDto);
        return service.createCategory(requestDto);
    }

    @DeleteMapping("/{catId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCategory(@PathVariable Integer catId) {
        service.deleteCategory(catId);
    }

    @PatchMapping("/{catId}")
    @ResponseStatus(HttpStatus.OK)
    public CategoryDto updateCategory(@RequestBody CategoryDto requestDto,
                                      @PathVariable Integer catId) {
        if (catId == null) {
            throw new ValidationException("Field: id. Error: must not be null. Value: null");
        }
        log.info("Updating category {}", requestDto);
        return service.updateCategory(catId, requestDto);
    }


}
