package io.myrecipes.front.service;

import io.myrecipes.front.dto.PageParam;
import io.myrecipes.front.dto.Recipe;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IndexServiceImpl implements IndexService {
    @Value("${app.api.recipe}")
    private String api;

    private final RestService restService;

    public IndexServiceImpl(RestService restService) {
        this.restService = restService;
    }

    @Override
    public List<Recipe> readRecipeList(PageParam pageParam) {
        String url = api + "/recipes"
                        + "?page=" + pageParam.getPage()
                        + "&size=" + pageParam.getSize()
                        + "&sortField=" + pageParam.getSortField()
                        + "&isDescending=" + pageParam.isDescending();

        ResponseEntity<List<Recipe>> responseEntity = this.restService.callApi(url, HttpMethod.GET);
        return responseEntity.getBody();
    }
}
