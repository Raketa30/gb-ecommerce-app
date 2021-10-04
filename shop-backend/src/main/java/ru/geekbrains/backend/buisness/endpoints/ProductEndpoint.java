package ru.geekbrains.backend.buisness.endpoints;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import ru.geekbrains.backend.buisness.service.ProductService;

@Endpoint
public class ProductEndpoint {
    private static final String NAMESPACE_URI = "http://gbshop.ru/soap/ws/products";
    private final ProductService productService;

    @Autowired
    public ProductEndpoint(ProductService productService) {
        this.productService = productService;
    }
}
