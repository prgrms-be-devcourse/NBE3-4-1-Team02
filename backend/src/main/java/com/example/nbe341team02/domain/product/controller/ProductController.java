package com.example.nbe341team02.domain.product.controller;

import com.example.nbe341team02.domain.product.dto.ProductDTO;
import com.example.nbe341team02.domain.product.dto.ProductDescriptionDTO;
import com.example.nbe341team02.domain.product.dto.StatusUpdateRequest;
import com.example.nbe341team02.domain.product.service.ProductService;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;


    // 상품 추가
    @PostMapping
    public ResponseEntity<ProductDescriptionDTO> addProduct(
            @RequestParam("file") MultipartFile file,  // 이미지 파일을 받기 위한 파라미터 추가
            @RequestParam("name") String name,
            @RequestParam("price") int price,
            @RequestParam("stock") int stock,
            @RequestParam("status") boolean status,
            @RequestParam("description") String description) {

        // 이미지 파일 저장 후 URL 반환
        String imageUrl = productService.saveImage(file);

        // DTO 생성 (id는 null로 설정, imageUrl은 저장된 이미지 URL)
        ProductDescriptionDTO productDescriptionDTO = new ProductDescriptionDTO(
                null,
                name,
                price,
                stock,
                status,
                imageUrl,
                description
        );

        ProductDescriptionDTO createdProduct = productService.addProduct(productDescriptionDTO, file);

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
    public ResponseEntity<ProductDescriptionDTO> updateProduct(@RequestBody ProductDescriptionDTO productDescriptionDTO,
                                                    @PathVariable("id") Long id){
        ProductDescriptionDTO updatedProduct = productService.updateProduct(id,productDescriptionDTO);
        return ResponseEntity.ok(updatedProduct);
    }

    //상품 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable("id") Long id){
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }

    //상품상태수정
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
