package tech.brokendeveloper.shortener_url.domain.url.useCases;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;
import tech.brokendeveloper.shortener_url.api.v2.dto.ShortenUrlRequestDtoV2;
import tech.brokendeveloper.shortener_url.api.v2.dto.ShortenUrlResponseDtoV2;
import tech.brokendeveloper.shortener_url.domain.url.Url;
import tech.brokendeveloper.shortener_url.domain.url.UrlRepository;
import tech.brokendeveloper.shortener_url.exceptions.ShortUrlGenerationException;
import tech.brokendeveloper.shortener_url.utils.UrlBuilder;
import tech.brokendeveloper.shortener_url.utils.UuidShortCodeGenerator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class GenerateShortUrlUseCaseV2Test {

    @Mock
    private UrlRepository urlRepository;

    @Mock
    private UrlBuilder urlBuilder;

    @Mock
    private UuidShortCodeGenerator uuidShortCodeGenerator;

    @InjectMocks
    private GenerateShortUrlUseCaseV2 useCase;

    // 201 - created
    // Scenario 1: url created successfully
    @Test
    void shouldReturnShortUrlWhenGivenValidUrl() {
        // given
        String originalUrl = "https://www.brokendeveloper.com";
        String shortCode = "uuid123";
        String shortUrl = "https://localhost:8080/uuid123";
        ShortenUrlRequestDtoV2 request = new ShortenUrlRequestDtoV2(originalUrl);

        when(uuidShortCodeGenerator.generateUuidShortCode()).thenReturn(shortCode);
        when(urlBuilder.builderUrl(shortCode)).thenReturn(shortUrl);
        when(urlRepository.save(any(Url.class))).thenReturn(new Url());

        // when
        ShortenUrlResponseDtoV2 response = useCase.execute(request);

        // then
        assertEquals(shortUrl, response.shortenedUrl());
        verify(urlRepository).save(any(Url.class));
        verify(uuidShortCodeGenerator).generateUuidShortCode();
        verify(urlBuilder).builderUrl(shortCode);
    }

    // 201 - created
    // Scenario 2: short code collision occurs, retries and succeeds
    @Test
    void shouldRetryAndSucceedWhenShortCodeCollisionOccurs() {
        // given
        String originalUrl = "https://www.brokendeveloper.com";
        String shortCode1 = "uuid123";
        String shortCode2 = "uuid456";
        String shortUrl1 = "https://localhost:8080/uuid123";
        String shortUrl2 = "https://localhost:8080/uuid456";
        ShortenUrlRequestDtoV2 request = new ShortenUrlRequestDtoV2(originalUrl);

        when(uuidShortCodeGenerator.generateUuidShortCode())
                .thenReturn(shortCode1)
                .thenReturn(shortCode2);
        when(urlBuilder.builderUrl(shortCode1)).thenReturn(shortUrl1);
        when(urlBuilder.builderUrl(shortCode2)).thenReturn(shortUrl2);

        when(urlRepository.save(any(Url.class)))
                .thenThrow(new DataIntegrityViolationException("Collision. ShortCode is duplicated"))
                .thenReturn(new Url());

        // when
        ShortenUrlResponseDtoV2 response = useCase.execute(request);

        // then
        assertEquals(shortUrl2, response.shortenedUrl());
        verify(urlRepository, times(2)).save(any(Url.class));
        verify(uuidShortCodeGenerator, times(2)).generateUuidShortCode();
        verify(urlBuilder).builderUrl(shortCode1);
        verify(urlBuilder).builderUrl(shortCode2);
    }

    // 500 - internal server error
    // Scenario 3: all short code generation attempts fail, throws exception
    @Test
    void shouldThrowExceptionWhenAllShortCodesAttemptsFail() {
        // given
        String originalUrl = "https://www.brokendeveloper.com";
        String shortCode1 = "uuid123";
        String shortCode2 = "uuid456";
        String shortCode3 = "uuid789";
        String shortUrl1 = "https://localhost:8080/uuid123";
        ShortenUrlRequestDtoV2 request = new ShortenUrlRequestDtoV2(originalUrl);

        when(uuidShortCodeGenerator.generateUuidShortCode())
                .thenReturn(shortCode1)
                .thenReturn(shortCode2)
                .thenReturn(shortCode3);
        when(urlBuilder.builderUrl(shortCode1)).thenReturn(shortUrl1);

        when(urlRepository.save(any(Url.class)))
                .thenThrow(new DataIntegrityViolationException("Collision. ShortCode is duplicated"))
                .thenThrow(new DataIntegrityViolationException("Collision. ShortCode is duplicated"))
                .thenThrow(new DataIntegrityViolationException("Collision. ShortCode is duplicated"));

        // when & then
        assertThrows(ShortUrlGenerationException.class, () -> useCase.execute(request));
        verify(urlRepository, times(3)).save(any(Url.class));
        verify(uuidShortCodeGenerator, times(3)).generateUuidShortCode();
    }

}
