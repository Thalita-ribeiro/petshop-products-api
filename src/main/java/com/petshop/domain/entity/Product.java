package com.petshop.domain.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    private String name;
    @ManyToOne
    private Category category;
    @NotNull
    private double value;
    @NotNull
    private int quantity;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
