package tech.brokendeveloper.shortener_url.domain.url.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

public record UrlRequestDTO(
        @NotBlank(message = "The original URL cannot be empty")
        @Schema(description = "The original URL chosen by user", example = "https://www.exampleUrl.com")
        String originalUrl
) {
}
