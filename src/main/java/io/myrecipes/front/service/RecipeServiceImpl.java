package io.myrecipes.front.service;

import io.myrecipes.front.common.RestTemplateHelperImpl;
import io.myrecipes.front.common.S3HelperImpl;
import io.myrecipes.front.dto.Material;
import io.myrecipes.front.dto.Recipe;
import io.myrecipes.front.dto.RecipeRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
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
    private final S3HelperImpl s3Helper;

    public RecipeServiceImpl(RestTemplateHelperImpl restTemplateHelper, S3HelperImpl s3Helper) {
        this.restTemplateHelper = restTemplateHelper;
        this.s3Helper = s3Helper;
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

    @Override
    public String uploadImage(MultipartFile file, String path) {
        String imageUrl = "";

        try {
            imageUrl = this.s3Helper.upload(file, path);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return imageUrl;
    }
}
