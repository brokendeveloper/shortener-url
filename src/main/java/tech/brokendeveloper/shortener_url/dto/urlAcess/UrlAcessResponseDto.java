package tech.brokendeveloper.shortener_url.dto.urlAcess;

public record UrlAcessResponseDto(
        long totalAccesses,
        double avgPerDay
) {
}
