package com.prigovor.api.core.review;

import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

import java.util.List;

public interface ReviewService {

    /**
     * Sample usage:
     * <p>
     * curl -X POST $HOST:$PORT/review \
     * -H "Content-Type: application/json" --data \
     * '{"productId":123,"reviewId":456,"author":"me","subject":"yada, yada, yada","content":"yada, yada, yada"}'
     *
     * @param body Review
     * @return {@code Review}
     */
    @PostMapping(value = "/review", consumes = "application/json", produces = "application/json")
    Review createReview(@RequestBody final Review body);

    /**
     * Sample usage: curl $HOST:$PORT/review?productId=1
     *
     * @param productId product id
     * @return {@code Flux<Review>}
     */
    @GetMapping(value = "/review", produces = "application/json")
    Flux<Review> getReviews(@RequestParam(value = "productId") final int productId);

    /**
     * Sample usage:
     * <p>
     * curl -X DELETE $HOST:$PORT/review?productId=1
     *
     * @param productId product id
     */
    @DeleteMapping(value = "/review")
    void deleteReviews(@RequestParam(value = "productId") final int productId);

}