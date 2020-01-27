package com.prigovor.composite;

import com.prigovor.api.composite.product.ProductAggregate;
import com.prigovor.api.composite.product.RecommendationSummary;
import com.prigovor.api.composite.product.ReviewSummary;
import com.prigovor.api.core.product.Product;
import com.prigovor.api.core.recommendation.Recommendation;
import com.prigovor.api.core.review.Review;
import com.prigovor.composite.services.ProductCompositeIntegration;
import com.prigovor.util.exceptions.InvalidInputException;
import com.prigovor.util.exceptions.NotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import static java.util.Collections.singletonList;
import static org.mockito.Mockito.when;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;

@SpringBootTest(webEnvironment = RANDOM_PORT)
class ProductCompositeServiceApplicationTests {

    private static final int PRODUCT_ID_OK = 1;
    private static final int PRODUCT_ID_NOT_FOUND = 2;
    private static final int PRODUCT_ID_INVALID = 3;

    @Autowired
    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    private WebTestClient client;

    @MockBean
    private ProductCompositeIntegration compositeIntegration;

    @BeforeEach
    public void setUp() {
        when(compositeIntegration.getProduct(PRODUCT_ID_OK)).
                thenReturn(new Product(PRODUCT_ID_OK, "name", 1, "mock-address"));
        when(compositeIntegration.getRecommendations(PRODUCT_ID_OK)).
                thenReturn(singletonList(new Recommendation(PRODUCT_ID_OK, 1, "author", 1, "content", "mock address")));
        when(compositeIntegration.getReviews(PRODUCT_ID_OK)).
                thenReturn(singletonList(new Review(PRODUCT_ID_OK, 1, "author", "subject", "content", "mock address")));
        when(compositeIntegration.getProduct(PRODUCT_ID_NOT_FOUND)).thenThrow(new NotFoundException("NOT FOUND: " + PRODUCT_ID_NOT_FOUND));
        when(compositeIntegration.getProduct(PRODUCT_ID_INVALID)).thenThrow(new InvalidInputException("INVALID: " + PRODUCT_ID_INVALID));
    }

    @Test
    public void contextLoads() {
    }

    @Test
    public void createCompositeProduct1() {
        ProductAggregate compositeProduct = new ProductAggregate(1, "name", 1, null, null, null);
        postAndVerifyProduct(compositeProduct);
    }

    @Test
    public void createCompositeProduct2() {
        ProductAggregate compositeProduct = new ProductAggregate(1, "name", 1,
                singletonList(new RecommendationSummary(1, "a", 1, "c")),
                singletonList(new ReviewSummary(1, "a", "s", "c")), null);
        postAndVerifyProduct(compositeProduct);
    }

    @Test
    public void deleteCompositeProduct() {
        ProductAggregate compositeProduct = new ProductAggregate(1, "name", 1,
                singletonList(new RecommendationSummary(1, "a", 1, "c")),
                singletonList(new ReviewSummary(1, "a", "s", "c")), null);
        postAndVerifyProduct(compositeProduct);
        deleteAndVerifyProduct(compositeProduct.getProductId());
        deleteAndVerifyProduct(compositeProduct.getProductId());
    }

    @Test
    public void getProductById() {
        getAndVerifyProduct(PRODUCT_ID_OK, OK)
                .jsonPath("$.productId").isEqualTo(PRODUCT_ID_OK)
                .jsonPath("$.recommendations.length()").isEqualTo(1)
                .jsonPath("$.reviews.length()").isEqualTo(1);
    }

    @Test
    public void getProductNotFound() {
        getAndVerifyProduct(PRODUCT_ID_NOT_FOUND, NOT_FOUND)
                .jsonPath("$.path").isEqualTo("/product-composite/" + PRODUCT_ID_NOT_FOUND)
                .jsonPath("$.message").isEqualTo("NOT FOUND: " + PRODUCT_ID_NOT_FOUND);
    }

    @Test
    public void getProductInvalidInput() {
        getAndVerifyProduct(PRODUCT_ID_INVALID, UNPROCESSABLE_ENTITY)
                .jsonPath("$.path").isEqualTo("/product-composite/" + PRODUCT_ID_INVALID)
                .jsonPath("$.message").isEqualTo("INVALID: " + PRODUCT_ID_INVALID);
    }

    private WebTestClient.BodyContentSpec getAndVerifyProduct(int productId, HttpStatus expectedStatus) {
        return client.get()
                .uri("/product-composite/" + productId)
                .accept(APPLICATION_JSON)
                .exchange()
                .expectStatus().isEqualTo(expectedStatus)
                .expectHeader().contentType(APPLICATION_JSON)
                .expectBody();
    }

    private void postAndVerifyProduct(ProductAggregate compositeProduct) {
        client.post()
                .uri("/product-composite")
                .body(Mono.just(compositeProduct), Object.class)
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.OK);
    }

    private void deleteAndVerifyProduct(int productId) {
        client.delete()
                .uri("/product-composite/" + productId)
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.OK);
    }

}
