package io.myrecipes.front.service;

import io.myrecipes.front.config.RestTemplateHelper;
import io.myrecipes.front.dto.PageParam;
import io.myrecipes.front.dto.Recipe;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class IndexServiceImpl implements IndexService {
    @Value("${app.api.recipe}")
    private String api;

    private final RestTemplate restTemplate;
    private final RestTemplateHelper restTemplateHelper;

    public IndexServiceImpl(RestTemplate restTemplate, RestTemplateHelper restTemplateHelper) {
        this.restTemplate = restTemplate;
        this.restTemplateHelper = restTemplateHelper;
    }

    @Override
    public List<Recipe> readRecipeList(PageParam pageParam) {
        String url = api + "/recipes"
                        + "?page=" + pageParam.getPage()
                        + "&size=" + pageParam.getSize()
                        + "&sortField=" + pageParam.getSortField()
                        + "&isDescending=" + pageParam.isDescending();

        ResponseEntity<List<Recipe>> responseEntity = restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Recipe>>(){});

        return responseEntity.getBody();

//        return restTemplateHelper.getForList(Recipe.class, url);
    }
}
