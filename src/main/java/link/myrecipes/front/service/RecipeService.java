package link.myrecipes.front.service;

import link.myrecipes.front.dto.Material;
import link.myrecipes.front.dto.Recipe;
import link.myrecipes.front.dto.request.RecipeRequest;
import link.myrecipes.front.dto.view.RecipeView;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface RecipeService {
    List<Material> readMaterialList();

    Recipe createRecipe(RecipeRequest recipeRequest);

    String uploadImage(MultipartFile file, String path);

    RecipeView readRecipe(int id);
}
