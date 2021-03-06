package link.myrecipes.front.service;

import link.myrecipes.front.common.RestTemplateHelper;
import link.myrecipes.front.dto.Recipe;
import link.myrecipes.front.dto.RecipeCount;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.PageRequest;
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
            key = "#pageRequest.pageNumber + ':' + #pageRequest.pageSize + ':' + #pageRequest.sort")
    public List<Recipe> readRecipeList(PageRequest pageRequest) {
        UriComponents uriComponents = UriComponentsBuilder.newInstance()
                .scheme(this.scheme)
                .host(this.host)
                .port(this.port)
                .path("/recipes")
                .queryParam("page", pageRequest.getPageNumber())
                .queryParam("size", pageRequest.getPageSize())
                .queryParam("sort", pageRequest.getSort().toString().replace(": ", ","))
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
        RecipeCount recipeCount = this.restTemplateHelper.getForEntity(RecipeCount.class, uriComponents.toUriString());

        return (int) Math.ceil((double) recipeCount.getCount() / pageSize);
    }

    @Override
    public List<Recipe> readPopularRecipeList() {
        UriComponents uriComponents = UriComponentsBuilder.newInstance()
                .scheme(this.scheme)
                .host(this.host)
                .port(this.port)
                .path("/recipes/popular")
                .build(true);

        return this.restTemplateHelper.getForList(Recipe.class, uriComponents.toUriString());
    }
}
