package com.petshop.domain.service;

import com.petshop.api.dto.ProductDTO;
import com.petshop.domain.entity.Category;
import com.petshop.domain.entity.Product;
import com.petshop.domain.exception.CategoryNotFoundException;
import com.petshop.infra.repository.CategoryRepository;
import com.petshop.infra.repository.ProductRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ModelMapper modelMapper;

    public void saveProduct(ProductDTO productDTO) {
        if (productRepository.existsByNameAndCategoryId(productDTO.name(), productDTO.category().getId())) {
            throw new IllegalArgumentException("Product name must be unique within the category");
        }
        Category category = categoryRepository.findById(productDTO.category().getId())
                .orElseThrow(() -> new CategoryNotFoundException("Category not found"));
        Product product = modelMapper.map(productDTO, Product.class);
        product.setCategory(category);
        Product savedProduct = productRepository.save(product);
        modelMapper.map(savedProduct, ProductDTO.class);
    }
}
