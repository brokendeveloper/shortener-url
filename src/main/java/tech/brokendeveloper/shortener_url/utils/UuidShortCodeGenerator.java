package tech.brokendeveloper.shortener_url.utils;

import org.springframework.stereotype.Component;

import java.security.SecureRandom;
import java.util.UUID;

@Component
public class UuidShortCodeGenerator {

    private final SecureRandom random = new SecureRandom();

    public String generateUuidShortCode() {

        String uuid = UUID.randomUUID().toString().replace("-", "");
        int length = random.nextInt(4) + 5;

        String generatedUuid = uuid.substring(0, length);

        return generatedUuid;

    }
}
