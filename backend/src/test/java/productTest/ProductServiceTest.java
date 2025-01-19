package productTest;

import com.example.nbe341team02.domain.product.dto.ProductDTO;
import com.example.nbe341team02.product.entity.Product;
import com.example.nbe341team02.product.exception.ProductNotFoundException;
import com.example.nbe341team02.product.repository.ProductRepository;
import com.example.nbe341team02.product.service.ProductService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService;

    @Test
    void 상품수정_성공() {
        // given
        Long productId = 1L;
        Product existingProduct = new Product(
                productId, "Basic Product", 1000, 10, true,
                LocalDateTime.now(), LocalDateTime.now()
        );
        ProductDTO updateDTO = new ProductDTO(
                productId, "Updated Product", 2000, 20, true
        );

        when(productRepository.findById(productId)).thenReturn(Optional.of(existingProduct));
        when(productRepository.save(any(Product.class))).thenReturn(existingProduct);

        // when
        ProductDTO updatedProduct = productService.updateProduct(productId, updateDTO);

        // then
        assertThat(updatedProduct).isNotNull();
        assertThat(updatedProduct.getProductName()).isEqualTo("Updated Product");
        assertThat(updatedProduct.getProductPrice()).isEqualTo(2000);
        assertThat(updatedProduct.getProductStock()).isEqualTo(20);
        verify(productRepository).save(any(Product.class));
    }

    @Test
    void 상품수정_예외() {
        // given
        Long productId = 999L;
        ProductDTO updateDTO = new ProductDTO(
                productId, "Updated Product", 2000, 20, true
        );
        when(productRepository.findById(productId)).thenReturn(Optional.empty());

        // when & then
        assertThatThrownBy(() -> productService.updateProduct(productId, updateDTO))
                .isInstanceOf(ProductNotFoundException.class)
                .hasMessageContaining("Product not found");
    }

    @Test
    void 상품삭제_성공() {
        // given
        Long productId = 1L;
        Product existingProduct = new Product(
                productId, "Product", 1000, 10, true,
                LocalDateTime.now(), LocalDateTime.now()
        );
        when(productRepository.findById(productId)).thenReturn(Optional.of(existingProduct));
        doNothing().when(productRepository).delete(existingProduct);

        // when
        productService.deleteProduct(productId);

        // then
        verify(productRepository).delete(existingProduct);
    }

    @Test
    void 상품삭제_예외() {
        // given
        Long productId = 999L;
        when(productRepository.findById(productId)).thenReturn(Optional.empty());

        // when & then
        assertThatThrownBy(() -> productService.deleteProduct(productId))
                .isInstanceOf(ProductNotFoundException.class)
                .hasMessageContaining("Product not found");
    }
}