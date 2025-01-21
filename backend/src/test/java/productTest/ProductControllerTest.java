package productTest;

import com.example.nbe341team02.domain.product.controller.ProductController;
import com.example.nbe341team02.domain.product.dto.ProductDTO;
import com.example.nbe341team02.domain.product.dto.ProductDescriptionDTO;
import com.example.nbe341team02.domain.product.dto.StatusUpdateRequest;
import com.example.nbe341team02.domain.product.entity.Product;
import com.example.nbe341team02.domain.product.service.ProductService;
import com.example.nbe341team02.global.exception.CustomException;
import com.example.nbe341team02.global.exception.ErrorCode;
import com.example.nbe341team02.global.exception.GlobalExceptionHandler;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class ProductControllerTest {

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @Mock
    private ProductService productService;

    @InjectMocks
    private ProductController productController;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
        mockMvc = MockMvcBuilders
                .standaloneSetup(productController)
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();
    }
    
    @Test
    void 상품수정_1개_성공() throws Exception {
        // given
        Long productId = 1L;
        ProductDescriptionDTO productDescriptionDTO = ProductDescriptionDTO.builder()
            .productId(productId)
            .productName("Updated Product")
            .productPrice(3000)
            .productStock(15)
            .productStatus(true)
            .description("설명")
            .imageUrl("image-url")
            .build();
        when(productService.updateProduct(eq(productId), any(ProductDescriptionDTO.class))).thenReturn(productDescriptionDTO);

        // when & then
        mockMvc.perform(put("/products/{id}", productId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(productDescriptionDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.productId").value(productId))
                .andExpect(jsonPath("$.productName").value("Updated Product"))
                .andExpect(jsonPath("$.productPrice").value(3000))
                .andExpect(jsonPath("$.productStock").value(15))
                .andExpect(jsonPath("$.productStatus").value(true));
    }

    @Test
    void 상품수정_상품이_존재하지_않을때() throws Exception {
        // given
        Long productId = 999L;
        ProductDescriptionDTO productDescriptionDTO = ProductDescriptionDTO.builder()
            .productId(productId)
            .productName("Updated Product")
            .productPrice(3000)
            .productStock(15)
            .productStatus(true)
            .description("설명")
            .imageUrl("image-url")
            .build();
        when(productService.updateProduct(eq(productId), any(ProductDescriptionDTO.class)))
                .thenThrow(new CustomException(ErrorCode.PRODUCT_NOT_FOUND));

        // when & then
        mockMvc.perform(put("/products/{id}", productId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(productDescriptionDTO)))
                .andExpect(status().isNotFound());
    }

    @Test
    void 상품삭제_성공() throws Exception {
        // given
        Long productId = 1L;
        doNothing().when(productService).deleteProduct(productId);

        // when & then
        mockMvc.perform(delete("/products/{id}", productId))
                .andExpect(status().isNoContent());
    }

    @Test
    void 상품삭제_상품이_존재하지_않을_때() throws Exception {
        // given
        Long productId = 999999L;
        doThrow(new CustomException(ErrorCode.PRODUCT_NOT_FOUND))
                .when(productService).deleteProduct(productId);
        // when & then
        mockMvc.perform(delete("/products/{id}", productId))
                .andExpect(status().isNotFound());
    }

    @Test
    void 상품상태_변경_성공() throws Exception {
        // given
        Long productId = 1L;
        StatusUpdateRequest request = new StatusUpdateRequest(false);
        ProductDTO updatedProduct = new ProductDTO(productId, "Test Product", 10000, 100, false);

        when(productService.updateProductStatus(eq(productId), eq(false)))
                .thenReturn(updatedProduct);

        // when & then
        mockMvc.perform(patch("/products/{id}/status", productId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(false));
    }
}