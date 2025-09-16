package cl.robis.sallop.ms.usuarios.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "swagger.info")
public record OpenApiProperties(
    String version,
    String name,
    String title,
    String description,
    OpenApiContactProperties contact){}