package tech.brokendeveloper.shortener_url.utils;

import com.aventrix.jnanoid.jnanoid.NanoIdUtils;
import org.springframework.stereotype.Component;

import java.security.SecureRandom;
import java.util.UUID;

@Component
public class NanoIdShortCodeGenerator {

    private final SecureRandom random = new SecureRandom();

    public String generateNanoIdShortCode() {

        int length = 5 + new java.security.SecureRandom().nextInt(4);

        String generateNanoId = NanoIdUtils.randomNanoId(NanoIdUtils.DEFAULT_NUMBER_GENERATOR, NanoIdUtils.DEFAULT_ALPHABET, length);

        return generateNanoId;
    }

    }

