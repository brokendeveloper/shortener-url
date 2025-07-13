package tech.brokendeveloper.shortener_url.domain.url.api.v2.dto;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.Test;
import tech.brokendeveloper.shortener_url.api.v2.dto.ShortenUrlRequestDtoV2;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ShortenUrlRequestDtoV2Test {

    private final Validator validator;

    ShortenUrlRequestDtoV2Test() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void shouldPassValidationWhenOriginalUrlIsValid() {
        ShortenUrlRequestDtoV2 dto = new ShortenUrlRequestDtoV2("https://www.brokendeveloper.com");
        var violations = validator.validate(dto);
        assertTrue(violations.isEmpty());
    }

    @Test
    void shouldPassValidationWhenOriginalUrlIsLocalhost() {
        ShortenUrlRequestDtoV2 dto = new ShortenUrlRequestDtoV2("http://localhost:8080");
        var violations = validator.validate(dto);
        assertTrue(violations.isEmpty());
    }

    @Test
    void shouldFailValidationWhenOriginalUrlIsBlank() {
        ShortenUrlRequestDtoV2 dto = new ShortenUrlRequestDtoV2("");
        var violations = validator.validate(dto);
        assertFalse(violations.isEmpty());
    }

    @Test
    void shouldFailValidationWhenOriginalUrlIsNull() {
        ShortenUrlRequestDtoV2 dto = new ShortenUrlRequestDtoV2(null);
        var violations = validator.validate(dto);
        assertFalse(violations.isEmpty());
    }

    @Test
    void shouldFailValidationWhenOriginalUrlHasNoProtocol() {
        ShortenUrlRequestDtoV2 dto = new ShortenUrlRequestDtoV2("www.brokendeveloper.com");
        var violations = validator.validate(dto);
        assertFalse(violations.isEmpty());
    }

    @Test
    void shouldFailValidationWhenOriginalUrlHasInvalidProtocol() {
        ShortenUrlRequestDtoV2 dto = new ShortenUrlRequestDtoV2("ftp://www.brokendeveloper.com");
        var violations = validator.validate(dto);
        assertFalse(violations.isEmpty());
    }
}
