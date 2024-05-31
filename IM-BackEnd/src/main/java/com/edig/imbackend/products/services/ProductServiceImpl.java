package com.edig.imbackend.products.services;

import com.edig.imbackend.products.dto.ProductCreateDTO;
import com.edig.imbackend.products.dto.ProductDTO;
import com.edig.imbackend.products.entities.Product;
import com.edig.imbackend.products.mapper.ProductMapper;
import com.edig.imbackend.products.repository.ProductRepository;
import com.edig.imbackend.users.entities.User;
import com.edig.imbackend.users.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ProductServiceImpl implements ProductService {


    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final ProductMapper productMapper;

    @Override
    public List<ProductDTO> getProducts() {
        return productRepository.findAll().stream()
                .map(productMapper::toProductDTO)
                .toList();
    }

    @Override
    public ProductDTO getProduct(String id) {
        return productRepository.findById(id)
                .map(productMapper::toProductDTO)
                .orElseThrow(() -> new RuntimeException("Product Not Found with id: " + id));
    }

    @Override
    public ProductDTO createProduct(ProductCreateDTO productDTO, String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User Not Found with username: " + username));
        Product product = productMapper.toProductFromCreateDTO(productDTO);
        product.setUser(user);
        return productMapper.toProductDTO(productRepository.save(product));
    }

    @Override
    public ProductDTO updateProduct(String id, ProductCreateDTO productDTO, String username) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product Not Found with id: " + id));
        if (!product.getUser().getUsername().equals(username)) {
            throw new RuntimeException("Access Denied");
        }
        product.setName(productDTO.getName());
        product.setDescription(productDTO.getDescription());
        product.setPrice(productDTO.getPrice());
        product.setStock(productDTO.getStock());
        return productMapper.toProductDTO(productRepository.save(product));
    }

    @Override
    public void deleteProduct(String id) {
        productRepository.deleteById(id);
    }

    @Override
    public List<ProductDTO> getProductsByUser(String username) {
        return productRepository.findByUserUsername(username).stream()
                .map(productMapper::toProductDTO)
                .toList();
    }
}
