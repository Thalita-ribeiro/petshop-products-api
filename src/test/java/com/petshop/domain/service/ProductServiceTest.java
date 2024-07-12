package com.petshop.domain.service;

import com.petshop.api.dto.CategoryDTO;
import com.petshop.api.dto.ProductDTO;
import com.petshop.domain.entity.Category;
import com.petshop.domain.entity.Product;
import com.petshop.domain.exception.CategoryNotFoundException;
import com.petshop.infra.repository.CategoryRepository;
import com.petshop.infra.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.never;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private ProductService productService;

    @Test
    public void itShouldThrowExceptionIfProductNameNotUniqueWithinCategory() {
        CategoryDTO categoryDTO = new CategoryDTO(1L, true, "Category 1");
        Category category = new Category();
        category.setId(1L);
        ProductDTO productDTO = new ProductDTO(null, "Product 1", category, new BigDecimal("100.0"), 10);

        when(productRepository.existsByNameAndCategoryId(productDTO.name(), categoryDTO.id())).thenReturn(true);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> productService.saveProduct(productDTO));

        assertEquals("Product name must be unique within the category", exception.getMessage());
        verify(productRepository, times(1)).existsByNameAndCategoryId(productDTO.name(), categoryDTO.id());
        verify(categoryRepository, never()).findById(anyLong());
        verify(productRepository, never()).save(any(Product.class));
    }

    @Test
    public void itShouldThrowExceptionIfCategoryNotFound() {
        CategoryDTO categoryDTO = new CategoryDTO(1L, true, "Category 1");
        Category category = new Category();
        category.setId(1L);
        ProductDTO productDTO = new ProductDTO(null, "Product 1", category, new BigDecimal("100.0"), 10);

        when(productRepository.existsByNameAndCategoryId(productDTO.name(), categoryDTO.id())).thenReturn(false);
        when(categoryRepository.findById(categoryDTO.id())).thenReturn(Optional.empty());

        CategoryNotFoundException exception = assertThrows(CategoryNotFoundException.class, () -> productService.saveProduct(productDTO));

        assertEquals("Category not found", exception.getMessage());
        verify(productRepository, times(1)).existsByNameAndCategoryId(productDTO.name(), categoryDTO.id());
        verify(categoryRepository, times(1)).findById(categoryDTO.id());
        verify(productRepository, never()).save(any(Product.class));
    }

    @Test
    public void itShouldSaveProductSuccessfully() {
        CategoryDTO categoryDTO = new CategoryDTO(1L, true, "Category 1");
        Category category = new Category();
        category.setId(1L);
        ProductDTO productDTO = new ProductDTO(null, "Product 1", category, new BigDecimal("100.0"), 10);

        Product product = new Product();
        product.setName("Product 1");
        product.setCategory(category);
        product.setValue(new BigDecimal("100.0"));
        product.setQuantity(10);

        Product savedProduct = new Product();
        savedProduct.setId(1L);
        savedProduct.setName("Product 1");
        savedProduct.setCategory(category);
        savedProduct.setValue(new BigDecimal("100.0"));
        savedProduct.setQuantity(10);

        when(productRepository.existsByNameAndCategoryId(productDTO.name(), categoryDTO.id())).thenReturn(false);
        when(categoryRepository.findById(categoryDTO.id())).thenReturn(Optional.of(category));
        when(modelMapper.map(productDTO, Product.class)).thenReturn(product);
        when(productRepository.save(product)).thenReturn(savedProduct);
        when(modelMapper.map(savedProduct, ProductDTO.class)).thenReturn(new ProductDTO(1L, "Product 1", category, new BigDecimal("100.0"), 10));

        productService.saveProduct(productDTO);

        verify(productRepository, times(1)).existsByNameAndCategoryId(productDTO.name(), categoryDTO.id());
        verify(categoryRepository, times(1)).findById(categoryDTO.id());
        verify(productRepository, times(1)).save(product);
        verify(modelMapper, times(1)).map(productDTO, Product.class);
        verify(modelMapper, times(1)).map(savedProduct, ProductDTO.class);
    }
}
