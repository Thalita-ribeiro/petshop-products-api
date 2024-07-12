package com.petshop.domain.service;

import com.petshop.api.dto.CategoryDTO;
import com.petshop.domain.entity.Category;
import com.petshop.infra.repository.CategoryRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.never;

@ExtendWith(MockitoExtension.class)
public class CategoryServiceTest {

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private CategoryService categoryService;

    @Test
    public void itShouldThrowExceptionIfCategoryNameNotUnique() {
        CategoryDTO categoryDTO = new CategoryDTO(null, true, "Unique Category");
        when(categoryRepository.existsByName(categoryDTO.name())).thenReturn(true);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> categoryService.saveCategory(categoryDTO));

        assertEquals("Category name must be unique", exception.getMessage());
        verify(categoryRepository, times(1)).existsByName(categoryDTO.name());
        verify(categoryRepository, never()).save(any(Category.class));
    }

    @Test
    public void itShouldSaveCategorySuccessfully() {
        CategoryDTO categoryDTO = new CategoryDTO(null, true, "New Category");
        Category category = new Category();
        category.setName("New Category");

        when(categoryRepository.existsByName(categoryDTO.name())).thenReturn(false);
        when(modelMapper.map(categoryDTO, Category.class)).thenReturn(category);
        when(categoryRepository.save(category)).thenReturn(category);
        when(modelMapper.map(category, CategoryDTO.class)).thenReturn(new CategoryDTO(category.getId(), category.getActive(), category.getName()));

        CategoryDTO result = categoryService.saveCategory(categoryDTO);

        assertNotNull(result);
        assertEquals(categoryDTO.name(), result.name());
        verify(categoryRepository, times(1)).existsByName(categoryDTO.name());
        verify(categoryRepository, times(1)).save(category);
        verify(modelMapper, times(1)).map(categoryDTO, Category.class);
        verify(modelMapper, times(1)).map(category, CategoryDTO.class);
    }

    @Test
    public void itShouldGetActiveCategories() {
        Category activeCategory1 = new Category();
        activeCategory1.setActive(true);
        activeCategory1.setName("Active Category 1");

        Category activeCategory2 = new Category();
        activeCategory2.setActive(true);
        activeCategory2.setName("Active Category 2");

        when(categoryRepository.findAllByActiveTrue()).thenReturn(Arrays.asList(activeCategory1, activeCategory2));
        when(modelMapper.map(activeCategory1, CategoryDTO.class)).thenReturn(new CategoryDTO(activeCategory1.getId(), activeCategory1.getActive(), activeCategory1.getName()));
        when(modelMapper.map(activeCategory2, CategoryDTO.class)).thenReturn(new CategoryDTO(activeCategory2.getId(), activeCategory2.getActive(), activeCategory2.getName()));

        List<CategoryDTO> activeCategories = categoryService.getActiveCategories();

        assertNotNull(activeCategories);
        assertEquals(2, activeCategories.size());
        assertEquals("Active Category 1", activeCategories.get(0).name());
        assertEquals("Active Category 2", activeCategories.get(1).name());
        verify(categoryRepository, times(1)).findAllByActiveTrue();
        verify(modelMapper, times(1)).map(activeCategory1, CategoryDTO.class);
        verify(modelMapper, times(1)).map(activeCategory2, CategoryDTO.class);
    }
}
