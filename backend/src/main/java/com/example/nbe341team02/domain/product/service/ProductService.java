package com.example.nbe341team02.domain.product.service;

import com.example.nbe341team02.domain.product.dto.ProductDescriptionDTO;
import com.example.nbe341team02.domain.product.entity.Product;
import com.example.nbe341team02.domain.product.repository.ProductRepository;
import com.example.nbe341team02.global.exception.CustomException;
import com.example.nbe341team02.global.exception.ErrorCode;
import com.example.nbe341team02.domain.product.dto.ProductDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    // 이미지 저장 메소드
    public String saveImage(MultipartFile file) {
        try {
            // 상품 개수 조회 (현재 저장된 상품 수 + 1)
            long productCount = productRepository.count();
            String fileName = "product" + (productCount + 1) + "." + getFileExtension(file); // 파일의 id가 만약 5이면 product5.png이런식으로 저장되게

            Path path = Paths.get("src/main/resources/static", fileName);

            // 파일 저장
            Files.write(path, file.getBytes());

            return "/static/" + fileName;
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("이미지 저장에 실패했습니다.");
        }
    }

    // 파일 확장자 추출
    private String getFileExtension(MultipartFile file) {
        String fileName = file.getOriginalFilename();
        return fileName != null && fileName.contains(".") ? fileName.substring(fileName.lastIndexOf(".") + 1) : "";
    }

    // 상품 추가 기능 (이미지 저장 기능 추가)
    public ProductDescriptionDTO addProduct(ProductDescriptionDTO productDescriptionDTO, MultipartFile file) {
        // 이미지 파일 저장 후 URL 반환
        String imageUrl = saveImage(file);

        // DTO 객체 생성
        Product product = convertToEntity(productDescriptionDTO);
        product.setImageUrl(imageUrl);  // 이미지 URL 설정

        // 상품 저장
        Product savedProduct = productRepository.save(product);

        // 저장된 상품을 DTO로 변환하여 반환
        return new ProductDescriptionDTO(
                savedProduct.getId(),
                savedProduct.getName(),
                savedProduct.getPrice(),
                savedProduct.getStock(),
                savedProduct.isStatus(),
                savedProduct.getImageUrl(),
                savedProduct.getDescription()
        );
    }

    // 상품 목록 조회
    private List<ProductDTO> convertToDTOList(List<Product> products) {
        List<ProductDTO> productDTOList = new ArrayList<>();
        for (Product product : products) {
            productDTOList.add(convertToDTO(product)); // 각 상품을 DTO로 변환하여 리스트에 추가
        }
        return productDTOList;
    }

    public List<ProductDTO> findAllProducts() {
        List<Product> products = productRepository.findAll();
        return convertToDTOList(products);
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
    public ProductDescriptionDTO updateProduct(Long id, ProductDescriptionDTO productDescriptionDTO) {
        //예외 - 상품이 없음
        Product product =  productRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.PRODUCT_NOT_FOUND));

        product.setName(productDescriptionDTO.getName());
        product.setPrice(productDescriptionDTO.getPrice());
        product.setStock(productDescriptionDTO.getStock());
        product.setStatus(productDescriptionDTO.isStatus());
        product.setDescription(productDescriptionDTO.getDescription());

        Product updatedProduct = productRepository.save(product);

        return convertToDescriptionDTO(updatedProduct);
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

    @Transactional
    public ProductDTO updateProductStatus(Long productId, boolean status) {
        // 상품 조회
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new CustomException(ErrorCode.PRODUCT_NOT_FOUND));

        // 상태 변경
        product.setStatus(status);

        return convertToDTO(product);
    }

    // DTO로 변환 메소드
    private ProductDTO convertToDTO(Product product) {
        return new ProductDTO(
                product.getId(),
                product.getName(),
                product.getPrice(),
                product.getStock(),
                product.isStatus(),
                product.getImageUrl()
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
                product.getImageUrl(),
                product.getDescription()
        );
    }

    // ProductDTO 객체를 Product 엔티티 객체로 변환
    private Product convertToEntity(ProductDTO productDTO) {
        return Product.builder()
                .name(productDTO.getName())
                .price(productDTO.getPrice())
                .stock(productDTO.getStock())
                .status(productDTO.isStatus())
                .imageUrl(productDTO.getImageUrl())
                .build();
    }

    // ProductDescriptionDTO 객체를 Product 엔티티 객체로 변환
    private Product convertToEntity(ProductDescriptionDTO productDescriptionDTO) {
        return Product.builder()
                .name(productDescriptionDTO.getName())
                .price(productDescriptionDTO.getPrice())
                .stock(productDescriptionDTO.getStock())
                .status(productDescriptionDTO.isStatus())
                .imageUrl(productDescriptionDTO.getImageUrl())
                .description(productDescriptionDTO.getDescription())
                .build();
    }


}

