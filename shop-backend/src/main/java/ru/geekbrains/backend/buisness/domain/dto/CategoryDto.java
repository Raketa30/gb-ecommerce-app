package ru.geekbrains.backend.buisness.domain.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDto {
    private Long id;
    private String title;
    private String alias;
    private String parentCategoryAlias;
    private List<Long> subCategories;
}
