package com.example.postgresql.controller;

import com.example.postgresql.exception.ResourceNotFoundException;
import com.example.postgresql.model.Product;
import com.example.postgresql.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/product")
public class ProductController {
    @Autowired
    private ProductRepository productRepository;

    //get
    @GetMapping
    public ResponseEntity getAllProducts(){
        return ResponseEntity.ok(this.productRepository.findAll());
    }
    @GetMapping("/{id}")
    public ResponseEntity getProductById(@PathVariable(value = "id") Long productId){
        return ResponseEntity.ok(this.productRepository.findById(productId)) ;
    }
    @PostMapping
    public Product createProduct(@RequestBody Product product){
        return this.productRepository.save(product);
    }
    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable(value = "id") Long producctId, @RequestBody Product productDetails) throws ResourceNotFoundException {
       Product product=productRepository.findById(producctId)
               .orElseThrow(()-> new ResourceNotFoundException("Employee not found for this id :" + producctId));
       product.setName(productDetails.getName());
       product.setDescription(productDetails.getDescription());
       return ResponseEntity.ok(this.productRepository.save(product));
    }
    @DeleteMapping("/{id}")
    public Map<String ,Boolean> deleteProduct(@PathVariable(value = "id") Long producctId) throws ResourceNotFoundException {
        Product product=productRepository.findById(producctId)
                .orElseThrow(()-> new ResourceNotFoundException("Employee not found for this id :" + producctId));
        this.productRepository.delete(product);
        Map<String,Boolean> response =new HashMap<>();
        response.put("deleted",Boolean.TRUE);
        return response;
    }



}
