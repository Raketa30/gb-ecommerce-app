package ru.geekbrains.backend.buisness.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.geekbrains.backend.buisness.domain.dto.CategoryDto;
import ru.geekbrains.backend.buisness.domain.entity.CategoryEntity;
import ru.geekbrains.backend.buisness.service.CategoryService;

import java.util.List;

import static ru.geekbrains.backend.constants.NameConstant.CATEGORY;


@RestController
@RequestMapping(CATEGORY)
public class CategoryController {
    private final CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/list")
    public ResponseEntity<List<CategoryDto>> getCategoryListPage() {
        List<CategoryDto> categoryEntityList = categoryService.findAll();
        System.out.println(categoryEntityList);
        return ResponseEntity.ok(categoryEntityList);
    }

    @PutMapping("/add")
    public ResponseEntity<CategoryEntity> addCategory(CategoryDto categoryDto) {
        if (categoryDto.getId() != null && categoryDto.getId() != 0) {
            return new ResponseEntity("Id must be null", HttpStatus.NOT_ACCEPTABLE);
        }

        return ResponseEntity.ok(categoryService.addCategory(categoryDto));
    }

    @PatchMapping("/update")
    public ResponseEntity<Void> updateProduct(@RequestBody CategoryDto categoryDto) {
        if (categoryDto.getId() == null || categoryDto.getId() == 0) {
            return new ResponseEntity("missed param id", HttpStatus.NOT_ACCEPTABLE);
        }
        categoryService.updateCategory(categoryDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Void> deleteProduct(@RequestBody Long id) {
        try {
            categoryService.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            e.printStackTrace();
            return new ResponseEntity("Category id = " + id + " not found", HttpStatus.NOT_ACCEPTABLE);
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
