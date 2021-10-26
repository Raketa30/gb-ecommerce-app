package ru.geekbrains.backend.buisness.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.geekbrains.backend.buisness.domain.entity.CategoryEntity;
import ru.geekbrains.backend.buisness.domain.entity.ProductEntity;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, Long> {
    Page<ProductEntity> findProductEntityByTitleContainsAndCostBetween(Pageable pageRequest, String title, Double min, Double max);

    List<ProductEntity> findProductEntitiesByCategoriesEquals(CategoryEntity category);
}
