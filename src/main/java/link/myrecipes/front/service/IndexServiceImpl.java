package link.myrecipes.front.service;

import link.myrecipes.front.common.RestTemplateHelper;
import link.myrecipes.front.dto.PageParam;
import link.myrecipes.front.dto.Recipe;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
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

    private final RestTemplateHelper restTemplateHelper;

    public IndexServiceImpl(RestTemplateHelper restTemplateHelper) {
        this.restTemplateHelper = restTemplateHelper;
    }

    @Override
    @Cacheable(value = "myrecipe:front:recipeList",
            key = "#pageParam.page + ':' + #pageParam.size + ':' + #pageParam.sortField + ':' + #pageParam.descending")
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
    public int readRecipePageCount() {
        UriComponents uriComponents = UriComponentsBuilder.newInstance()
                .scheme(this.scheme)
                .host(this.host)
                .port(this.port)
                .path("/recipes/count")
                .build(true);
        long recipeCount = this.restTemplateHelper.getForEntity(Long.class, uriComponents.toUriString());

        return (int) Math.ceil((double) recipeCount / pageSize);
    }
}
