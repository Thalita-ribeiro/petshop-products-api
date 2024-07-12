package com.petshop.api.controller;

import com.petshop.api.dto.ProductDTO;
import com.petshop.domain.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping
    public ResponseEntity<Void> createProduct(@Valid @RequestBody ProductDTO productDTO) {
        productService.saveProduct(productDTO);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}