package ru.geekbrains.backend.buisness.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.geekbrains.backend.buisness.domain.dto.CategoryDto;
import ru.geekbrains.backend.buisness.domain.entity.CategoryEntity;
import ru.geekbrains.backend.buisness.repository.CategoryRepository;
import ru.geekbrains.backend.buisness.service.CategoryService;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public List<CategoryEntity> findAllByIds(List<Long> categoryIdList) {
        return categoryRepository.findAllById(categoryIdList);
    }

    @Override
    public List<CategoryEntity> findAllByIdList(List<Long> categoryIdList) {
        return categoryRepository.findAllByIdInAndParentCategoryIsNotNull(categoryIdList);
    }

    @Override
    public List<CategoryDto> findAll() {
        List<CategoryEntity> categoryEntities = categoryRepository.findAll();

        return getCategoryDtoList(categoryEntities);
    }

    @Override
    @Transactional
    public CategoryEntity addCategory(CategoryDto categoryDTO) {
        return null;
    }

    @Override
    @Transactional
    public void updateCategory(CategoryDto dto) {
        Optional<CategoryEntity> optionalCategory = categoryRepository.findById(dto.getId());

        if (optionalCategory.isPresent()) {
            CategoryEntity entity = optionalCategory.get();
            entity.setTitle(dto.getTitle());
            entity.setAlias(dto.getAlias());

            CategoryEntity byAlias = categoryRepository.findByAlias(dto.getParentCategoryAlias());
            entity.setParentCategory(byAlias);

            List<CategoryEntity> allById = categoryRepository.findAllById(dto.getSubCategories());
            entity.setSubCategories(new HashSet<>(allById));
        }
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        categoryRepository.deleteById(id);
    }

    private List<CategoryDto> getCategoryDtoList(List<CategoryEntity> categoryEntities) {
        return categoryEntities.stream()
                .map(this::getDto)
                .collect(Collectors.toList());
    }

    private CategoryDto getDto(CategoryEntity entity) {
        return CategoryDto.builder()
                .id(entity.getId())
                .title(entity.getTitle())
                .alias(entity.getAlias())
                .parentCategoryAlias(entity.getParentCategory().getAlias())
                .build();
    }

    private CategoryEntity getEntity(CategoryDto dto) {
        return CategoryEntity.builder()
                .id(dto.getId())
                .title(dto.getTitle())
                .alias(dto.getAlias())
                .parentCategory(categoryRepository.findByAlias(dto.getParentCategoryAlias()))
                .build();
    }
}
