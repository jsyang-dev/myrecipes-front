package link.myrecipes.front.service;

import link.myrecipes.front.dto.Material;
import link.myrecipes.front.dto.Recipe;
import link.myrecipes.front.dto.request.RecipeRequest;
import link.myrecipes.front.dto.view.RecipeView;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface RecipeService {
    RecipeView readRecipe(int id);

    List<Material> readMaterialList();

    Recipe createRecipe(RecipeRequest recipeRequest);

    Recipe updateRecipe(int id, RecipeRequest recipeRequest);

    void deleteRecipe(int id);

    String uploadImage(MultipartFile file, String path);

    void increaseReadCount(int id);
}
