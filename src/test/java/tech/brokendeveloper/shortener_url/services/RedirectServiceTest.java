package tech.brokendeveloper.shortener_url.services;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tech.brokendeveloper.shortener_url.domain.Url;
import tech.brokendeveloper.shortener_url.domain.UrlAccess;
import tech.brokendeveloper.shortener_url.exceptions.ShortCodeNotFoundException;
import tech.brokendeveloper.shortener_url.repositories.UrlAccessRepository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RedirectServiceTest {

    @Mock
    private FindOriginalUrlService findOriginalUrlService;

    @Mock
    private UrlAccessRepository urlAccessRepository;

    @InjectMocks
    private RedirectService redirectService;

    @Test
    void shouldRegisterAccessAndReturnOriginalUrl() {
        // given
        String shortCode = "oRGXi4e";
        String originalUrl = "https://www.notion.com/pt";
        Url url = new Url();
        url.setShortCode(shortCode);
        url.setOriginalUrl(originalUrl);

        when(findOriginalUrlService.findEntity(shortCode)).thenReturn(url);

        // when
        String result = redirectService.getOriginalUrlAndRegisterAccess(shortCode);

        // then
        assertEquals(originalUrl, result);
        verify(findOriginalUrlService).findEntity(shortCode);
        verify(urlAccessRepository).save(any(UrlAccess.class));
    }

    @Test
    void shouldThrowExceptionWhenShortCodeNotFound() {
        // given
        String shortCode = "notfound";
        when(findOriginalUrlService.findEntity(shortCode)).thenThrow(new ShortCodeNotFoundException(shortCode));

        // when & then
        assertThrows(ShortCodeNotFoundException.class, () -> redirectService.getOriginalUrlAndRegisterAccess(shortCode));
        verify(findOriginalUrlService).findEntity(shortCode);
        verifyNoInteractions(urlAccessRepository);
    }

}
