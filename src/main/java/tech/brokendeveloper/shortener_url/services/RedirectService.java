package tech.brokendeveloper.shortener_url.services;

import org.springframework.stereotype.Service;
import tech.brokendeveloper.shortener_url.domain.Url;
import tech.brokendeveloper.shortener_url.domain.UrlAccess;
import tech.brokendeveloper.shortener_url.repositories.UrlAccessRepository;

import java.time.LocalDateTime;

@Service
public class RedirectService {

    private final FindOriginalUrlService findOriginalUrlService;
    private final UrlAccessRepository urlAccessRepository;

    public RedirectService(FindOriginalUrlService findOriginalUrlService, UrlAccessRepository urlAccessRepository) {
        this.findOriginalUrlService = findOriginalUrlService;
        this.urlAccessRepository = urlAccessRepository;
    }

    public String getOriginalUrlAndRegisterAccess(String shortCode) {
        // Busca a entidade Url
        Url url = findOriginalUrlService.findEntity(shortCode);

        // Registra o acesso
        urlAccessRepository.save(new UrlAccess(url, LocalDateTime.now()));

        // Retorna a URL original

        String originalUrl = url.getOriginalUrl();
        return originalUrl;
    }
}
