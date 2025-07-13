package tech.brokendeveloper.shortener_url.domain.url.useCases;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.web.util.InvalidUrlException;
import tech.brokendeveloper.shortener_url.domain.url.Url;
import tech.brokendeveloper.shortener_url.domain.url.UrlRepository;
import tech.brokendeveloper.shortener_url.domain.url.dto.UrlRequestDTO;
import tech.brokendeveloper.shortener_url.domain.url.dto.UrlResponseDTO;
import tech.brokendeveloper.shortener_url.exceptions.ShortUrlGenerationException;
import tech.brokendeveloper.shortener_url.utils.SecureGenerateUrlString;
import tech.brokendeveloper.shortener_url.utils.UrlBuilder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class GenerateShortUrlUseCaseTest {

    @Mock
    private UrlRepository urlRepository;

    @Mock
    private UrlBuilder urlBuilder;

    @Mock
    private SecureGenerateUrlString secureGenerateUrlString;

    @InjectMocks
    private GenerateShortUrlUseCase useCase;

    // 201 - created
    // Scenario 1: url created successfully
    @Test
    void shouldReturnShortUrlWhenGivenValidUrl() {
        // given
        String originalUrl = "https://www.brokendeveloper.com";
        String shortCode = "abc123";
        String shortUrl = "https://localhost:8080/abc123";
        UrlRequestDTO request = new UrlRequestDTO(originalUrl);

        when(secureGenerateUrlString.generateUrlString()).thenReturn(shortCode);
        when(urlBuilder.builderUrl(shortCode)).thenReturn(shortUrl);
        when(urlRepository.save(any(Url.class))).thenReturn(new Url());

        // when
        UrlResponseDTO response = useCase.execute(request);

        // then
        assertEquals(shortUrl, response.shortenedUrl());
        verify(urlRepository).save(any(Url.class));
        verify(secureGenerateUrlString).generateUrlString();
        verify(urlBuilder).builderUrl(shortCode);

    }



    @Test
    void ShouldRetryAndSucceedWhenShortCodeCollisionOccurs(){
        // given
        String originalUrl = "https://www.brokendeveloper.com";
        String shortCode1 = "abc123";
        String shortCode2 = "def456";
        String shortUrl1 = "https://localhost:8080/abc123";
        String shortUrl2 = "https://localhost:8080/def456";
        UrlRequestDTO request = new UrlRequestDTO(originalUrl);



        // first attempt = shortCode1, second = shortCode2
        when(secureGenerateUrlString.generateUrlString())
                .thenReturn(shortCode1)
                .thenReturn(shortCode2);
        when(urlBuilder.builderUrl(shortCode1)).thenReturn(shortUrl1);
        when(urlBuilder.builderUrl(shortCode2)).thenReturn(shortUrl2);

        // first attempt = collision, second = success
        when(urlRepository.save(any(Url.class)))
                .thenThrow(new DataIntegrityViolationException("Collision. ShortCode is duplicated"))
                .thenReturn(new Url());

        // when
        UrlResponseDTO response = useCase.execute(request);

        // then
        assertEquals(shortUrl2, response.shortenedUrl());
        verify(urlRepository, times(2)).save(any(Url.class));
        verify(secureGenerateUrlString, times(2)).generateUrlString();
        verify(urlBuilder).builderUrl(shortCode1);
        verify(urlBuilder).builderUrl(shortCode2);

    }

    @Test
    void shouldThrowExceptionWhenAllShortCodesAttemptsFail() {
        //given
        String originalUrl = "https://www.brokendeveloper.com";
        String shortCode1 = "abc123";
        String shortCode2 = "def456";
        String shortCode3 = "ghi789";
        String shortUrl1 = "https://localhost:8080/abc123";
        UrlRequestDTO request = new UrlRequestDTO(originalUrl);

        when(secureGenerateUrlString.generateUrlString())
                .thenReturn(shortCode1)
                .thenReturn(shortCode2)
                .thenReturn(shortCode3);
        when(urlBuilder.builderUrl(shortCode1)).thenReturn(shortUrl1);

        // all attempts fail
        when(urlRepository.save(any(Url.class)))
                .thenThrow(new DataIntegrityViolationException("Collision. ShortCode is duplicated"))
                .thenThrow(new DataIntegrityViolationException("Collision. ShortCode is duplicated"))
                .thenThrow(new DataIntegrityViolationException("Collision. ShortCode is duplicated"));

        // when & then
        assertThrows(ShortUrlGenerationException.class, () -> useCase.execute(request));
        verify(urlRepository, times(3)).save(any(Url.class));
        verify(secureGenerateUrlString, times(3)).generateUrlString();
    }




}
