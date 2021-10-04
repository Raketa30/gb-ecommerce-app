package ru.geekbrains.backend.buisness.endpoints;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import ru.geekbrains.backend.buisness.service.CategoryService;

@Endpoint
public class CategoryEndpoint {
    private static final String NAMESPACE_URI = "http://gbshop.ru/soap/ws/category";
    private final CategoryService categoryService;

    @Autowired
    public CategoryEndpoint(CategoryService categoryService) {
        this.categoryService = categoryService;
    }


}
