package ru.geekbrains.backend.buisness.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.geekbrains.backend.buisness.domain.entity.CategoryEntity;

import java.util.List;
import java.util.Set;

@Repository
public interface CategoryRepository extends JpaRepository<CategoryEntity, Long> {
    Set<CategoryEntity> findAllByIdInAndParentCategoryIsNotNull(List<Long> categoryIdList);

    List<CategoryEntity> findCategoryEntitiesByParentCategoryIsNotNull();

    CategoryEntity findByAlias(String parentCategoryAlias);

}
