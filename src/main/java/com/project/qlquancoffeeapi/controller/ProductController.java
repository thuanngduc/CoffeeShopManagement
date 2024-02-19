package com.project.qlquancoffeeapi.controller;

import com.project.qlquancoffeeapi.entity.Product;
import com.project.qlquancoffeeapi.entity.ProductType;
import com.project.qlquancoffeeapi.exception.DuplicateProductException;
import com.project.qlquancoffeeapi.exception.ProductNotFoundException;
import com.project.qlquancoffeeapi.exception.ResourceNotFoundException;
import com.project.qlquancoffeeapi.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/products")
public class ProductController {
    @Autowired
    private ProductService productService;

    @GetMapping
    public List<Product> getAllProducts()
    {
        return productService.getAllProducts();
    }
    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductsById(@PathVariable Long id) throws ProductNotFoundException {
        try{
            Product product = productService.getProductById(id);
            return new ResponseEntity<>(product, HttpStatus.OK);
        }catch (ResourceNotFoundException ex)
        {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/search")
    public List<Product> searchProducts(@RequestParam String query)
    {
        return productService.searchProducts(query);
    }

    @GetMapping("/byType")
    public List<Product> getProductsByType(@RequestParam("type") ProductType productType)
    {
        return productService.getProductsByType(productType);
    }

    @PostMapping
    public ResponseEntity<Product> saveProduct(@RequestBody Product product)
    {
        Product savedProduct = productService.saveProduct(product);
        return new ResponseEntity<>(savedProduct, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable Long id, @RequestBody Product product)
    {
        try
        {
            Product updatedProduct = productService.updateProduct(id, product);
            return new ResponseEntity<>(updatedProduct, HttpStatus.OK);
        }catch (ProductNotFoundException ex) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (DuplicateProductException ex) {
            return new ResponseEntity<>(HttpStatus.CONFLICT); // 409 Conflict
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Product> deleteProduct(@PathVariable Long id)
    {
        try{
            productService.deleteProduct(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }catch (ResourceNotFoundException ex)
        {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
