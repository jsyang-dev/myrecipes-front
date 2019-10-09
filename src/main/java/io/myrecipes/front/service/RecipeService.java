package io.myrecipes.front.service;

import io.myrecipes.front.dto.Material;

import java.util.List;

public interface RecipeService {
    List<Material> readMaterialList();
}
