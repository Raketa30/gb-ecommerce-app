package ru.geekbrains.backend.buisness.endpoints;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;
import ru.geekbrains.backend.buisness.service.CategorySoapService;
import ru.geekbrains.backend.buisness.soap.categories.GetCategoryByTitleRequest;
import ru.geekbrains.backend.buisness.soap.categories.GetCategoryByTitleResponse;

@Endpoint
public class CategoryEndpoint {
    private static final String NAMESPACE_URI = "http://gbshop.ru/soap/ws/category";
    private final CategorySoapService categoryService;

    @Autowired
    public CategoryEndpoint(CategorySoapService categoryService) {
        this.categoryService = categoryService;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getCategoryByTitleRequest")
    @ResponsePayload
    @Transactional
    public GetCategoryByTitleResponse getCategoryByTitle(@RequestPayload GetCategoryByTitleRequest request) {
        GetCategoryByTitleResponse response = new GetCategoryByTitleResponse();
        response.setCategory(categoryService.findCategoryByTitle(request.getTitle()));
        return response;
    }
}
