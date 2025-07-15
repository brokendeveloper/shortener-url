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
import org.springframework.web.bind.annotation.RestController;
import tech.brokendeveloper.shortener_url.services.FindOriginalUrlService;
import tech.brokendeveloper.shortener_url.exceptions.dto.ErrorResponseDto;

@RestController
@Tag(name = "Redirect", description = "Endpoint for redirecting shortened URLs")
public class RedirectController {

    private final FindOriginalUrlService findOriginalUrlService;

    public RedirectController(FindOriginalUrlService findOriginalUrlService) {
        this.findOriginalUrlService = findOriginalUrlService;
    }


    @GetMapping("/{shortCode}")
    @Operation(
            summary = "Redirect to the original URL",
            description = "Given a short code, redirects the user to the original registered URL."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "302",
                    description = "Redirect to the original URL"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Short code not found",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))
            )
    })
    public ResponseEntity<Void> redirect(
            @Parameter(description = "Short code of the shortened URL", example = "oRGXi4e")
            @PathVariable String shortCode
    ) {
        String originalUrl = findOriginalUrlService.execute(shortCode);
        return ResponseEntity.status(302)
                .header("Location", originalUrl)
                .build();
    }

}
