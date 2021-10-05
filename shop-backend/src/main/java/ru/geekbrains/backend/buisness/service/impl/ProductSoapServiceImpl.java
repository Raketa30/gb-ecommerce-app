package ru.geekbrains.backend.buisness.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.geekbrains.backend.buisness.domain.entity.CategoryEntity;
import ru.geekbrains.backend.buisness.domain.entity.ProductEntity;
import ru.geekbrains.backend.buisness.repository.ProductRepository;
import ru.geekbrains.backend.buisness.service.ProductSoapService;
import ru.geekbrains.backend.buisness.soap.products.Product;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class ProductSoapServiceImpl implements ProductSoapService {
    private final ProductRepository productRepository;

    @Autowired
    public ProductSoapServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public static final Function<ProductEntity, Product> funcEntityToSoap = pe -> {
        Product pr = new Product();
        pr.setId(pe.getId());
        pr.setTitle(pe.getTitle());
        pr.setDescription(pe.getDescription());
        pr.setCost(pe.getCost());
        pr.setImageLink(pe.getImageLink());
        pr.getCategoryIdList().addAll(
                pe.getCategories()
                        .stream()
                        .map(CategoryEntity::getId)
                        .collect(Collectors.toList())
        );
        return pr;
    };


    @Override
    public Product getProductByTitle(String title) {
        return productRepository.findByTitle(title).map(funcEntityToSoap).get();
    }

    @Override
    public List<Product> findAll() {
        return productRepository.findAll()
                .stream()
                .map(funcEntityToSoap)
                .collect(Collectors.toList());
    }
}
