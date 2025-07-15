package tech.brokendeveloper.shortener_url.dto.urlAccess;

import io.swagger.v3.oas.annotations.media.Schema;

public record UrlAccessResponseDto(
        @Schema(description = "Total number of accesses for the shortened URL", example = "42")
        long totalAccesses,
        @Schema(description = "Average number of accesses per day", example = "3.5")
        double avgPerDay
) {
}
