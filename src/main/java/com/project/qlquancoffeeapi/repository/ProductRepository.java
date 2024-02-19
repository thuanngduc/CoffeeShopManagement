package com.project.qlquancoffeeapi.repository;

import com.project.qlquancoffeeapi.entity.Product;
import com.project.qlquancoffeeapi.entity.ProductType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findAll();
    List<Product> findByItemNameContainingIgnoreCase(String itemName);
    List<Product> findByProductType(ProductType productType);
}
