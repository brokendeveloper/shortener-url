package tech.brokendeveloper.shortener_url.exceptions;

public class ShortUrlNotFoundException extends RuntimeException {
    public ShortUrlNotFoundException(String shortCode) {
        super("Short code not found: " + shortCode);
    }
}
