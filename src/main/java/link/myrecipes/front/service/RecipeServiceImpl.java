package link.myrecipes.front.service;

import link.myrecipes.front.common.RedisTemplateHelper;
import link.myrecipes.front.common.RestTemplateHelperImpl;
import link.myrecipes.front.common.S3HelperImpl;
import link.myrecipes.front.dto.Material;
import link.myrecipes.front.dto.Recipe;
import link.myrecipes.front.dto.request.RecipeRequest;
import link.myrecipes.front.dto.view.RecipeView;
import link.myrecipes.front.repository.RecipeRedisRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

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
    private final RedisTemplateHelper<RecipeView> redisTemplateHelper;
    private final RecipeRedisRepository recipeRedisRepository;

    public RecipeServiceImpl(RestTemplateHelperImpl restTemplateHelper, S3HelperImpl s3Helper, RedisTemplateHelper<RecipeView> redisTemplateHelper, RecipeRedisRepository recipeRedisRepository) {
        this.restTemplateHelper = restTemplateHelper;
        this.s3Helper = s3Helper;
        this.redisTemplateHelper = redisTemplateHelper;
        this.recipeRedisRepository = recipeRedisRepository;
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
                .queryParam("userId", 10001)
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

    @Override
    public RecipeView readRecipe(int id) {
        Optional<RecipeView> recipeViewOptional = Optional.of(redisTemplateHelper.getValue(
                redisTemplateHelper.makeKey(RecipeView.class, String.valueOf(id))
        ));
        return recipeViewOptional.orElseGet(() -> getRecipeView(id));
    }

    private RecipeView getRecipeView(int id) {
        UriComponents uriComponents = UriComponentsBuilder.newInstance()
                .scheme(this.scheme)
                .host(this.host)
                .port(this.port)
                .path("/recipes")
                .path("/" + id)
                .build(true);

        return this.restTemplateHelper.getForEntity(RecipeView.class, uriComponents.toUriString());
    }
}
