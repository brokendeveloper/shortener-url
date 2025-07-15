package tech.brokendeveloper.shortener_url.dto.v2;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import org.hibernate.validator.constraints.URL;

public record ShortenUrlRequestDtoV2(
        @URL
        @Pattern(regexp = "^(http|https)://.*$", message = "URL needs to start with http or https")
        @NotBlank(message = "The original URL cannot be empty")
        @Schema(description = "The original URL chosen by user", example = "https://www.exampleUrl.com")
        String originalUrl
) {
}
