package com.example.nbe341team02.domain.product.service;

import com.example.nbe341team02.domain.product.dto.ProductDescriptionDTO;
import com.example.nbe341team02.domain.product.entity.Product;
import com.example.nbe341team02.domain.product.repository.ProductRepository;
import com.example.nbe341team02.global.exception.CustomException;
import com.example.nbe341team02.global.exception.ErrorCode;
import com.example.nbe341team02.domain.product.dto.ProductDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;


    //상품 추가 기능
    public ProductDTO addProduct(ProductDTO productDTO) {
        // ProductDTO를 Product 엔티티로 변환
        Product product = convertToEntity(productDTO);

        Product savedProduct = productRepository.save(product);

        // 저장된 상품을 DTO로 변환하여 반환
        return convertToDTO(savedProduct);
    }

    //상품 목록 조회
    private List<ProductDTO> convertToDTOList(List<Product> products) {
        List<ProductDTO> productDTOList = new ArrayList<>();
        for (Product product : products) {
            productDTOList.add(convertToDTO(product)); // 각 상품을 DTO로 변환하여 리스트에 추가
        }
        return productDTOList;
    }
    public List<ProductDTO> findAllProducts() {
        List<Product> products = productRepository.findAll();

        return products.isEmpty() ? new ArrayList<>() : convertToDTOList(products);
        //상품 없으면 빈 리스트 반환, 있으면 dto변환 후 반환
    }

    //상품 상세 조회
    public ProductDescriptionDTO findProductById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.PRODUCT_NOT_FOUND));

        return convertToDescriptionDTO(product);
    }


    //--김규진--
    //상품 수정 기능
    @jakarta.transaction.Transactional
    public ProductDTO updateProduct(Long id, ProductDTO productDTO) {
        //예외 - 상품이 없음
        Product product =  productRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.PRODUCT_NOT_FOUND));

        product.setName(productDTO.getName());
        product.setPrice(productDTO.getPrice());
        product.setStock(productDTO.getStock());
        product.setStatus(productDTO.isStatus());

        Product updatedProduct = productRepository.save(product);

        return convertToDTO(updatedProduct);
    }

    @Transactional
    public Product reduceStock(Long id, int quantity) {
        Product product = productRepository.findById(id)
                        .orElseThrow(() -> new CustomException(ErrorCode.PRODUCT_NOT_FOUND));
        int newStock = product.getStock() - quantity;
        if (newStock < 0) {
            throw new CustomException(ErrorCode.INSUFFICIENT_STOCK);  // 재고 부족 처리
        }
        product.setStock(newStock); // Product Entity에서 분리했는데 setter 써도되는지..?
        return productRepository.save(product);
    }

    // 상품 삭제
    @Transactional
    public void deleteProduct(Long id) {
        //예외 - 상품이 없음
        Product product =  productRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.PRODUCT_NOT_FOUND));

        productRepository.delete(product);
    }


    // DTO로 변환 메소드
    private ProductDTO convertToDTO(Product product) {
        return new ProductDTO(
                product.getId(),
                product.getName(),
                product.getPrice(),
                product.getStock(),
                product.isStatus()
        );
    }

    //DescriptionDTO로 변환 메소드
    private ProductDescriptionDTO convertToDescriptionDTO(Product product) {
        return new ProductDescriptionDTO(
                product.getId(),
                product.getName(),
                product.getPrice(),
                product.getStock(),
                product.isStatus(),
                product.getDescription() // description 추가
        );
    }

    // Entity로 변환 메소드
    private Product convertToEntity(ProductDTO productDTO) {
        return Product.builder()
                .name(productDTO.getName())
                .price(productDTO.getPrice())
                .stock(productDTO.getStock())
                .status(productDTO.isStatus())
                .build();
    }

}

