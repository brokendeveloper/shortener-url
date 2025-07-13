package tech.brokendeveloper.shortener_url.api.v2.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public record ShortenUrlResponseV2(
        @Schema(description = "Short URL after all shortener process", example = "https://localhost:PortExample/shortCode")
        String shortenedUrl
) {
}
