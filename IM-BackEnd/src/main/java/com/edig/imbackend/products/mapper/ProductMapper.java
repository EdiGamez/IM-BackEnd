package com.edig.imbackend.products.mapper;

import com.edig.imbackend.products.dto.ProductCreateDTO;
import com.edig.imbackend.products.dto.ProductDTO;
import com.edig.imbackend.products.entities.Product;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {

    public ProductDTO toProductDTO(Product product) {
        ProductDTO productDTO = new ProductDTO();
        productDTO.setName(product.getName());
        productDTO.setDescription(product.getDescription());
        productDTO.setPrice(product.getPrice());
        productDTO.setStock(product.getStock());
        productDTO.setUserId(product.getUser().getId());
        return productDTO;
    }

    public Product toProductFromCreateDTO(ProductCreateDTO productCreateDTO) {
        Product product = new Product();
        product.setName(productCreateDTO.getName());
        product.setDescription(productCreateDTO.getDescription());
        product.setPrice(productCreateDTO.getPrice());
        product.setStock(productCreateDTO.getStock());
        return product;
    }
}
