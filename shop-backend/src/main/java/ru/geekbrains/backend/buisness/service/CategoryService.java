package ru.geekbrains.backend.buisness.service;


import ru.geekbrains.backend.buisness.domain.dto.CategoryDto;
import ru.geekbrains.backend.buisness.domain.entity.CategoryEntity;

import java.util.List;

public interface CategoryService {
    List<CategoryEntity> findAllByIds(List<Long> categoryIdList);

    List<CategoryEntity> findAllByIdList(List<Long> categoryIdList);

    List<CategoryDto> findAll();

    CategoryEntity addCategory(CategoryDto categoryDTO);

    void updateCategory(CategoryDto categoryDto);

    void deleteById(Long id);
}
