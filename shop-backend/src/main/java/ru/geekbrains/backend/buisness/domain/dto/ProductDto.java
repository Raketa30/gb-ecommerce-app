package ru.geekbrains.backend.buisness.domain.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductDto {
    private Long id;
    private String title;
    private String description;
    private Double cost;
    private String imageLink;
    private List<Long> categoryIdList;
}
