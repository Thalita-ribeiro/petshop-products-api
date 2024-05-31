package com.petshop.api.dto;

public record CategoryDTO(
        Long id,
        Boolean active,
        String name
) {}
