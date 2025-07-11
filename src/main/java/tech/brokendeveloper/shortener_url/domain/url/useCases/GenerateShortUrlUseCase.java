package tech.brokendeveloper.shortener_url.domain.url.useCases;

import org.springframework.stereotype.Service;
import org.springframework.web.util.InvalidUrlException;
import tech.brokendeveloper.shortener_url.domain.url.Url;
import tech.brokendeveloper.shortener_url.domain.url.UrlRepository;
import tech.brokendeveloper.shortener_url.domain.url.dto.UrlRequestDTO;
import tech.brokendeveloper.shortener_url.domain.url.dto.UrlResponseDTO;
import tech.brokendeveloper.shortener_url.utils.SecureGenerateUrlString;
import tech.brokendeveloper.shortener_url.utils.UrlBuilder;
import tech.brokendeveloper.shortener_url.utils.ValidateUrl;

@Service
public class GenerateShortUrlUseCase {

    private final UrlRepository urlRepository;
    private final UrlBuilder urlBuilder;
    private final SecureGenerateUrlString secureGenerateURLString;
    private final ValidateUrl validateUrl;

    public GenerateShortUrlUseCase(UrlRepository urlRepository, UrlBuilder urlBuilder, SecureGenerateUrlString secureGenerateURLString, ValidateUrl validateUrl) {
        this.urlRepository = urlRepository;
        this.urlBuilder = urlBuilder;
        this.secureGenerateURLString = secureGenerateURLString;
        this.validateUrl = validateUrl;
    }


    public UrlResponseDTO execute(UrlRequestDTO request) {
        if (!validateUrl.isValidUrl(request.originalUrl())) {
            throw new InvalidUrlException("Invalid URL");
        }

        String newShortCode = secureGenerateURLString.generateUrlString();

        String newShortUrl = urlBuilder.builderUrl(newShortCode);

        Url urlEntity = new Url();
        urlEntity.setOriginalUrl(request.originalUrl());
        urlEntity.setShortenedUrl(newShortUrl);

        urlRepository.save(urlEntity);

        return new UrlResponseDTO(newShortUrl);
    }
}
