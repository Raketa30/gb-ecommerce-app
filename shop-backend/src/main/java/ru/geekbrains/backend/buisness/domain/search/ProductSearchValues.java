package ru.geekbrains.backend.buisness.domain.search;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Sort;

import java.util.Collections;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductSearchValues {
    private Sort.Direction sortDirection = Sort.Direction.ASC;
    private String sortField = "id";

    private int pageNum = 1;
    private Integer pageSize = 9;
    private List<Integer> pageNumbers = Collections.singletonList(1);

    //filters
    private List<Long> categoryIds;
    private Double minCost;
    private Double maxCost;
}
