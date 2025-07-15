package tech.brokendeveloper.shortener_url.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tech.brokendeveloper.shortener_url.dto.urlAccess.UrlAccessResponseDto;
import tech.brokendeveloper.shortener_url.repositories.UrlAccessRepository;
import tech.brokendeveloper.shortener_url.repositories.UrlRepository;
import tech.brokendeveloper.shortener_url.services.UrlAccessService;

@RestController
@RequestMapping("/api/urls")
public class UrlAccessController {

    private final UrlRepository urlRepository;
    private final UrlAccessRepository urlAccessRepository;
    private final UrlAccessService urlAccessService;


    public UrlAccessController(UrlRepository urlRepository, UrlAccessRepository urlAccessRepository, UrlAccessService urlAccessService) {
        this.urlRepository = urlRepository;
        this.urlAccessRepository = urlAccessRepository;
        this.urlAccessService = urlAccessService;
    }

    @GetMapping("/{shortCode}/access")
    public ResponseEntity<UrlAccessResponseDto> getStats(@PathVariable String shortCode) {
        UrlAccessResponseDto response = urlAccessService.execute(shortCode);
        return ResponseEntity.ok(response);
    }

}
