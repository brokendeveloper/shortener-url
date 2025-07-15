package tech.brokendeveloper.shortener_url.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import tech.brokendeveloper.shortener_url.domain.Url;
import tech.brokendeveloper.shortener_url.repositories.UrlRepository;
import tech.brokendeveloper.shortener_url.dto.v1.ShortenUrlRequestDtoV1;
import tech.brokendeveloper.shortener_url.dto.v1.ShortenUrlResponseDtoV1;
import tech.brokendeveloper.shortener_url.exceptions.ShortUrlGenerationException;
import tech.brokendeveloper.shortener_url.utils.SecureStringShortCodeGenerator;
import tech.brokendeveloper.shortener_url.utils.UrlBuilder;

@Service
public class GenerateShortUrlServiceV1 {

    private static final Logger logger = LoggerFactory.getLogger(GenerateShortUrlServiceV1.class);

    private final UrlRepository urlRepository;
    private final UrlBuilder urlBuilder;
    private final SecureStringShortCodeGenerator secureStringShortCodeGenerator;

    public GenerateShortUrlServiceV1(UrlRepository urlRepository, UrlBuilder urlBuilder, SecureStringShortCodeGenerator secureStringShortCodeGenerator) {
        this.urlRepository = urlRepository;
        this.urlBuilder = urlBuilder;
        this.secureStringShortCodeGenerator = secureStringShortCodeGenerator;
    }


    public ShortenUrlResponseDtoV1 execute(ShortenUrlRequestDtoV1 request) {

        int maxAttempts = 3;
        for (int attempt = 0; attempt < maxAttempts; attempt++) {

            String newShortCode = secureStringShortCodeGenerator.generateUrlString();
            String newShortUrl = urlBuilder.builderUrl(newShortCode);

            Url urlEntity = new Url();
            urlEntity.setOriginalUrl(request.originalUrl());
            urlEntity.setShortenedUrl(newShortUrl);
            urlEntity.setShortCode(newShortCode);

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
