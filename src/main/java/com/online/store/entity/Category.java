package com.online.store.entity;

import com.online.store.entity.status.CategoryStatus;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "categories")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @Enumerated(EnumType.STRING)
    private CategoryStatus status;
    @OneToMany(mappedBy = "category")
    private List<Product> products = new ArrayList<>();
}
