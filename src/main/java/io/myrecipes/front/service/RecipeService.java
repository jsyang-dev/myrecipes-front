package io.myrecipes.front.service;

import io.myrecipes.front.dto.Material;
import io.myrecipes.front.dto.Recipe;
import io.myrecipes.front.dto.RecipeRequest;

import java.util.List;

public interface RecipeService {
    List<Material> readMaterialList();

    Recipe createRecipe(RecipeRequest recipeRequest);
}
