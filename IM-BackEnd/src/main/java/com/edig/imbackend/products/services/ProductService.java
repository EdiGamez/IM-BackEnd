package com.edig.imbackend.products.services;

import com.edig.imbackend.products.dto.ProductCreateDTO;
import com.edig.imbackend.products.dto.ProductDTO;

import java.util.List;

public interface ProductService {
    List<ProductDTO> getProducts();

    ProductDTO getProduct(String id);

    ProductDTO createProduct(ProductCreateDTO productDTO, String username);

    ProductDTO updateProduct(String id, ProductCreateDTO productDTO, String username);

    void deleteProduct(String id);

    List<ProductDTO> getProductsByUser(String username);
}
