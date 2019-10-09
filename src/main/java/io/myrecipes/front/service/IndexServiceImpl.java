package io.myrecipes.front.service;

import io.myrecipes.front.common.RestTemplateHelperImpl;
import io.myrecipes.front.dto.PageParam;
import io.myrecipes.front.dto.Recipe;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@Service
public class IndexServiceImpl implements IndexService {
    @Value("${app.api.recipe.scheme}")
    private String scheme;

    @Value("${app.api.recipe.host}")
    private String host;

    @Value("${app.api.recipe.port}")
    private String port;

    @Value("${app.index.page-size}")
    private int pageSize;

    private final RestTemplateHelperImpl restTemplateHelper;

    public IndexServiceImpl(RestTemplateHelperImpl restTemplateHelper) {
        this.restTemplateHelper = restTemplateHelper;
    }

    @Override
    public List<Recipe> readRecipeList(PageParam pageParam) {
        UriComponents uriComponents = UriComponentsBuilder.newInstance()
                .scheme(this.scheme)
                .host(this.host)
                .port(this.port)
                .path("/recipes")
                .queryParam("page", pageParam.getPage())
                .queryParam("size", pageParam.getSize())
                .queryParam("sortField", pageParam.getSortField())
                .queryParam("isDescending", pageParam.isDescending())
                .build(true);

        return this.restTemplateHelper.getForList(Recipe.class, uriComponents.toUriString());
    }

    @Override
    public int readRecipePageCnt() {
        UriComponents uriComponents = UriComponentsBuilder.newInstance()
                .scheme(this.scheme)
                .host(this.host)
                .port(this.port)
                .path("/recipes/cnt")
                .build(true);
        long recipeCnt = this.restTemplateHelper.getForObject(Long.class, uriComponents.toUriString());

        return (int) Math.ceil((double) recipeCnt / pageSize);
    }
}
