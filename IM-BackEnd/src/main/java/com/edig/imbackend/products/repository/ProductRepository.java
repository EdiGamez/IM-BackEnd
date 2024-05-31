package com.edig.imbackend.products.repository;

import com.edig.imbackend.products.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, String> {

    List<Product> findByUserUsername(String username);

}
