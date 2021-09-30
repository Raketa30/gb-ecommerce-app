package ru.geekbrains.backend.buisness.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "product", schema = "rest")
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {"categories"})
@EqualsAndHashCode(exclude = {"id", "categories"})
public class ProductEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title")
    @NotBlank(message = "Product title required")
    private String title;

    @Column(name = "description")
    @NotBlank(message = "Description required")
    private String description;

    @Column(name = "cost")
    @NotNull(message = "Product cost required")
    private Double cost;

    @Column(name = "img_link")
    private String imageLink;

    @JsonIgnore
    @ManyToMany
    @JoinTable(
            name = "product_category",
            joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id")
    )
    private Set<CategoryEntity> categories = new HashSet<>();

}
