package tech.brokendeveloper.shortener_url.api.v1.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public record ShortenUrlResponseDtoV1(
        @Schema(description = "Short URL after all shortener process", example = "https://localhost:PortExample/StringShortCode")
        String shortenedUrl
) {
}
