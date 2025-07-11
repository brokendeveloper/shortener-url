package tech.brokendeveloper.shortener_url.application;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tech.brokendeveloper.shortener_url.domain.url.dto.UrlRequestDTO;
import tech.brokendeveloper.shortener_url.domain.url.dto.UrlResponseDTO;
import tech.brokendeveloper.shortener_url.domain.url.useCases.GenerateShortUrlUseCase;

@RestController
@RequestMapping("/api/v1/urls")
public class UrlController {

    private final GenerateShortUrlUseCase generateShortUrlUseCase;

    public UrlController(GenerateShortUrlUseCase generateShortUrlUseCase) {
        this.generateShortUrlUseCase = generateShortUrlUseCase;
    }

    @PostMapping("/shorten")
    public ResponseEntity<UrlResponseDTO>shorten(@RequestBody UrlRequestDTO request) {
        UrlResponseDTO response = generateShortUrlUseCase.execute(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
