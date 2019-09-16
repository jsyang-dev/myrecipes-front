package io.myrecipes.front.service;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class RestServiceImpl implements RestService {
    private final RestTemplateBuilder restTemplateBuilder;

    public RestServiceImpl(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplateBuilder = restTemplateBuilder;
    }

    @Override
    public <T> ResponseEntity<T> callApi(String url, HttpMethod method) {
        RestTemplate restTemplate = this.restTemplateBuilder.build();
        return restTemplate.exchange(url, method, null,  new ParameterizedTypeReference<T>(){});
    }
}
