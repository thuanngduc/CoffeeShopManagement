package com.project.qlquancoffeeapi.service;

import com.project.qlquancoffeeapi.entity.Product;
import com.project.qlquancoffeeapi.entity.ProductType;
import com.project.qlquancoffeeapi.exception.DuplicateProductException;
import com.project.qlquancoffeeapi.exception.ProductNotFoundException;
import com.project.qlquancoffeeapi.exception.ResourceNotFoundException;
import com.project.qlquancoffeeapi.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;
    public List<Product> getAllProducts()
    {
        return productRepository.findAll();
    }
    public Product getProductById(Long id) throws ProductNotFoundException {
        return productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Không tìm thấy product với id: " + id));
    }
    public Product saveProduct(Product product)
    {
        return productRepository.save(product);
    }

    private void validateProductNameUnique(String productName, Long productId) {
        Product existingProduct = (Product) productRepository.findByItemNameContainingIgnoreCase(productName);
        if(existingProduct != null && !existingProduct.getId().equals(productId))
        {
            throw new DuplicateProductException("Tên product '"+ productName +"' đã tồn tại");
        }
    }

    public List<Product> searchProducts (String query)
    {
        return productRepository.findByItemNameContainingIgnoreCase(query);
    }
    public List<Product> getProductsByType(ProductType productType)
    {
        return productRepository.findByProductType(productType);
    }
    public  Product updateProduct(Long id, Product updatedProduct){
        Product existingProduct = getProductById(id);
        existingProduct.setItemName(updatedProduct.getItemName());
        existingProduct.setDescription(updatedProduct.getDescription());
        existingProduct.setPrice(updatedProduct.getPrice());
        existingProduct.setProductType(updatedProduct.getProductType());
        existingProduct.setImageUrl(updatedProduct.getImageUrl());
        return productRepository.save(existingProduct);
    }
    public void deleteProduct(Long id)
    {
        try {
            productRepository.deleteById(id);
        }catch (EmptyResultDataAccessException e)
        {
            throw new ResourceNotFoundException("Product", "id", id);
        }
    }
}
