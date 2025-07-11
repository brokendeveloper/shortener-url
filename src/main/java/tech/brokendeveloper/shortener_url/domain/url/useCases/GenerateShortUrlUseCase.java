package tech.brokendeveloper.shortener_url.domain.url.useCases;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.web.util.InvalidUrlException;
import tech.brokendeveloper.shortener_url.domain.url.Url;
import tech.brokendeveloper.shortener_url.domain.url.UrlRepository;
import tech.brokendeveloper.shortener_url.domain.url.dto.UrlRequestDTO;
import tech.brokendeveloper.shortener_url.domain.url.dto.UrlResponseDTO;
import tech.brokendeveloper.shortener_url.exceptions.ShortUrlGenerationException;
import tech.brokendeveloper.shortener_url.utils.SecureGenerateUrlString;
import tech.brokendeveloper.shortener_url.utils.UrlBuilder;
import tech.brokendeveloper.shortener_url.utils.ValidateUrl;

@Service
public class GenerateShortUrlUseCase {

    private static final Logger logger = LoggerFactory.getLogger(GenerateShortUrlUseCase.class);

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

        int maxAttempts = 3;
        for (int attempt = 0; attempt < maxAttempts; attempt++) {

            String newShortCode = secureGenerateURLString.generateUrlString();
            String newShortUrl = urlBuilder.builderUrl(newShortCode);

            Url urlEntity = new Url();
            urlEntity.setOriginalUrl(request.originalUrl());
            urlEntity.setShortenedUrl(newShortUrl);

            try {
                urlEntity = urlRepository.save(urlEntity);
                return new UrlResponseDTO(newShortUrl);

            }catch (DataIntegrityViolationException e){
                logger.warn("Short code collision detected. Attempt {}", attempt);
            }
        }
        throw new ShortUrlGenerationException("Failed to generate shortened url after " + maxAttempts + " attempts.");
    }
}
