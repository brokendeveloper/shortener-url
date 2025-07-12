package tech.brokendeveloper.shortener_url.application;

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
import tech.brokendeveloper.shortener_url.domain.url.dto.UrlRequestDTO;
import tech.brokendeveloper.shortener_url.domain.url.dto.UrlResponseDTO;
import tech.brokendeveloper.shortener_url.domain.url.useCases.GenerateShortUrlUseCase;

@RestController
@RequestMapping("/api/v1/urls")
@Tag(name = "URLs", description = "Endpoints about the URLs")
public class UrlController {

    private final GenerateShortUrlUseCase generateShortUrlUseCase;

    public UrlController(GenerateShortUrlUseCase generateShortUrlUseCase) {
        this.generateShortUrlUseCase = generateShortUrlUseCase;
    }

    @PostMapping("/shorten")
    @Operation(
            summary = "Create a shortened URL",
            description = "Receives an original URL and returns a shortened version."
    )
    @ApiResponses(
            {
                    @ApiResponse(responseCode = "201",
                            description = "Short URL created successfully",
                            content = {
                            @Content(schema = @Schema(implementation = UrlResponseDTO.class))
                    }),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Invalid input data",
                            content = @Content(schema = @Schema(example = "{\"message\": \"The original URL cannot be empty\"}"))
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Internal server error",
                            content = @Content(schema = @Schema(example = "{\"message\": \"Failed to generate shortened url after 3 attempts.\"}"))
                    )
            }
    )
    public ResponseEntity<UrlResponseDTO>shorten(@RequestBody @Valid UrlRequestDTO request) {
        UrlResponseDTO response = generateShortUrlUseCase.execute(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
