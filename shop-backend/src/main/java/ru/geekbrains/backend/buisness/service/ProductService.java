package ru.geekbrains.backend.buisness.service;

import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;
import ru.geekbrains.backend.buisness.domain.dto.ProductDto;
import ru.geekbrains.backend.buisness.domain.entity.ProductEntity;
import ru.geekbrains.backend.buisness.domain.search.ProductSearchValues;

import java.util.List;

public interface ProductService {
    List<ProductDto> findAll();

    ProductEntity saveWithImage(ProductDto productDto, MultipartFile image);

    void updateWithImage(ProductDto productDto, MultipartFile image);

    void deleteById(Long id);

    Page<ProductDto> findAllPaginated(ProductSearchValues searchValues);

    List<ProductDto> findProductsByCategoryId(Long id);
}
