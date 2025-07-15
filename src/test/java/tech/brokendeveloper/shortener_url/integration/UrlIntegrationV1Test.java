package tech.brokendeveloper.shortener_url.integration;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class UrlIntegrationV1Test {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void shouldShortenUrlAndReturnStats() throws Exception {
        // 1. short url
        String originalUrl = "https://www.brokendeveloper.com";
        String requestBody = "{\"originalUrl\": \"" + originalUrl + "\"}";

        String response = mockMvc.perform(post("/api/v1/urls/shorten")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.shortenedUrl", notNullValue()))
                .andReturn()
                .getResponse()
                .getContentAsString();


        String shortCode = response.split("/")[response.split("/").length - 1].replace("\"}", "");

        // 2. redirect
        mockMvc.perform(get("/" + shortCode))
                .andExpect(status().isFound())
                .andExpect(header().string("Location", originalUrl));

        // 3. access
        mockMvc.perform(get("/api/urls/" + shortCode + "/access"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalAccesses", is(1)))
                .andExpect(jsonPath("$.avgPerDay", greaterThanOrEqualTo(1.0)));
    }
}