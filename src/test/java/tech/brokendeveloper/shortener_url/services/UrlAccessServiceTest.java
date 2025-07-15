package tech.brokendeveloper.shortener_url.services;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tech.brokendeveloper.shortener_url.domain.Url;
import tech.brokendeveloper.shortener_url.dto.urlAccess.UrlAccessResponseDto;
import tech.brokendeveloper.shortener_url.exceptions.ShortCodeNotFoundException;
import tech.brokendeveloper.shortener_url.repositories.UrlAccessRepository;
import tech.brokendeveloper.shortener_url.repositories.UrlRepository;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UrlAccessServiceTest {

    @Mock
    private UrlRepository urlRepository;

    @Mock
    private UrlAccessRepository urlAccessRepository;

    @InjectMocks
    private UrlAccessService urlAccessService;

    @Test
    void shouldReturnStatsWhenAccessesExist() {
        // given
        String shortCode = "oRGXi4e";
        UUID urlId = UUID.randomUUID();
        Url url = new Url();
        url.setId(urlId);
        url.setShortCode(shortCode);

        when(urlRepository.findByShortCode(shortCode)).thenReturn(Optional.of(url));
        when(urlAccessRepository.countByUrl(url)).thenReturn(10L);
        when(urlAccessRepository.avgPerDay(urlId)).thenReturn(2.5);

        // when
        UrlAccessResponseDto result = urlAccessService.execute(shortCode);

        // then
        assertEquals(10L, result.totalAccesses());
        assertEquals(2.5, result.avgPerDay());
        verify(urlRepository).findByShortCode(shortCode);
        verify(urlAccessRepository).countByUrl(url);
        verify(urlAccessRepository).avgPerDay(urlId);
    }

    @Test
    void shouldReturnZeroStatsWhenNoAccesses() {
        // given
        String shortCode = "oRGXi4e";
        UUID urlId = UUID.randomUUID();
        Url url = new Url();
        url.setId(urlId);
        url.setShortCode(shortCode);

        when(urlRepository.findByShortCode(shortCode)).thenReturn(Optional.of(url));
        when(urlAccessRepository.countByUrl(url)).thenReturn(0L);

        // when
        UrlAccessResponseDto result = urlAccessService.execute(shortCode);

        // then
        assertEquals(0L, result.totalAccesses());
        assertEquals(0.0, result.avgPerDay());
        verify(urlRepository).findByShortCode(shortCode);
        verify(urlAccessRepository).countByUrl(url);
        verify(urlAccessRepository, never()).avgPerDay(any());
    }

    @Test
    void shouldThrowExceptionWhenShortCodeNotFound() {
        // given
        String shortCode = "notfound";
        when(urlRepository.findByShortCode(shortCode)).thenReturn(Optional.empty());

        // when & then
        assertThrows(ShortCodeNotFoundException.class, () -> urlAccessService.execute(shortCode));
        verify(urlRepository).findByShortCode(shortCode);
        verifyNoInteractions(urlAccessRepository);
    }
}
