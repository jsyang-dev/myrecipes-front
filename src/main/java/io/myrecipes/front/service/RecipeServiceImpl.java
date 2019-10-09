package io.myrecipes.front.service;

import io.myrecipes.front.common.RestTemplateHelperImpl;
import io.myrecipes.front.dto.Material;
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
}
