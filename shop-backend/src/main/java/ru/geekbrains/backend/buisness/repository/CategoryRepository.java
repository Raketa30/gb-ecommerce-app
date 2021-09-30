package ru.geekbrains.backend.buisness.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.geekbrains.backend.buisness.domain.entity.CategoryEntity;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<CategoryEntity, Long> {
    List<CategoryEntity> findAllByIdInAndParentCategoryIsNotNull(List<Long> categoryIdList);

    CategoryEntity findByAlias(String parentCategoryAlias);

}
