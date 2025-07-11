package tech.brokendeveloper.shortener_url.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class UrlBuilder {

    @Value("${app.shortener.base-url}")
    private String baseUrl;


    public String builderUrl(String generatedUrl){
        return baseUrl + generatedUrl;
    }
}
