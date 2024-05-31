package com.petshop.api.dto;

import com.petshop.domain.entity.Category;
import jakarta.validation.constraints.NotNull;

public record ProductDTO(
        Long id,
        @NotNull String name,
        Category category,
        @NotNull double value,
        @NotNull int quantity
) {}
