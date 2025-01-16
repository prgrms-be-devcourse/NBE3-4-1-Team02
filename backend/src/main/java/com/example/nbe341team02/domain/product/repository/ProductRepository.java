package com.example.nbe341team02.domain.product.repository;//package com.example.nbe341team02.domain.product.repository;

import com.example.nbe341team02.domain.product.entity.Product;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {

    Optional<Product> findById(Long id); // 반환 타입 수정

}
