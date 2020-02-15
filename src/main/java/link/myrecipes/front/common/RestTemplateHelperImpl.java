package link.myrecipes.front.common;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

@Component
@Slf4j
public class RestTemplateHelperImpl implements RestTemplateHelper {
    private static final String EMBEDDED = "_embedded";

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    public RestTemplateHelperImpl(RestTemplateBuilder restTemplateBuilder, ObjectMapper objectMapper) {
        this.restTemplate = restTemplateBuilder.build();
        this.objectMapper = objectMapper;
    }

    @Override
    public <T> List<T> getForList(Class<T> clazz, String url, Object... uriVariables) {
        ResponseEntity<String> response = this.restTemplate.getForEntity(url, String.class, uriVariables);
        CollectionType type = this.objectMapper.getTypeFactory().constructCollectionType(List.class, clazz);
        List<T> result = null;
        String embeddedJson = null;

        try {
            JSONObject jsonObject = new JSONObject(response.getBody());
            if (jsonObject.has(EMBEDDED)) {
                var iterator = jsonObject.getJSONObject(EMBEDDED).keys();
                String key = (String) iterator.next();
                embeddedJson = jsonObject.getJSONObject(EMBEDDED).getString(key);
            }
        } catch (JSONException e) {
            log.error(e.getMessage());
        }

        try {
            if (embeddedJson == null) {
                result = this.objectMapper.readValue(Objects.requireNonNull(response.getBody()), type);
            } else {
                result = this.objectMapper.readValue(embeddedJson, type);
            }
        } catch (IOException e) {
            log.error(e.getMessage());
        }

        return result;
    }

    @Override
    public <T> T getForEntity(Class<T> clazz, String url, Object... uriVariables) {
        ResponseEntity<String> response = this.restTemplate.getForEntity(url, String.class, uriVariables);
        return getResult(clazz, response);
    }

    @Override
    public <T, R> T postForEntity(Class<T> clazz, String url, R body, Object... uriVariables) {
        HttpEntity<R> request = new HttpEntity<>(body);
        ResponseEntity<String> response = this.restTemplate.postForEntity(url, request, String.class, uriVariables);
        return getResult(clazz, response);
    }

    @Override
    public <T, R> T putForEntity(Class<T> clazz, String url, R body, Object... uriVariables) {
        HttpEntity<R> request = new HttpEntity<>(body);
        ResponseEntity<String> response = this.restTemplate.exchange(url, HttpMethod.PUT, request, String.class, uriVariables);
        return getResult(clazz, response);
    }

    @Override
    public <R> void putForEntity(String url, R body, Object... uriVariables) {
        HttpEntity<R> request = new HttpEntity<>(body);
        this.restTemplate.exchange(url, HttpMethod.PUT, request, String.class, uriVariables);
    }

    @Override
    public void delete(String url) {
        this.restTemplate.delete(url);
    }

    @SuppressWarnings("unchecked")
    private <T> T getResult(Class<T> clazz, ResponseEntity<String> response) {
        JavaType type = this.objectMapper.getTypeFactory().constructType(clazz);
        T result = null;

        try {
            if (clazz == String.class) {
                result = (T) response.getBody();
            } else {
                result = this.objectMapper.readValue(Objects.requireNonNull(response.getBody()), type);
            }
        } catch (IOException e) {
            log.error(e.getMessage());
        }
        return result;
    }
}
