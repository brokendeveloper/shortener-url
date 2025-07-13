package tech.brokendeveloper.shortener_url.domain.url.useCases;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import tech.brokendeveloper.shortener_url.domain.url.Url;
import tech.brokendeveloper.shortener_url.domain.url.UrlRepository;
import tech.brokendeveloper.shortener_url.api.v1.dto.ShortenUrlRequestDtoV1;
import tech.brokendeveloper.shortener_url.api.v1.dto.ShortenUrlResponseDtoV1;
import tech.brokendeveloper.shortener_url.exceptions.ShortUrlGenerationException;
import tech.brokendeveloper.shortener_url.utils.SecureGenerateUrlString;
import tech.brokendeveloper.shortener_url.utils.UrlBuilder;

@Service
public class GenerateShortUrlUseCaseV1 {

    private static final Logger logger = LoggerFactory.getLogger(GenerateShortUrlUseCaseV1.class);

    private final UrlRepository urlRepository;
    private final UrlBuilder urlBuilder;
    private final SecureGenerateUrlString secureGenerateURLString;

    public GenerateShortUrlUseCaseV1(UrlRepository urlRepository, UrlBuilder urlBuilder, SecureGenerateUrlString secureGenerateURLString) {
        this.urlRepository = urlRepository;
        this.urlBuilder = urlBuilder;
        this.secureGenerateURLString = secureGenerateURLString;
    }


    public ShortenUrlResponseDtoV1 execute(ShortenUrlRequestDtoV1 request) {

        int maxAttempts = 3;
        for (int attempt = 0; attempt < maxAttempts; attempt++) {

            String newShortCode = secureGenerateURLString.generateUrlString();
            String newShortUrl = urlBuilder.builderUrl(newShortCode);

            Url urlEntity = new Url();
            urlEntity.setOriginalUrl(request.originalUrl());
            urlEntity.setShortenedUrl(newShortUrl);

            try {
                urlEntity = urlRepository.save(urlEntity);
                return new ShortenUrlResponseDtoV1(newShortUrl);

            }catch (DataIntegrityViolationException e){
                logger.warn("Short code collision detected for code '{}'. Attempt {}", newShortCode, attempt);
            }
        }
        throw new ShortUrlGenerationException("Failed to generate shortened url after " + maxAttempts + " attempts.");
    }
}
