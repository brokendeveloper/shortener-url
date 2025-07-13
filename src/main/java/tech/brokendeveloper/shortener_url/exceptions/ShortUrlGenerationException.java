package tech.brokendeveloper.shortener_url.exceptions;

public class ShortUrlGenerationException extends RuntimeException {
    public ShortUrlGenerationException(String message) {
        super(message);
    }
}
