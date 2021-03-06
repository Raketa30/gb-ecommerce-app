package ru.geekbrains.backend.buisness.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.geekbrains.backend.buisness.domain.dto.ProductDto;
import ru.geekbrains.backend.buisness.domain.entity.ProductEntity;
import ru.geekbrains.backend.buisness.domain.search.ProductSearchValues;
import ru.geekbrains.backend.buisness.service.ProductService;

import java.util.List;

import static ru.geekbrains.backend.constants.NameConstant.PRODUCT;

@RestController
@RequestMapping(PRODUCT)
public class ProductController {
    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping("/list")
    public ResponseEntity<Page<ProductDto>> getProductList(@RequestBody ProductSearchValues searchValues) {
        Page<ProductDto> page = productService.findAllPaginated(searchValues);
        return new ResponseEntity<>(page, HttpStatus.OK);
    }

    @PostMapping("/category-id")
    public ResponseEntity<List<ProductDto>> getCategoryProductList(@RequestBody String id) {
        return ResponseEntity.ok(productService.findProductsByCategoryId(Long.valueOf(id)));
    }

    @GetMapping("/all")
    public ResponseEntity<List<ProductDto>> findAll() {
        return ResponseEntity.ok(productService.findAll());
    }

    @PutMapping("/add")
    public ResponseEntity<ProductEntity> addProduct(@RequestPart ProductDto productDto,
                                                    @RequestPart(required = false) MultipartFile image) {
        if (productDto.getId() != null && productDto.getId() != 0) {
            return new ResponseEntity("Id must be null", HttpStatus.NOT_ACCEPTABLE);
        }

        return ResponseEntity.ok(productService.saveWithImage(productDto, image));
    }

    @PatchMapping("/update")
    public ResponseEntity<Void> updateProduct(@RequestBody ProductDto productDto,
                                              @RequestParam(required = false) MultipartFile image) {
        if (productDto.getId() == null || productDto.getId() == 0) {
            return new ResponseEntity("missed param id", HttpStatus.NOT_ACCEPTABLE);
        }
        productService.updateWithImage(productDto, image);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Void> deleteProduct(@RequestBody Long id) {
        try {
            productService.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            e.printStackTrace();
            return new ResponseEntity("id = " + id + " not found", HttpStatus.NOT_ACCEPTABLE);
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
