package tech.brokendeveloper.shortener_url.exceptions;

public class ShortCodeNotFoundException extends RuntimeException {
    public ShortCodeNotFoundException(String shortCode) {
        super("Short code not found: " + shortCode);
    }
}
