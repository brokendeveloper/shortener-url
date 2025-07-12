package tech.brokendeveloper.shortener_url.domain.url.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public record UrlResponseDTO(
        @Schema(description = "Short URL after all shortener process", example = "https://localhost:PortExample/shortCode")
        String shortenedUrl
) {
}
