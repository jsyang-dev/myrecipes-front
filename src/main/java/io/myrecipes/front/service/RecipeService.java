package io.myrecipes.front.service;

import io.myrecipes.front.dto.Material;
import io.myrecipes.front.dto.Recipe;
import io.myrecipes.front.dto.RecipeRequest;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface RecipeService {
    List<Material> readMaterialList();

    Recipe createRecipe(RecipeRequest recipeRequest);

    String uploadImage(MultipartFile file, String path);
}
