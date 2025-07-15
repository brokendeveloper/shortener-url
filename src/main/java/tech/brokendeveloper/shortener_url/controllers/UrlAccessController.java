package tech.brokendeveloper.shortener_url.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tech.brokendeveloper.shortener_url.dto.urlAccess.UrlAccessResponseDto;
import tech.brokendeveloper.shortener_url.exceptions.dto.ErrorResponseDto;
import tech.brokendeveloper.shortener_url.repositories.UrlAccessRepository;
import tech.brokendeveloper.shortener_url.repositories.UrlRepository;
import tech.brokendeveloper.shortener_url.services.UrlAccessService;

@RestController
@RequestMapping("/api/urls")
@Tag(name = "URL Access", description = "Endpoints for URL access statistics")
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
    @Operation(
            summary = "Get access statistics for a shortened URL",
            description = "Returns the total number of accesses and the average accesses per day for the given short code."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Statistics found",
                    content = @Content(schema = @Schema(implementation = UrlAccessResponseDto.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Short code not found",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))
            )
    })
    public ResponseEntity<UrlAccessResponseDto> getStats(
            @Parameter(description = "Short code of the shortened URL", example = "oRGXi4e")
            @PathVariable String shortCode
    ) {
        UrlAccessResponseDto response = urlAccessService.execute(shortCode);
        return ResponseEntity.ok(response);
    }

}
