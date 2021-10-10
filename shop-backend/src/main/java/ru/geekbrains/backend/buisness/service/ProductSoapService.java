package ru.geekbrains.backend.buisness.service;


import ru.geekbrains.backend.buisness.soap.products.Product;

import java.util.List;

public interface ProductSoapService {
    Product getProductByTitle(String title);

    List<Product> findAll();
}
