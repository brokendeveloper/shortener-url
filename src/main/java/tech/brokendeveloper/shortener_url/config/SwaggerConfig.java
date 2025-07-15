package tech.brokendeveloper.shortener_url.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("URL Shortener API")
                        .version("1.0")
                        .description("API that turns a URL into a shortened URL")
                        .contact(new Contact()
                                .name("Luccas Fernandes - Broken Developer")
                                .email("contatoluccasf9@gmail.com")
                                .url("https://github.com/brokendeveloper/shortener-url")
                        )
                        .license(new License()
                                .name("MIT License")
                                .url("https://opensource.org/licenses/MIT")
                        )
                )
                .servers(List.of(
                        new Server().url("http://localhost:8080").description("Local server")
                ));
    }
    }

