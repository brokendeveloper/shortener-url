package tech.brokendeveloper.shortener_url.utils;

import org.springframework.stereotype.Component;

import java.net.MalformedURLException;
import java.net.URL;

@Component
public class ValidateUrl {

    public boolean isValidUrl(String url) {
        try{
            URL parsedUrl = new URL(url);
            String protocol = parsedUrl.getProtocol();
            return protocol.equals("http") || protocol.equals("https");
        }catch (MalformedURLException e){
            return false;
        }
    }
}
