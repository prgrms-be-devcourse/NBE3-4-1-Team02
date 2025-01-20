package com.example.nbe341team02.domain.product.controller;

import com.example.nbe341team02.domain.product.dto.ProductDTO;
import com.example.nbe341team02.domain.product.dto.ProductDescriptionDTO;
import com.example.nbe341team02.domain.product.dto.StatusUpdateRequest;
import com.example.nbe341team02.domain.product.service.ProductService;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    //상품 추가
    @PostMapping
    public ResponseEntity<ProductDTO> addProduct(@RequestBody ProductDTO productDTO){
        ProductDTO createdProduct = productService.addProduct(productDTO);
        return new ResponseEntity<>(createdProduct, HttpStatus.CREATED);
    }

    //상품 목록 조회
    @GetMapping
    public List<ProductDTO> findAllProducts(){
        return productService.findAllProducts();
    }

    //상품 상세 조회
    @GetMapping("/{id}")
    public ResponseEntity<ProductDescriptionDTO> findProductById(@PathVariable("id") Long id) {
        ProductDescriptionDTO productDescriptionDTO = productService.findProductById(id);
        return ResponseEntity.ok(productDescriptionDTO);
    }

    //상품 수정
    @PutMapping("/{id}")
    public ResponseEntity<ProductDTO> updateProduct(@RequestBody ProductDTO productDTO,
                                                    @PathVariable("id") Long id){
        ProductDTO updatedProduct = productService.updateProduct(id,productDTO);
        return ResponseEntity.ok(updatedProduct);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable("id") Long id){
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<ProductDTO> updateProductStatus(
            @PathVariable Long id,
            @RequestBody StatusUpdateRequest request
    ) {
        return ResponseEntity.ok(
                productService.updateProductStatus(id, request.isStatus())
        );
    }
}
