package io.myrecipes.front.service;

import io.myrecipes.front.domain.PageParam;
import io.myrecipes.front.domain.Recipe;

import java.util.List;

public interface IndexService {
    List<Recipe> readRecipeList(PageParam pageParam);
}
