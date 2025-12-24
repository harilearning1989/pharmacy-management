package com.web.pharma.config;

import org.springdoc.core.properties.SwaggerUiConfigParameters;
import org.springframework.context.annotation.Configuration;
import jakarta.annotation.PostConstruct;

@Configuration
public class SwaggerUiConfig {

    private final SwaggerUiConfigParameters swaggerUiConfigParameters;

    public SwaggerUiConfig(SwaggerUiConfigParameters swaggerUiConfigParameters) {
        this.swaggerUiConfigParameters = swaggerUiConfigParameters;
    }

    @PostConstruct
    public void hideSchemas() {
        swaggerUiConfigParameters.setDefaultModelsExpandDepth(-1);
    }
}