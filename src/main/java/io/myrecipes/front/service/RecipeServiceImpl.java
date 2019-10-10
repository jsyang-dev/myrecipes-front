package io.myrecipes.front.service;

import io.myrecipes.front.common.RestTemplateHelperImpl;
import io.myrecipes.front.dto.Material;
import io.myrecipes.front.dto.Recipe;
import io.myrecipes.front.dto.RecipeRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@Service
public class RecipeServiceImpl implements RecipeService {
    @Value("${app.api.recipe.scheme}")
    private String scheme;

    @Value("${app.api.recipe.host}")
    private String host;

    @Value("${app.api.recipe.port}")
    private String port;

    private final RestTemplateHelperImpl restTemplateHelper;

    public RecipeServiceImpl(RestTemplateHelperImpl restTemplateHelper) {
        this.restTemplateHelper = restTemplateHelper;
    }

    @Override
    public List<Material> readMaterialList() {
        UriComponents uriComponents = UriComponentsBuilder.newInstance()
                .scheme(this.scheme)
                .host(this.host)
                .port(this.port)
                .path("/materials")
                .build(true);

        return this.restTemplateHelper.getForList(Material.class, uriComponents.toUriString());
    }

    @Override
    public Recipe createRecipe(RecipeRequest recipeRequest) {
        UriComponents uriComponents = UriComponentsBuilder.newInstance()
                .scheme(this.scheme)
                .host(this.host)
                .port(this.port)
                .path("/recipes")
                .build(true);

        return this.restTemplateHelper.postForEntity(Recipe.class, uriComponents.toUriString(), recipeRequest);
    }
}
