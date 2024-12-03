package org.example.payment.adapters.services;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import java.util.Map;

@Service
public class RequestService implements RequestInterface {

    private final RestTemplate restTemplate;

    public RequestService() {
        this.restTemplate = new RestTemplate();
    }

    @Override
    public void post(String path, Map<String, Object> jsonBody) {
        Dotenv dotenv = Dotenv.load();
        String url = System.getenv("URL_API_ORDER") + path;
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(jsonBody, headers);
        this.restTemplate.postForEntity(url, requestEntity, String.class);
    }
}
