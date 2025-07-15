package tech.brokendeveloper.shortener_url.services;

import org.springframework.stereotype.Service;
import tech.brokendeveloper.shortener_url.domain.Url;
import tech.brokendeveloper.shortener_url.repositories.UrlRepository;
import tech.brokendeveloper.shortener_url.exceptions.ShortCodeNotFoundException;

@Service
public class FindOriginalUrlService {

    private final UrlRepository urlRepository;

    public FindOriginalUrlService(UrlRepository urlRepository) {
        this.urlRepository = urlRepository;
    }

    public String execute(String shortCode) {
        return urlRepository.findByShortCode(shortCode)
                .map(Url::getOriginalUrl)
                .orElseThrow(() -> new ShortCodeNotFoundException(shortCode));
    }
}
