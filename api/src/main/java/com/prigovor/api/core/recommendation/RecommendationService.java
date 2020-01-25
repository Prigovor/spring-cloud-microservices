package com.prigovor.api.core.recommendation;

import org.springframework.web.bind.annotation.*;

import java.util.List;

public interface RecommendationService {

    /**
     * Sample usage:
     * <p>
     * curl -X POST $HOST:$PORT/recommendation \
     * -H "Content-Type: application/json" --data \
     * '{"productId":123,"recommendationId":456,"author":"me","rate":5,"content":"yada, yada, yada"}'
     *
     * @param body Recommendation
     * @return {@code Recommendation}
     */
    @PostMapping(value = "/recommendation", consumes = "application/json", produces = "application/json")
    Recommendation createRecommendation(@RequestBody final Recommendation body);

    /**
     * Sample usage:
     * <p>
     * curl $HOST:$PORT/recommendation?productId=1
     *
     * @param productId product id
     * @return {@code List<Recommendation>}
     */
    @GetMapping(value = "/recommendation", produces = "application/json")
    List<Recommendation> getRecommendations(@RequestParam(value = "productId") final int productId);

    /**
     * Sample usage:
     * <p>
     * curl -X DELETE $HOST:$PORT/recommendation?productId=1
     *
     * @param productId product id
     */
    @DeleteMapping(value = "/recommendation")
    void deleteRecommendations(@RequestParam(value = "productId") final int productId);

}