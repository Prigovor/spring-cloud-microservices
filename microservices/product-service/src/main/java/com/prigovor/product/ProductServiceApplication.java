package com.prigovor.product;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.env.ConfigurableEnvironment;

@SpringBootApplication
@ComponentScan({"com.prigovor"})
public class ProductServiceApplication {

    private static final Logger LOG = LoggerFactory.getLogger(ProductServiceApplication.class);

    public static void main(String[] args) {
        final ConfigurableEnvironment env = SpringApplication.run(ProductServiceApplication.class, args).getEnvironment();
        LOG.info("Connected to MongoDb: " + env.getProperty("spring.data.mongodb.host") + ":" + env.getProperty("spring.data.mongodb.port"));
    }

}
