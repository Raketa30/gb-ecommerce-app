package ru.geekbrains.backend.buisness.service;

import ru.geekbrains.backend.buisness.soap.categories.Category;

public interface CategorySoapService {
    Category findCategoryByTitle(String title);
}
