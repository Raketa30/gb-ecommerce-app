package ru.geekbrains.backend.security.domain.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "role_data", schema = "rest")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(exclude = {"id"})
public class RoleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;
}
