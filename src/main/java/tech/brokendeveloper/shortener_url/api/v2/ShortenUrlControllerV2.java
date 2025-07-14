package tech.brokendeveloper.shortener_url.api.v2;

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
import tech.brokendeveloper.shortener_url.api.v1.dto.ShortenUrlResponseDtoV1;
import tech.brokendeveloper.shortener_url.api.v2.dto.ShortenUrlRequestDtoV2;
import tech.brokendeveloper.shortener_url.api.v2.dto.ShortenUrlResponseDtoV2;
import tech.brokendeveloper.shortener_url.domain.url.useCases.GenerateShortUrlUseCaseV2;

@RestController
@RequestMapping("/api/v2/urls")
@Tag(name = "URLs v2", description = "Endpoints for URL shortening - version 2")
public class ShortenUrlControllerV2 {

    private final GenerateShortUrlUseCaseV2 generateShortUrlUseCaseV2;


    public ShortenUrlControllerV2(GenerateShortUrlUseCaseV2 generateShortUrlUseCaseV2) {
        this.generateShortUrlUseCaseV2 = generateShortUrlUseCaseV2;
    }

    @PostMapping("/shorten")
    @Operation(
            summary = "Create a shortened URL (v2)",
            description = "Receives an original URL and returns a shortened version using NanoID short code."
    )
    @ApiResponses(
            {
                    @ApiResponse(responseCode = "201",
                            description = "Short URL created successfully",
                            content = {
                                    @Content(schema = @Schema(implementation = ShortenUrlResponseDtoV2.class))
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
    public ResponseEntity<ShortenUrlResponseDtoV2>shorten(@RequestBody @Valid ShortenUrlRequestDtoV2 request) {
        ShortenUrlResponseDtoV2 response = generateShortUrlUseCaseV2.execute(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
