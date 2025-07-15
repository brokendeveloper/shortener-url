package tech.brokendeveloper.shortener_url.dto.urlAccess;

public record UrlAccessResponseDto(
        long totalAccesses,
        double avgPerDay
) {
}
