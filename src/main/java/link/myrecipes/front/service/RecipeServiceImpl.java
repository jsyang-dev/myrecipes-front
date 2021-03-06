package link.myrecipes.front.service;

import link.myrecipes.front.common.RestTemplateHelper;
import link.myrecipes.front.common.S3HelperImpl;
import link.myrecipes.front.dto.Material;
import link.myrecipes.front.dto.Recipe;
import link.myrecipes.front.dto.request.RecipeRequest;
import link.myrecipes.front.dto.view.RecipeView;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.util.List;

@Service
@Slf4j
public class RecipeServiceImpl implements RecipeService {
    private static final String RECIPES = "/recipes";

    @Value("${app.api.recipe.scheme}")
    private String scheme;

    @Value("${app.api.recipe.host}")
    private String host;

    @Value("${app.api.recipe.port}")
    private String port;

    private final RestTemplateHelper restTemplateHelper;
    private final S3HelperImpl s3Helper;

    public RecipeServiceImpl(RestTemplateHelper restTemplateHelper, S3HelperImpl s3Helper) {
        this.restTemplateHelper = restTemplateHelper;
        this.s3Helper = s3Helper;
    }

    @Override
    @Cacheable(value = "myrecipe:front:recipeView", key = "#id")
    public RecipeView readRecipe(int id) {
        UriComponents uriComponents = UriComponentsBuilder.newInstance()
                .scheme(this.scheme)
                .host(this.host)
                .port(this.port)
                .path(RECIPES)
                .path("/" + id)
                .build(true);

        return this.restTemplateHelper.getForEntity(RecipeView.class, uriComponents.toUriString());
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
    @CacheEvict(value = "myrecipe:front:recipeList", allEntries = true)
    public Recipe createRecipe(RecipeRequest recipeRequest, int loginUserId) {
        UriComponents uriComponents = UriComponentsBuilder.newInstance()
                .scheme(this.scheme)
                .host(this.host)
                .port(this.port)
                .path(RECIPES)
                .queryParam("userId", loginUserId)
                .build(true);

        return this.restTemplateHelper.postForEntity(Recipe.class, uriComponents.toUriString(), recipeRequest);
    }

    @Override
    @Caching(evict = {
            @CacheEvict(value = "myrecipe:front:recipeView", key = "#id"),
            @CacheEvict(value = "myrecipe:front:recipeList", allEntries = true)
    })
    public Recipe updateRecipe(int id, RecipeRequest recipeRequest, int loginUserId) {
        UriComponents uriComponents = UriComponentsBuilder.newInstance()
                .scheme(this.scheme)
                .host(this.host)
                .port(this.port)
                .path(RECIPES)
                .path("/" + id)
                .queryParam("userId", loginUserId)
                .build(true);

        return this.restTemplateHelper.putForEntity(Recipe.class, uriComponents.toUriString(), recipeRequest);
    }

    @Override
    @Caching(evict = {
            @CacheEvict(value = "myrecipe:front:recipeView", key = "#id"),
            @CacheEvict(value = "myrecipe:front:recipeList", allEntries = true)
    })
    public void deleteRecipe(int id) {
        UriComponents uriComponents = UriComponentsBuilder.newInstance()
                .scheme(this.scheme)
                .host(this.host)
                .port(this.port)
                .path(RECIPES)
                .path("/" + id)
                .build(true);

        this.restTemplateHelper.delete(uriComponents.toUriString());
    }

    @Override
    public String uploadImage(MultipartFile file, String path) {
        String imageUrl = "";

        try {
            imageUrl = this.s3Helper.upload(file, path);
        } catch (IOException e) {
            log.error(e.getMessage());
        }

        return imageUrl;
    }

    @Override
    public void increaseReadCount(int id) {
        UriComponents uriComponents = UriComponentsBuilder.newInstance()
                .scheme(this.scheme)
                .host(this.host)
                .port(this.port)
                .path(RECIPES)
                .path("/" + id)
                .path("/readCount")
                .build(true);

        this.restTemplateHelper.putForEntity(uriComponents.toUriString(), null);
    }
}
