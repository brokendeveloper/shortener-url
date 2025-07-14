package tech.brokendeveloper.shortener_url.domain.url.useCases;

import org.springframework.stereotype.Service;
import tech.brokendeveloper.shortener_url.domain.url.Url;
import tech.brokendeveloper.shortener_url.domain.url.UrlRepository;

@Service
public class FindOriginalUrlUseCase {

    private final UrlRepository urlRepository;

    public FindOriginalUrlUseCase(UrlRepository urlRepository) {
        this.urlRepository = urlRepository;
    }

    public String execute(String shortCode) {
        return urlRepository.findByShortCode(shortCode)
                .map(Url::getOriginalUrl)
                .orElseThrow(() -> new ShortUrlNotFoundException(shortCode));
    }
}
