package tech.brokendeveloper.shortener_url.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import tech.brokendeveloper.shortener_url.dto.v2.ShortenUrlRequestDtoV2;
import tech.brokendeveloper.shortener_url.dto.v2.ShortenUrlResponseDtoV2;
import tech.brokendeveloper.shortener_url.domain.Url;
import tech.brokendeveloper.shortener_url.repositories.UrlRepository;
import tech.brokendeveloper.shortener_url.exceptions.ShortUrlGenerationException;
import tech.brokendeveloper.shortener_url.utils.UrlBuilder;
import tech.brokendeveloper.shortener_url.utils.NanoIdShortCodeGenerator;

@Service
public class GenerateShortUrlServiceV2 {

    private static final Logger logger = LoggerFactory.getLogger(GenerateShortUrlServiceV2.class);

    private final UrlRepository urlRepository;
    private final UrlBuilder urlBuilder;
    private final NanoIdShortCodeGenerator nanoIdShortCodeGenerator;

    public GenerateShortUrlServiceV2(UrlRepository urlRepository, UrlBuilder urlBuilder, NanoIdShortCodeGenerator nanoIdShortCodeGenerator){
        this.urlRepository = urlRepository;
        this.urlBuilder = urlBuilder;
        this.nanoIdShortCodeGenerator = nanoIdShortCodeGenerator;
    }

    public ShortenUrlResponseDtoV2 execute(ShortenUrlRequestDtoV2 request){

        int maxAttempts = 3;
        for(int attempt = 0; attempt < maxAttempts; attempt++){

            String newShortCode = nanoIdShortCodeGenerator.generateNanoIdShortCode();
            String newShortUrl = urlBuilder.builderUrl(newShortCode);

            Url urlEntity = new Url();
            urlEntity.setOriginalUrl(request.originalUrl());
            urlEntity.setShortenedUrl(newShortUrl);
            urlEntity.setShortCode(newShortCode);

            try{
                urlRepository.save(urlEntity);
                return new ShortenUrlResponseDtoV2(newShortUrl);

            }catch (DataIntegrityViolationException e){
                logger.warn("Short code collision detected for code '{}'. Attempt {}", newShortCode, attempt);
            }
        }
        throw new ShortUrlGenerationException("Failed to generate shortened url after " + maxAttempts + " attempts.");
    }
}
