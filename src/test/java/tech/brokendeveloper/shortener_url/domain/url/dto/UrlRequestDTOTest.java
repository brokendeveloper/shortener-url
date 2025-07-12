package tech.brokendeveloper.shortener_url.domain.url.dto;


import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;

public class UrlRequestDTOTest {

    private final Validator validator;

    UrlRequestDTOTest() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void shouldFailValidationWhenOriginalUrlIsBlank() {
        UrlRequestDTO dto = new UrlRequestDTO("");
        var violations = validator.validate(dto);
        assertFalse(violations.isEmpty());
    }
}
