package com.prigovor.composite;

import com.prigovor.composite.services.ProductCompositeIntegration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.health.*;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2WebFlux;

import java.util.LinkedHashMap;

import static java.util.Collections.emptyList;
import static org.springframework.web.bind.annotation.RequestMethod.*;
import static springfox.documentation.builders.RequestHandlerSelectors.basePackage;
import static springfox.documentation.spi.DocumentationType.SWAGGER_2;

@EnableSwagger2WebFlux
@SpringBootApplication
@ComponentScan({"com.prigovor"})
public class ProductCompositeServiceApplication {

    @Value("${api.common.version}")
    String apiVersion;
    @Value("${api.common.title}")
    String apiTitle;
    @Value("${api.common.description}")
    String apiDescription;
    @Value("${api.common.termsOfServiceUrl}")
    String apiTermsOfServiceUrl;
    @Value("${api.common.license}")
    String apiLicense;
    @Value("${api.common.licenseUrl}")
    String apiLicenseUrl;
    @Value("${api.common.contact.name}")
    String apiContactName;
    @Value("${api.common.contact.url}")
    String apiContactUrl;
    @Value("${api.common.contact.email}")
    String apiContactEmail;

    public ProductCompositeServiceApplication(HealthAggregator healthAggregator, ProductCompositeIntegration integration) {
        this.healthAggregator = healthAggregator;
        this.integration = integration;
    }

    /**
     * Will exposed on $HOST:$PORT/swagger-ui.html
     *
     * @return {@code Docket}
     */
    @Bean
    public Docket apiDocumentation() {
        return new Docket(SWAGGER_2)
                .select()
                .apis(basePackage("com.prigovor"))
                .paths(PathSelectors.any())
                .build()
                .globalResponseMessage(POST, emptyList())
                .globalResponseMessage(GET, emptyList())
                .globalResponseMessage(DELETE, emptyList())
                .apiInfo(new ApiInfo(
                        apiTitle,
                        apiDescription,
                        apiVersion,
                        apiTermsOfServiceUrl,
                        new Contact(apiContactName, apiContactUrl, apiContactEmail),
                        apiLicense,
                        apiLicenseUrl,
                        emptyList()
                ));
    }

    final HealthAggregator healthAggregator;
    final ProductCompositeIntegration integration;

    @Bean
    ReactiveHealthIndicator coreServices() {
        ReactiveHealthIndicatorRegistry registry = new DefaultReactiveHealthIndicatorRegistry(new LinkedHashMap<>());
        registry.register("review", () -> integration.getReviewHealth());
        registry.register("product", () -> integration.getProductHealth());
        registry.register("recommendation", () -> integration.getRecommendationHealth());
        return new CompositeReactiveHealthIndicator(healthAggregator, registry);
    }

    public static void main(String[] args) {
        SpringApplication.run(ProductCompositeServiceApplication.class, args);
    }

}
