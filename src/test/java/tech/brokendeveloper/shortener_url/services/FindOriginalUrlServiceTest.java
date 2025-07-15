package tech.brokendeveloper.shortener_url.services;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tech.brokendeveloper.shortener_url.domain.Url;
import tech.brokendeveloper.shortener_url.exceptions.ShortCodeNotFoundException;
import tech.brokendeveloper.shortener_url.repositories.UrlRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class FindOriginalUrlServiceTest {

    @Mock
    private UrlRepository urlRepository;

    @InjectMocks
    private FindOriginalUrlService findOriginalUrlService;

    @Test
    void shouldReturnOriginalUrlWhenShortCodeExists(){
        // given
        String shortCode = "oRGXi4e";
        String originalUrl = "https://www.notion.com/pt";
        Url url = new Url();
        url.setShortCode(shortCode);
        url.setOriginalUrl(originalUrl);

        when(urlRepository.findByShortCode(shortCode)).thenReturn(Optional.of(url));

        // when
        String result = findOriginalUrlService.execute(shortCode);

        // then
        assertEquals(originalUrl, result);
        verify(urlRepository).findByShortCode(shortCode);
    }

    @Test
    void shouldThrowExceptionWhenShortCodeDoesNotExist(){
        // given
        String shortCode = "notfound";
        when(urlRepository.findByShortCode(shortCode)).thenReturn(Optional.empty());

        // when & then
        assertThrows(ShortCodeNotFoundException.class, () -> findOriginalUrlService.execute(shortCode));
        verify(urlRepository).findByShortCode(shortCode);
    }
    }

