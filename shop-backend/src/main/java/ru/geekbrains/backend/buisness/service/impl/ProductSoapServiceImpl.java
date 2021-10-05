package ru.geekbrains.backend.buisness.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.geekbrains.backend.buisness.repository.ProductRepository;
import ru.geekbrains.backend.buisness.service.ProductSoapService;

@Service
public class ProductSoapServiceImpl implements ProductSoapService {
    private final ProductRepository productRepository;

    @Autowired
    public ProductSoapServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    
}
