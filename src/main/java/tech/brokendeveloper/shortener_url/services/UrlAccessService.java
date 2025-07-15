package tech.brokendeveloper.shortener_url.services;

import org.springframework.stereotype.Service;
import tech.brokendeveloper.shortener_url.domain.Url;
import tech.brokendeveloper.shortener_url.dto.urlAccess.UrlAccessResponseDto;
import tech.brokendeveloper.shortener_url.exceptions.ShortCodeNotFoundException;
import tech.brokendeveloper.shortener_url.repositories.UrlAccessRepository;
import tech.brokendeveloper.shortener_url.repositories.UrlRepository;

@Service
public class UrlAccessService {

    private final UrlRepository urlRepository;
    private final UrlAccessRepository urlAccessRepository;

    public UrlAccessService(UrlRepository urlRepository, UrlAccessRepository urlAccessRepository) {
        this.urlRepository = urlRepository;
        this.urlAccessRepository = urlAccessRepository;
    }

    public UrlAccessResponseDto execute(String shortCode) {
        Url url = urlRepository.findByShortCode(shortCode)
                .orElseThrow(() -> new ShortCodeNotFoundException(shortCode));

        long totalAccesses = urlAccessRepository.countByUrl(url);
        double avgPerDay = 0.0;

        if (totalAccesses > 0) {
            Double avg = urlAccessRepository.avgPerDay(url.getId());
            avgPerDay = avg != null ? avg : 0.0;
        }

        return new UrlAccessResponseDto(totalAccesses, avgPerDay);
    }
}
