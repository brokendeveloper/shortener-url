package tech.brokendeveloper.shortener_url.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tech.brokendeveloper.shortener_url.dto.v1.ShortenUrlRequestDtoV1;
import tech.brokendeveloper.shortener_url.dto.v1.ShortenUrlResponseDtoV1;
import tech.brokendeveloper.shortener_url.services.GenerateShortUrlServiceV1;

@RestController
@RequestMapping("/api/v1/urls")
@Tag(name = "URLs", description = "Endpoints about the URLs - version 1")
public class ShortenUrlControllerV1 {

    private final GenerateShortUrlServiceV1 generateShortUrlServiceV1;

    public ShortenUrlControllerV1(GenerateShortUrlServiceV1 generateShortUrlServiceV1) {
        this.generateShortUrlServiceV1 = generateShortUrlServiceV1;
    }

    @PostMapping("/shorten")
    @Operation(
            summary = "Create a shortened URL (v1)",
            description = "Receives an original URL and returns a shortened version using a String generator short code."
    )
    @ApiResponses(
            {
                    @ApiResponse(responseCode = "201",
                            description = "Short URL created successfully",
                            content = {
                            @Content(schema = @Schema(implementation = ShortenUrlResponseDtoV1.class))
                    }),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Invalid input data",
                            content = @Content(schema = @Schema(example = "{\"message\": \"Invalid URL\"}"))
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Internal server error",
                            content = @Content(schema = @Schema(example = "{\"message\": \"Failed to generate shortened url after 3 attempts.\"}"))
                    )
            }
    )
    public ResponseEntity<ShortenUrlResponseDtoV1>shorten(@RequestBody @Valid ShortenUrlRequestDtoV1 request) {
        ShortenUrlResponseDtoV1 response = generateShortUrlServiceV1.execute(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
