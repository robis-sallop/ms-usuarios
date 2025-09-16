package cl.robis.sallop.ms.usuarios.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.responses.ApiResponse;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(OpenApiProperties.class)
public class OpenApiConfig {
    @Bean
    public OpenAPI apiInfo(OpenApiProperties properties) {
        return new OpenAPI()
                .info(new Info()
                        .title(properties.title())
                        .description(properties.description())
                        .version(properties.version())
                        .contact(new Contact()
                                .name(properties.contact().name())
                                .email(properties.contact().email())
                        )
                );
    }

    @Bean
    public ApiResponse apiResponse() { return new ApiResponse(); }
}
