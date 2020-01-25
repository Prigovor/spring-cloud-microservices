package com.prigovor.api.core.product;

import org.springframework.web.bind.annotation.*;

public interface ProductService {

    /**
     * Sample usage:
     * <p>
     * curl -X POST $HOST:$PORT/product \
     * -H "Content-Type: application/json" --data \
     * '{"productId":123,"name":"product 123","weight":123}'
     *
     * @param body Product
     * @return {@code Product}
     */
    @PostMapping(value = "/product", consumes = "application/json", produces = "application/json")
    Product createProduct(@RequestBody Product body);

    /**
     * Sample usage: curl $HOST:$PORT/product/1
     *
     * @param productId product id
     * @return the product, if found, else null
     */
    @GetMapping(value = "/product/{productId}", produces = "application/json")
    Product getProduct(@PathVariable int productId);

    /**
     * Sample usage:
     * <p>
     * curl -X DELETE $HOST:$PORT/product/1
     *
     * @param productId product id
     */
    @DeleteMapping(value = "/product/{productId}")
    void deleteProduct(@PathVariable int productId);

}