package com.petshop.api.dto;

import com.petshop.domain.entity.Category;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record ProductDTO(
        Long id,
        @NotNull String name,
        Category category,
        @NotNull BigDecimal value,
        @NotNull int quantity
) {}

// trocar o value para bigdecimal