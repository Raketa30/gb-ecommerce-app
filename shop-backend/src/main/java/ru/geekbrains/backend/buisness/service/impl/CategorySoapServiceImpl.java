package ru.geekbrains.backend.buisness.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.geekbrains.backend.buisness.domain.entity.CategoryEntity;
import ru.geekbrains.backend.buisness.repository.CategoryRepository;
import ru.geekbrains.backend.buisness.service.CategorySoapService;
import ru.geekbrains.backend.buisness.soap.categories.Category;

import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class CategorySoapServiceImpl implements CategorySoapService {
    public static final Function<CategoryEntity, Category> funcFromDtoToSoap = ce -> {

        Category category = new Category();
        category.setId(ce.getId());
        category.setTitle(ce.getTitle());
        category.setAlias(ce.getAlias());
        category.setParentCategoryAlias(ce.getParentCategory().getTitle());
        category.getSubCategories().addAll(ce.getSubCategories().stream().map(CategoryEntity::getId).collect(Collectors.toList()));

        return category;
    };
    private final CategoryRepository categoryRepository;

    @Autowired
    public CategorySoapServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Category findCategoryByTitle(String title) {
        return categoryRepository.findByTitle(title).map(funcFromDtoToSoap).get();
    }
}
