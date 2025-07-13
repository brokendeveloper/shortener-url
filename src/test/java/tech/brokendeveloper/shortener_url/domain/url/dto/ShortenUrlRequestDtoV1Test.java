    package tech.brokendeveloper.shortener_url.domain.url.dto;


    import jakarta.validation.Validation;
    import jakarta.validation.Validator;
    import jakarta.validation.ValidatorFactory;
    import org.junit.jupiter.api.Test;
    import tech.brokendeveloper.shortener_url.api.v1.dto.ShortenUrlRequestDtoV1;

    import static org.junit.jupiter.api.Assertions.assertFalse;
    import static org.junit.jupiter.api.Assertions.assertTrue;

    public class ShortenUrlRequestDtoV1Test {

        private final Validator validator;

        ShortenUrlRequestDtoV1Test() {
            ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
            validator = factory.getValidator();
        }



        @Test
        void shouldPassValidationWhenOriginalUrlIsValid() {
            ShortenUrlRequestDtoV1 dto = new ShortenUrlRequestDtoV1("https://www.brokendeveloper.com");
            var violations = validator.validate(dto);
            assertTrue(violations.isEmpty());
        }

        @Test
        void shouldPassValidationWhenOriginalUrlIsLocalhost() {
            ShortenUrlRequestDtoV1 dto = new ShortenUrlRequestDtoV1("http://localhost:8080");
            var violations = validator.validate(dto);
            assertTrue(violations.isEmpty());
        }

        @Test
        void shouldFailValidationWhenOriginalUrlIsBlank() {
            ShortenUrlRequestDtoV1 dto = new ShortenUrlRequestDtoV1("");
            var violations = validator.validate(dto);
            assertFalse(violations.isEmpty());
        }


        @Test
        void shouldFailValidationWhenOriginalUrlIsNull() {
            ShortenUrlRequestDtoV1 dto = new ShortenUrlRequestDtoV1(null);
            var violations = validator.validate(dto);
            assertFalse(violations.isEmpty());
        }

        @Test
        void shouldFailValidationWhenOriginalUrlHasNoProtocol() {
            ShortenUrlRequestDtoV1 dto = new ShortenUrlRequestDtoV1("www.brokendeveloper.com");
            var violations = validator.validate(dto);
            assertFalse(violations.isEmpty());
        }

        @Test
        void shouldFailValidationWhenOriginalUrlHasInvalidProtocol() {
            ShortenUrlRequestDtoV1 dto = new ShortenUrlRequestDtoV1("ftp://www.brokendeveloper.com");
            var violations = validator.validate(dto);
            assertFalse(violations.isEmpty());
        }
    }
