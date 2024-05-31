package com.petshop.domain.service;

import com.petshop.api.dto.CategoryDTO;
import com.petshop.domain.entity.Category;
import com.petshop.infra.repository.CategoryRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ModelMapper modelMapper;

    public CategoryDTO saveCategory(CategoryDTO categoryDTO) {
        if (categoryRepository.existsByName(categoryDTO.name())) {
            throw new IllegalArgumentException("Category name must be unique");
        }
        Category category = modelMapper.map(categoryDTO, Category.class);
        Category savedCategory = categoryRepository.save(category);
        return modelMapper.map(savedCategory, CategoryDTO.class);
    }

    public List<CategoryDTO> getActiveCategories() {
        return categoryRepository.findAllByActiveTrue()
                .stream()
                .map(category -> modelMapper.map(category, CategoryDTO.class))
                .collect(Collectors.toList());
    }
}