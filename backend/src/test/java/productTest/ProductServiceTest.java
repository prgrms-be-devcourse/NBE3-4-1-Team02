package productTest;

import com.example.nbe341team02.domain.product.dto.ProductDTO;
import com.example.nbe341team02.domain.product.entity.Product;
import com.example.nbe341team02.domain.product.repository.ProductRepository;
import com.example.nbe341team02.domain.product.service.ProductService;
import com.example.nbe341team02.global.exception.CustomException;
import com.example.nbe341team02.global.exception.ErrorCode;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

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
                productId, "Basic Product", 1000, 10, true
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
        assertThat(updatedProduct.getName()).isEqualTo("Updated Product");
        assertThat(updatedProduct.getPrice()).isEqualTo(2000);
        assertThat(updatedProduct.getStock()).isEqualTo(20);
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
                .isInstanceOf(CustomException.class)
                .hasMessage(ErrorCode.PRODUCT_NOT_FOUND.getMessage());
    }

//    @Test
//    void 상품수정_감소(){
//        //given
//        Long id = 1L;
//        ProductDTO reducedDTO = new ProductDTO(
//                id,"reduced Product",
//        )
//    }

    @Test
    void 상품삭제_성공() {
        // given
        Long productId = 1L;
        Product existingProduct = new Product(
                productId, "Product", 1000, 10, true
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
                .isInstanceOf(CustomException.class)
                .hasMessage(ErrorCode.PRODUCT_NOT_FOUND.getMessage());
    }

    @Test
    void 상품상태_변경_성공() {
        // given
        Long productId = 1L;
        Product product = Product.builder()
                .id(productId)
                .name("Test Product")
                .price(10000)
                .stock(100)
                .status(true)
                .build();

        when(productRepository.findById(productId)).thenReturn(Optional.of(product));
        when(productRepository.save(any(Product.class))).thenReturn(product);

        // when
        ProductDTO result = productService.updateProductStatus(productId, false);

        // then
        assertThat(result.isStatus()).isFalse();
    }

    @Test
    void 상품상태_변경_상품없음() {
        // given
        Long productId = 999L;
        when(productRepository.findById(productId)).thenReturn(Optional.empty());

        // when & then
        assertThatThrownBy(() -> productService.updateProductStatus(productId, false))
                .isInstanceOf(CustomException.class)
                .extracting("errorCode")    // errorCode 필드의 값을 확인
                .isEqualTo(ErrorCode.PRODUCT_NOT_FOUND);
    }
}