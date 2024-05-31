package com.edig.imbackend.products.controller;

import com.edig.imbackend.commons.ApiResponse;
import com.edig.imbackend.products.dto.ProductCreateDTO;
import com.edig.imbackend.products.dto.ProductDTO;
import com.edig.imbackend.products.entities.Product;
import com.edig.imbackend.products.services.ProductService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users/{username}/products")
@AllArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping
    public ResponseEntity<ApiResponse<?>> getProducts(@PathVariable String username, Authentication auth) {
        ResponseEntity<ApiResponse<?>> FORBIDDEN = validateAdmin(username, auth);
        if (FORBIDDEN != null) return FORBIDDEN;
        List<ProductDTO> products = productService.getProductsByUser(username);
        return ResponseEntity.ok(new ApiResponse<>(HttpStatus.OK.value(), "Products fetched successfully", products));
    }


    @PostMapping
    public ResponseEntity<ApiResponse<?>> createProduct(@PathVariable String username, @Valid @RequestBody ProductCreateDTO productDTO, Authentication auth) {
        ResponseEntity<ApiResponse<?>> FORBIDDEN = validateAdmin(username, auth);
        if (FORBIDDEN != null) return FORBIDDEN;
        ProductCreateDTO product = productService.createProduct(productDTO, username);
        return ResponseEntity.ok(new ApiResponse<>(HttpStatus.CREATED.value(), "Product created successfully", product));
    }

    @GetMapping("/{productId}")
    public ResponseEntity<ApiResponse<?>> getProduct(@PathVariable String username, @PathVariable String productId, Authentication auth) {
        ResponseEntity<ApiResponse<?>> FORBIDDEN = validateAdmin(username, auth);
        if (FORBIDDEN != null) return FORBIDDEN;
        ProductCreateDTO product = productService.getProduct(productId);
//        if (!product.getUserId().equals(username)) {
//            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ApiResponse<>(HttpStatus.FORBIDDEN.value(), "Access Denied", null));
//        }
        return ResponseEntity.ok(new ApiResponse<>(HttpStatus.OK.value(), "Product fetched successfully", product));
    }

    @PutMapping("/{productId}")
    public ResponseEntity<ApiResponse<?>> updateProduct(@PathVariable String username, @PathVariable String productId, @Valid @RequestBody ProductDTO productDTO, Authentication auth) {
        ResponseEntity<ApiResponse<?>> FORBIDDEN = validateAdmin(username, auth);
        if (FORBIDDEN != null) return FORBIDDEN;
        ProductCreateDTO product = productService.updateProduct(productId, productDTO, username);
        return ResponseEntity.ok(new ApiResponse<>(HttpStatus.OK.value(), "Product updated successfully", product));
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<ApiResponse<?>> deleteProduct(@PathVariable String username, @PathVariable String productId, Authentication auth) {
        ResponseEntity<ApiResponse<?>> FORBIDDEN = validateAdmin(username, auth);
        if (FORBIDDEN != null) return FORBIDDEN;
        productService.deleteProduct(productId);
        return ResponseEntity.ok(new ApiResponse<>(HttpStatus.OK.value(), "Product deleted successfully", null));
    }

    private static ResponseEntity<ApiResponse<?>> validateAdmin(String username, Authentication auth) {
        boolean isAdmin = auth.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .anyMatch(role -> role.equals("ROLE_ADMIN")); // Change "ROLE_ADMIN" to your admin role if it's different
        if (!auth.getName().equals(username) && !isAdmin) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ApiResponse<>(HttpStatus.FORBIDDEN.value(), "Access Denied", null));
        }
        return null;
    }
}
