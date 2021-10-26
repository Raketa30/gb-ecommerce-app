package ru.geekbrains.backend.buisness.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import ru.geekbrains.backend.buisness.domain.dto.ProductDto;
import ru.geekbrains.backend.buisness.domain.entity.CategoryEntity;
import ru.geekbrains.backend.buisness.domain.entity.ProductEntity;
import ru.geekbrains.backend.buisness.domain.search.ProductSearchValues;
import ru.geekbrains.backend.buisness.repository.ProductRepository;
import ru.geekbrains.backend.buisness.service.CategoryService;
import ru.geekbrains.backend.buisness.service.ProductService;
import ru.geekbrains.backend.buisness.util.FileUtils;

import java.nio.file.Path;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final CategoryService categoryService;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository, CategoryService categoryService) {
        this.productRepository = productRepository;
        this.categoryService = categoryService;
    }

    @Override
    public List<ProductDto> findAll() {
        List<ProductEntity> productEntities = productRepository.findAll();
        return getProductDtoList(productEntities);
    }

    @Override
    @Transactional
    public ProductEntity saveWithImage(ProductDto productDto, MultipartFile image) {
        ProductEntity product = getEntity(productDto);
        ProductEntity savedProduct = productRepository.save(product);
        if (image != null && !image.isEmpty()) {
            Path pathImage = FileUtils.saveProductImage(image);
            savedProduct.setImageLink(pathImage.toString());
            productRepository.save(savedProduct);
        }
        return savedProduct;
    }

    @Override
    @Transactional
    public void updateWithImage(ProductDto productDto, MultipartFile image) {
        ProductEntity product = productRepository.getById(productDto.getId());
        updateProduct(product, productDto);
        if (image != null && !image.isEmpty()) {
            Path pathImage = FileUtils.saveProductImage(image);
            product.setImageLink(pathImage.toString());
            productRepository.save(product);
        }
    }

    private void updateProduct(ProductEntity product, ProductDto dto) {
        product.setTitle(dto.getTitle());
        product.setDescription(dto.getDescription());
        product.setCost(dto.getCost());

        Set<CategoryEntity> categoryList = categoryService.findAllByIdList(dto.getCategoryIdList());
        product.setCategories(categoryList);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        productRepository.deleteById(id);
    }

    @Override
    public Page<ProductDto> findAllPaginated(ProductSearchValues searchValues) {
        Pageable pageRequest = PageRequest.of(
                searchValues.getPageNum() - 1,
                searchValues.getPageSize(),
                Sort.by(searchValues.getSortDirection(), searchValues.getSortField())
        );
        Page<ProductEntity> productEntityPage;
        if (searchValues.isFiltered()) {
            productEntityPage = productRepository
                    .findProductEntityByTitleContainsAndCostBetween(
                            pageRequest,
                            searchValues.getTitle(),
                            searchValues.getMinCost(),
                            searchValues.getMaxCost());
        } else {
            productEntityPage = productRepository.findAll(pageRequest);
        }
        return productEntityPage.map(this::getDto);
    }

    @Override
    public List<ProductDto> findProductsByCategoryId(Long id) {
        return getProductDtoList(productRepository
                .findProductEntitiesByCategoriesEquals(categoryService.findCategoryById(id)));
    }

    private List<ProductDto> getProductDtoList(List<ProductEntity> productEntities) {
        return productEntities.stream()
                .map(this::getDto)
                .collect(Collectors.toList());
    }

    private ProductEntity getEntity(ProductDto dto) {
        List<CategoryEntity> entities = categoryService.findAllByIds(dto.getCategoryIdList());
        return ProductEntity.builder()
                .title(dto.getTitle())
                .description(dto.getDescription())
                .cost(dto.getCost())
                .imageLink(dto.getImageLink())
                .categories(new HashSet<>(entities))
                .build();
    }

    private ProductDto getDto(ProductEntity entity) {
        List<Long> categoryIdList = getGategoryIdList(entity);
        return ProductDto.builder()
                .id(entity.getId())
                .title(entity.getTitle())
                .description(entity.getDescription())
                .cost(entity.getCost())
                .imageLink(entity.getImageLink())
                .categoryIdList(categoryIdList)
                .build();
    }

    private List<Long> getGategoryIdList(ProductEntity entity) {
        return entity.getCategories().stream()
                .map(CategoryEntity::getId)
                .collect(Collectors.toList());
    }
}
