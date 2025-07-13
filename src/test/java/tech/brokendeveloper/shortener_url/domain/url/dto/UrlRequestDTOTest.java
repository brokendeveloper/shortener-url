    package tech.brokendeveloper.shortener_url.domain.url.dto;


    import jakarta.validation.Validation;
    import jakarta.validation.Validator;
    import jakarta.validation.ValidatorFactory;
    import org.junit.jupiter.api.Test;

    import static org.junit.jupiter.api.Assertions.assertFalse;
    import static org.junit.jupiter.api.Assertions.assertTrue;

    public class UrlRequestDTOTest {

        private final Validator validator;

        UrlRequestDTOTest() {
            ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
            validator = factory.getValidator();
        }



        @Test
        void shouldPassValidationWhenOriginalUrlIsValid() {
            UrlRequestDTO dto = new UrlRequestDTO("https://www.brokendeveloper.com");
            var violations = validator.validate(dto);
            assertTrue(violations.isEmpty());
        }

        @Test
        void shouldPassValidationWhenOriginalUrlIsLocalhost() {
            UrlRequestDTO dto = new UrlRequestDTO("http://localhost:8080");
            var violations = validator.validate(dto);
            assertTrue(violations.isEmpty());
        }

        @Test
        void shouldFailValidationWhenOriginalUrlIsBlank() {
            UrlRequestDTO dto = new UrlRequestDTO("");
            var violations = validator.validate(dto);
            assertFalse(violations.isEmpty());
        }


        @Test
        void shouldFailValidationWhenOriginalUrlIsNull() {
            UrlRequestDTO dto = new UrlRequestDTO(null);
            var violations = validator.validate(dto);
            assertFalse(violations.isEmpty());
        }

        @Test
        void shouldFailValidationWhenOriginalUrlHasNoProtocol() {
            UrlRequestDTO dto = new UrlRequestDTO("www.brokendeveloper.com");
            var violations = validator.validate(dto);
            assertFalse(violations.isEmpty());
        }

        @Test
        void shouldFailValidationWhenOriginalUrlHasInvalidProtocol() {
            UrlRequestDTO dto = new UrlRequestDTO("ftp://www.brokendeveloper.com");
            var violations = validator.validate(dto);
            assertFalse(violations.isEmpty());
        }
    }
