package io.myrecipes.front.service;

import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

public interface RestService {
    <T> ResponseEntity<T> callApi(String url, HttpMethod method);
}
